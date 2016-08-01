package hous.pingpong.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hous.pingpong.R;
import hous.pingpong.wrapper.MatchWrapper;
import hous.pingpong.wrapper.PlayerInformationWrapper;

/**
 * Created by E.Kayali on 7/30/2016.
 */
public class MatchesListAdapter extends ArrayAdapter<MatchWrapper> {
    private Activity con;
    ArrayList<MatchWrapper> matches;
    ArrayList<PlayerInformationWrapper> player;
    public MatchesListAdapter(Activity context, ArrayList<MatchWrapper> matches, ArrayList<PlayerInformationWrapper> players) {
        super(context, android.R.layout.simple_list_item_1, matches);
        this.con = context;
        this.matches = matches;
        player = players;
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = con.getLayoutInflater().inflate(R.layout.simple_statics_list_item, null, true);
        TextView firstPlayerName = (TextView) rootView.findViewById(R.id.player_name);
        firstPlayerName.setText(getPlayerName(matches.get(position).getFirstPlayer()));
        TextView secondPlayerName = (TextView) rootView.findViewById(R.id.secondPlayer_name);
        secondPlayerName.setText(getPlayerName(matches.get(position).getSecondPlayer()));
        TextView firstPlayerScore = (TextView) rootView.findViewById(R.id.firstPlayer_score);
        firstPlayerScore.setText(matches.get(position).getFirstPlayerScore());
        TextView secondPlayerScore = (TextView) rootView.findViewById(R.id.secondPlayer_score);
        secondPlayerScore.setText(matches.get(position).getSecondPlayerScore());
        return rootView;
    }
    public String getPlayerName(String id)
    {
        for (int i=0;i<player.size();i++)
        {
            if (player.get(i).getPlayerId()==id)
                return player.get(i).getPlayerName();
        }
        return "";
    }
}
