package hous.pingpong.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hous.pingpong.R;
import hous.pingpong.wrapper.PlayerInformationWrapper;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public class PlayerListAdapter extends ArrayAdapter<PlayerInformationWrapper> {
    private Activity con;
    private ArrayList<PlayerInformationWrapper> playerList;
    public PlayerListAdapter(Activity context, ArrayList<PlayerInformationWrapper> playerList) {
        super(context,android.R.layout.simple_list_item_1,playerList);
        this.con=context;
        this.playerList=playerList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView=con.getLayoutInflater().inflate(R.layout.simple_list_item, null, true);
        ImageView playerImage= (ImageView) rootView.findViewById(R.id.player_photo);
        ImageView flag= (ImageView) rootView.findViewById(R.id.flag);
        TextView firstName= (TextView) rootView.findViewById(R.id.firstName);
        firstName.setText(playerList.get(position).getPlayerName());
        TextView secondName= (TextView) rootView.findViewById(R.id.secondName);
        switch (position)
        {
            case 0:
                playerList.get(position).setImgResId(R.drawable.malong);
                playerImage.setImageResource(playerList.get(position).getImgResId());
                flag.setImageResource(R.drawable.chinaflag);
                secondName.setText("MALONG");
                break;
            case 1:
                playerList.get(position).setImgResId(R.drawable.timoboll);
                playerImage.setImageResource(R.drawable.timoboll);
                flag.setImageResource(R.drawable.flaggermany);
                secondName.setText("BOLL");
                break;
            case 2:
                playerList.get(position).setImgResId(R.drawable.dingding);
                playerImage.setImageResource(R.drawable.dingding);
                flag.setImageResource(R.drawable.chinaflag);
                secondName.setText("DING");
                break;
            case 3:
                playerList.get(position).setImgResId(R.drawable.zhang);
                playerImage.setImageResource(R.drawable.zhang);
                flag.setImageResource(R.drawable.chinaflag);
                secondName.setText("ZHANG");
                break;
            default:
                playerImage.setImageResource(R.drawable.malong);
                flag.setImageResource(R.drawable.chinaflag);
                secondName.setText("MALONG");
        }
        return rootView;
    }
}
