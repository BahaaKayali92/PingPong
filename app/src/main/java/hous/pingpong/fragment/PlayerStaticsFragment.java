package hous.pingpong.fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import hous.pingpong.ParseDetailPlayer;
import hous.pingpong.PingPongApp;
import hous.pingpong.PlayersParseJson;
import hous.pingpong.R;
import hous.pingpong.adapter.MatchesListAdapter;
import hous.pingpong.interfaces.AfterDetailListner;
import hous.pingpong.interfaces.AfterLoadListener;
import hous.pingpong.wrapper.MatchWrapper;
import hous.pingpong.wrapper.PlayerInformationWrapper;

/**
 * Created by E.Kayali on 7/30/2016.
 */
public class PlayerStaticsFragment extends Fragment implements AfterDetailListner {
    de.hdodenhof.circleimageview.CircleImageView playerImage;
    TextView playerName;
    TextView playerAge;
    ListView playerMatches;
    MatchesListAdapter adapter;
    ArrayList<MatchWrapper> Matches;
    int position;
    ArrayList<MatchWrapper> playerMatchesArray;
    ArrayList<PlayerInformationWrapper> player;
    private RequestQueue queue;
    AfterDetailListner load;

String age;
    public PlayerStaticsFragment(ArrayList<MatchWrapper> Matches, ArrayList<PlayerInformationWrapper> players, int playerId) {
        this.Matches = Matches;
        position = playerId;
        player = players;
        playerMatchesArray = new ArrayList<>();
        load=this;
    }

    public void getPlayerMatches(ArrayList<MatchWrapper> match, String id) {
        for (int i = 0; i < match.size(); i++) {
            if (Matches.get(i).getFirstPlayer() == id || Matches.get(i).getSecondPlayer() == id) {
                playerMatchesArray.add(Matches.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       age =getPlayerAge();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_static, container, false);
        adapter = new MatchesListAdapter(getActivity(), playerMatchesArray, player);
        playerImage = (CircleImageView) rootView.findViewById(R.id.player_photo);
        playerImage.setImageResource(player.get(position).getImgResId());
        playerName = (TextView) rootView.findViewById(R.id.player_name);
        playerName.setText(player.get(position).getPlayerName());
        playerAge = (TextView) rootView.findViewById(R.id.age);

        playerMatches = (ListView) rootView.findViewById(R.id.player_mayches_list);
        playerMatches.setAdapter(adapter);
        getPlayerMatches(Matches, player.get(position).getPlayerId());
        return rootView;
    }

    public String getPlayerAge() {
        queue = Volley.newRequestQueue(getActivity());
        new Thread() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, PingPongApp.GET_PLAYER_DETAIL_URL + player.get(position).getPlayerId(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ParseDetailPlayer ppj = new ParseDetailPlayer(response, getContext(),load);
                                ppj.parseJSON();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PingPongApp.toast(getContext(), "Error Loading Player Age");

                    }
                });
                queue.add(stringRequest);
            }
        }.start();
        return "";
    }



    @Override
    public void onSuccess(String age) {
        playerAge.setText(age);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    // handle back button's click listener
                    getActivity().getSupportFragmentManager().beginTransaction().remove(PlayerStaticsFragment.this).setCustomAnimations(R.anim.scale_down,R.anim.scale_down).commit();
                    }
                    return true;


            }
        });
    }
}
