package hous.pingpong.interfaces;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public interface AfterMatchesLoadListner {
    void onMatchesLoadSucess(int id) throws InterruptedException;
    void onMatchesLoadError(String errorMessage);
}
