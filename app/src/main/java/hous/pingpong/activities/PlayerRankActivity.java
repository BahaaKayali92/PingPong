package hous.pingpong.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import hous.pingpong.PingPongApp;
import hous.pingpong.PlayersMatches;
import hous.pingpong.PlayersParseJson;
import hous.pingpong.R;
import hous.pingpong.adapter.PlayerListAdapter;
import hous.pingpong.fragment.PlayerStaticsFragment;
import hous.pingpong.interfaces.AfterLoadListener;
import hous.pingpong.interfaces.AfterMatchesLoadListner;
import hous.pingpong.wrapper.MatchWrapper;
import hous.pingpong.wrapper.PlayerInformationWrapper;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public class PlayerRankActivity extends AppCompatActivity implements ListView.OnItemClickListener, AfterLoadListener, AfterMatchesLoadListner {
    private ArrayList<PlayerInformationWrapper> players;
    private ArrayList<MatchWrapper> matchPlayers;
    AfterLoadListener load = this;
    AfterMatchesLoadListner Matchesload = this;
    private ListView playerList;
    private PlayerListAdapter adapter;
    private RequestQueue queue;
    private boolean finish = false;
    public static int number = 0;
    Toolbar toolbar;
    private ProgressDialog progressDialog;
    FloatingActionButton floatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_rank);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        floatBtn = (FloatingActionButton) findViewById(R.id.fab);
        playerList = (ListView) findViewById(R.id.player_list);
        players = new ArrayList<>();
        adapter = new PlayerListAdapter(PlayerRankActivity.this, players);
        playerList.setAdapter(adapter);
        playerList.setOnItemClickListener(this);
        matchPlayers = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayerRankActivity.this, MatchActivity.class);
                startActivity(i);
                finish();
            }
        });
        connectUrl();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PlayerStaticsFragment player = new PlayerStaticsFragment(matchPlayers, players, position);
        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(findViewById(R.id.player_photo), "logo")
                .setCustomAnimations(R.anim.scale_in, R.anim.scale_down)
                .replace(R.id.frame, player)
                .addToBackStack(null)
                .commit();
    }

    public void connectUrl() {
        queue = Volley.newRequestQueue(this);
        new Thread() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, PingPongApp.GET_PLAYERS_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                PlayersParseJson ppj = new PlayersParseJson(response, getBaseContext(), players, load);
                                ppj.parseJSON();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
                queue.add(stringRequest);
            }
        }.start();
    }

    @Override
    public void onSuccess() {
        // adapter.notifyDataSetChanged();
        for (int i = 0; i < players.size(); i++) {
            connectPlayerStatics(players.get(i).getPlayerId());
        }
        //     adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PingPongApp.toast(PlayerRankActivity.this, errorMessage);
                progressDialog.dismiss();
            }
        });

    }

    public void connectPlayerStatics(final String id) {
        queue = Volley.newRequestQueue(this);
        new Thread() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, PingPongApp.GET_PLAYERS_MATCHES_URL + id,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                PlayersMatches ppj = new PlayersMatches(response, getBaseContext(), players, matchPlayers, Matchesload, number);
                                ppj.parseJSON();
                                if (id.equals("" + (players.size()))) {
                                    finish = true;
                                    number++;
                                    try {
                                        onMatchesLoadSucess(number);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PingPongApp.toast(getBaseContext(), "Loading failed for player number" + id);
                        number++;
                        if (id.equals("" + (players.size()))) {
                            finish = true;

                            try {
                                onMatchesLoadSucess(number);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                });
                queue.add(stringRequest);
            }
        }.start();
    }

    @Override
    public void onMatchesLoadSucess(int id) throws InterruptedException {
        number = id;

        if (number == players.size()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int index = 0;
                    PlayerInformationWrapper temp;
                    PlayerInformationWrapper temp_two;
                    for (int i = 0; i < players.size(); i++) {
                        index = i;
                        float ratioTemp = players.get(i).getRatio();
                        for (int j = i + 1; j < players.size(); j++) {
                            if (ratioTemp < players.get(j).getRatio()) {
                                ratioTemp = players.get(j).getRatio();
                                index = j;
                            }
                        }
                        temp = players.get(i);
                        temp_two = players.get(index);
                        players.remove(i);
                        players.add(i, temp_two);
                        players.remove(index);
                        players.add(index, temp);
                    }
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            });
        }
    }


    @Override
    public void onMatchesLoadError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PingPongApp.toast(PlayerRankActivity.this, errorMessage);
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        number = 0;
    }
}
