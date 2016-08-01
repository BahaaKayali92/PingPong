package hous.pingpong.wrapper;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public class AgaintPlayerStaticsWrapper {
    String id;
    int win;
    int lose;
    public AgaintPlayerStaticsWrapper(String id)
    {
        this.id=id;
        win=0;
        lose=0;
    }

    public String getId() {
        return id;
    }

    public int getLose() {
        return lose;
    }

    public int getWin() {
        return win;
    }

    public void setWin() {
        win++;
    }

    public void setLose() {
        lose++;
    }
}
