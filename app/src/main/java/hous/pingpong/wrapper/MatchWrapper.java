package hous.pingpong.wrapper;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public class MatchWrapper {
    String time;
    String firstPlayer;
    String secondPlayer;
    String firstPlayerScore;
    String secondPlayerScore;
    public MatchWrapper(String time,String firstPlayer,String secondPlayer,String firstPlayerScore,String secondPlayerScore)
    {
        this.time=time;
        this.firstPlayer=firstPlayer;
        this.secondPlayer=secondPlayer;
        this.firstPlayerScore=firstPlayerScore;
        this.secondPlayerScore=secondPlayerScore;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public String getFirstPlayerScore() {
        return firstPlayerScore;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public String getSecondPlayerScore() {
        return secondPlayerScore;
    }

    public String getTime() {
        return time;
    }
}
