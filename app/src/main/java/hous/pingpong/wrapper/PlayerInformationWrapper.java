package hous.pingpong.wrapper;

import java.util.ArrayList;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public class PlayerInformationWrapper {
    String playerName;
    String playerId;

    int imgResId;
    float playerWin;
    float playerLose;
    float ratio;
    public  ArrayList<AgaintPlayerStaticsWrapper> againtsPlayer;

    public PlayerInformationWrapper(String playerName, String playerId) {
        this.playerName = playerName;
        this.playerId = playerId;


        playerWin = 0;
        playerLose = 0;
        ratio = 0;
        againtsPlayer=new ArrayList<>();
    }

    public void increaseWin() {
        playerWin++;
        calculateRatio();
    }

    public void increaseLose() {
        playerLose++;
        calculateRatio();
    }

    public void calculateRatio() {
        if (playerLose != 0)
            ratio = playerWin / playerLose;
        else if (playerWin != 0)
            ratio = 1;
        else
            ratio = 0;
    }

    public float getRatio() {
        return ratio;
    }

    public boolean isSameId(String id) {
        if (playerId == id)
            return true;
        return false;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public int getImgResId() {
        return imgResId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPlayerId() {
        return playerId;
    }


}
