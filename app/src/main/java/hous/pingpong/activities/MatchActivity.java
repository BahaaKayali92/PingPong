package hous.pingpong.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import hous.pingpong.ParsePlayerNames;
import hous.pingpong.PingPongApp;
import hous.pingpong.PlayersMatches;
import hous.pingpong.R;
import hous.pingpong.interfaces.AfterLoadListener;

/**
 * Created by E.Kayali on 7/31/2016.
 */
public class MatchActivity extends AppCompatActivity implements AfterLoadListener {
    private RequestQueue queue;
    Spinner firstPlayer;
    Spinner secondPlayer;
    EditText firstPlayerScore;
    EditText secondPlayerScore;
    ProgressDialog progress;
    ArrayList<String> names;
    AfterLoadListener load = this;
    ArrayAdapter<String> adapter;
    Button submit;
Toolbar toolbar;
    TextView toolbarText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_add_match);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbarText= (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarText.setText("New Match");
        names = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, names);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading....");
        progress.show();
        getPlayersNames();
        firstPlayer = (Spinner) findViewById(R.id.first_player_names_spinners);
        firstPlayer.setAdapter(adapter);
        secondPlayer = (Spinner) findViewById(R.id.second_player_names_spinners);
        secondPlayer.setAdapter(adapter);
        firstPlayerScore = (EditText) findViewById(R.id.first_player_score);
        secondPlayerScore = (EditText) findViewById(R.id.second_player_score);
        submit = (Button) findViewById(R.id.submit_match_btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
addMatch();
            }
        });
    }

    private void addMatch() {
        if (firstPlayerScore.getText().toString().equals(""))
        {
            PingPongApp.shakeBoxes(firstPlayerScore);
            return;
        }
        else if (secondPlayerScore.getText().toString().equals(""))
        {
            PingPongApp.shakeBoxes(secondPlayerScore);
            return;
        }
        queue = Volley.newRequestQueue(this);
        new Thread() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, PingPongApp.ADD_MATCH_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ParsePlayerNames ppj = new ParsePlayerNames(response, getBaseContext(), names, load);
                                ppj.parseJSON();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PingPongApp.toast(getBaseContext(), "I dont which parameters i have to put");
                        handelr();
                    }
                });
                queue.add(stringRequest);
            }
        }.start();
    }

    private void handelr() {
        final Intent intent = new Intent(MatchActivity.this, PlayerRankActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finishDelayed();
                startActivity(intent);
            }
        }, 2000);
    }

    public void getPlayersNames() {
        queue = Volley.newRequestQueue(this);
        new Thread() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, PingPongApp.GET_PLAYERS_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ParsePlayerNames ppj = new ParsePlayerNames(response, getBaseContext(), names, load);
                                ppj.parseJSON();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PingPongApp.toast(getBaseContext(), "Loading failed for players names");
                    }
                });
                queue.add(stringRequest);
            }
        }.start();
    }

    @Override
    public void onSuccess() {
        adapter.notifyDataSetChanged();
        progress.dismiss();

    }

    @Override
    public void onError(final String errorMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PingPongApp.toast(getBaseContext(), errorMessage);
            }
        });
    }
    private void finishDelayed() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);

    }
}
