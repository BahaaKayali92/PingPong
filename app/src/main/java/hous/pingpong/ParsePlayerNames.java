package hous.pingpong;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hous.pingpong.interfaces.AfterLoadListener;
import hous.pingpong.wrapper.PlayerInformationWrapper;

/**
 * Created by E.Kayali on 7/31/2016.
 */
public class ParsePlayerNames {
    private String json;
    public Context con;
    public static final String JSON_ARRAY = "";
    public ArrayList<String> players;
    AfterLoadListener load;

    public ParsePlayerNames(String json, Context con, ArrayList<String> players,final AfterLoadListener load) {
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
                    players.add(jo.getString("name"));
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
