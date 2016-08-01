package hous.pingpong.interfaces;

import java.util.List;

/**
 * Created by mohand1993 on 27/06/16.
 */
public interface AfterLoadListener{
    void onSuccess();
    void onError(String errorMessage);
}
