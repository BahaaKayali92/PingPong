package hous.pingpong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import hous.pingpong.R;

/**
 * Created by E.Kayali on 7/31/2016.
 */
public class TourFragment extends Fragment {
    int position;
    ImageView background;
TextView text;
    public TourFragment(int position) {
        this.position = position;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tour, container, false);
        background = (ImageView) rootView.findViewById(R.id.fragment_img);
        text= (TextView) rootView.findViewById(R.id.fragment_descreption);
        switch (position)
        {
            case 0:
                background.setImageResource(R.drawable.background_tour);
                text.setText(R.string.rank_fragment);
                break;
            case 1:
                background.setImageResource(R.drawable.statics);
                text.setText(R.string.statics_fragment);
                break;
            case 2:
                background.setImageResource(R.drawable.new_match);
                text.setText(R.string.match_fragment);
                break;
        }
        return rootView;
    }
}
