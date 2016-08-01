package hous.pingpong;

import android.content.Context;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hous.pingpong.interfaces.AfterLoadListener;
import hous.pingpong.wrapper.PlayerInformationWrapper;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public class PlayersParseJson {
    private String json;
    public Context con;
    public static final String JSON_ARRAY = "";
    public ArrayList<PlayerInformationWrapper> players;
    AfterLoadListener load;

    public PlayersParseJson(String json, Context con, ArrayList<PlayerInformationWrapper> players,final AfterLoadListener load) {
        this.con = con;
        this.json = json;
        this.players = players;
        this.load = load;
    }


    public void parseJSON() {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(json);

            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    players.add(new PlayerInformationWrapper(jo.getString("name"), jo.getString("id")));
                }
                load.onSuccess();
            } catch (Exception e) {
                load.onError(con.getString(R.string.parse_error));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
