package hous.pingpong;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hous.pingpong.interfaces.AfterDetailListner;
import hous.pingpong.interfaces.AfterLoadListener;
import hous.pingpong.wrapper.PlayerInformationWrapper;

/**
 * Created by E.Kayali on 7/31/2016.
 */
public class ParseDetailPlayer {
    private String json;
    public Context con;
AfterDetailListner load;

    public ParseDetailPlayer(String json, Context con,AfterDetailListner load) {
        this.con = con;
        this.json = json;
        this.load=load;
    }


    public void parseJSON() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);


            load.onSuccess(jsonObject.getString("age"));


        } catch (Exception e) {
            PingPongApp.toast(con, "error loading");
        }


    }
}

