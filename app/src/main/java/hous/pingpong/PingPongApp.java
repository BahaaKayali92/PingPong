package hous.pingpong;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

/**
 * Created by E.Kayali on 7/29/2016.
 */
public class PingPongApp {
    public static final String GET_PLAYERS_URL="https://demo1743076.mockable.io/player";
    public static final String GET_PLAYER_DETAIL_URL="https://demo1743076.mockable.io/player/";
    public static final String GET_PLAYERS_MATCHES_URL="https://demo1743076.mockable.io/match?player=";
    public static final String ADD_MATCH_URL="https://demo1743076.mockable.io/match";

    public static void toast(Context con,String message)
    {
        Toast.makeText(con,message,Toast.LENGTH_SHORT).show();
    }
    public static void toast(Context con,int stringResource)
    {
        Toast.makeText(con,con.getString(stringResource),Toast.LENGTH_SHORT).show();
    }
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


    public static void shakeBoxes(View view) {
        view.requestFocus();
        Animation an = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);
        view.startAnimation(an);
    }

}
