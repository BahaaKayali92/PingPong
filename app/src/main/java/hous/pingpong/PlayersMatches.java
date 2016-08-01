package hous.pingpong;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import hous.pingpong.interfaces.AfterLoadListener;
import hous.pingpong.interfaces.AfterMatchesLoadListner;
import hous.pingpong.wrapper.AgaintPlayerStaticsWrapper;
import hous.pingpong.wrapper.MatchWrapper;
import hous.pingpong.wrapper.PlayerInformationWrapper;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public class PlayersMatches {
    private  int id;
    private String json;
    public Context con;
    public static final String JSON_ARRAY = "";
    public ArrayList<PlayerInformationWrapper> players;
    public ArrayList<MatchWrapper> playersMatches;
    AfterMatchesLoadListner load;


    public PlayersMatches(String json, Context con, ArrayList<PlayerInformationWrapper> players, ArrayList<MatchWrapper> playersMatches, AfterMatchesLoadListner load,int id) {
        this.con = con;
        this.json = json;
        this.playersMatches = playersMatches;
        this.players = players;
        this.load = load;
        this.id=id;
    }

    public void parseJSON() {
        JSONArray jsonArray = null;
        JSONArray matchArray = null;
        int j;
        try {
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = jsonArray.getJSONObject(i);
                matchArray = jo.getJSONArray("players");

                for (j = 0; j < playersMatches.size(); j++)
                    if (playersMatches.get(j).getFirstPlayer() == matchArray.getJSONObject(0).getString("id")
                            && playersMatches.get(j).getSecondPlayer() == matchArray.getJSONObject(1).getString("id")
                            && playersMatches.get(j).getFirstPlayerScore() == matchArray.getJSONObject(0).getString("score")
                            && playersMatches.get(j).getSecondPlayerScore() == matchArray.getJSONObject(1).getString("score")) {
                        break;
                    }
                if (j == playersMatches.size()) {
                    playersMatches.add(new MatchWrapper(jo.getString("timeStart"), matchArray.getJSONObject(0).getString("id"), matchArray.getJSONObject(1).getString("id"), matchArray.getJSONObject(0).getString("score"), matchArray.getJSONObject(1).getString("score")));
                    compareAndAdd(matchArray.getJSONObject(0).getString("id"), matchArray.getJSONObject(1).getString("id"), matchArray.getJSONObject(0).getString("score"), matchArray.getJSONObject(1).getString("score"));
                }
            }

           load.onMatchesLoadSucess(id+1);
        } catch (Exception e) {
            load.onMatchesLoadError(con.getString(R.string.parse_error));
        }

    }


    public void compareAndAdd(String firstPlayerId, String secondPlayerId, String firstPlayerScore, String secondPlayerScore) {
        if (Integer.parseInt(firstPlayerScore) > Integer.parseInt(secondPlayerScore)) {
            addWin(firstPlayerId, secondPlayerId);
        } else  {
            addWin(secondPlayerId,firstPlayerId);
        }

    }

    public void addWin(String winerId, String loserId) {
        int j;
        for (int i = 0; i < players.size(); i++) {

            if (players.get(i).getPlayerId() == winerId) {

                players.get(i).increaseWin();
                for (j = 0; j < players.get(i).againtsPlayer.size(); j++) {

                    if (players.get(i).againtsPlayer.get(j).getId() == loserId) {

                        players.get(i).againtsPlayer.get(j).setWin();
                        continue;
                    }
                }
                if (j == players.get(i).againtsPlayer.size()) {
                    players.get(i).againtsPlayer.add(new AgaintPlayerStaticsWrapper(loserId));
                    players.get(i).againtsPlayer.get(players.get(i).againtsPlayer.size() - 1).setWin();
                }
                continue;
            }
            if (players.get(i).getPlayerId() == loserId)
            {
                players.get(i).increaseLose();
                for (j = 0; j < players.get(i).againtsPlayer.size(); j++) {

                    if (players.get(i).againtsPlayer.get(j).getId() == winerId) {

                        players.get(i).againtsPlayer.get(j).setLose();
                        continue;
                    }
                }
                if (j == players.get(i).againtsPlayer.size()) {
                    players.get(i).againtsPlayer.add(new AgaintPlayerStaticsWrapper(winerId));
                    players.get(i).againtsPlayer.get(players.get(i).againtsPlayer.size() - 1).setLose();
                }
                continue;
            }
        }
    }

    public void addLose(String loserId, String winnerId) {
        int j;
        for (int i = 0; i < players.size(); i++) {

            if (players.get(i).getPlayerId() == loserId) {

                players.get(i).increaseLose();
                for (j = 0; j < players.get(i).againtsPlayer.size(); j++) {

                    if (players.get(i).againtsPlayer.get(j).getId() == winnerId) {

                        players.get(i).againtsPlayer.get(j).setLose();
                        break;
                    }
                }
                if (j == players.get(i).againtsPlayer.size()) {
                    players.get(i).againtsPlayer.add(new AgaintPlayerStaticsWrapper(winnerId));
                    players.get(i).againtsPlayer.get(players.get(i).againtsPlayer.size() - 1).setLose();
                }
                break;
            }
        }
    }
}
