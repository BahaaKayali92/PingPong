package hous.pingpong.adapter;

/**
 * Created by E.Kayali on 7/31/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hous.pingpong.fragment.TourFragment;

public class SamplePagerAdapter extends FragmentPagerAdapter {


    private int mSize;

    public SamplePagerAdapter(FragmentManager fm) {
        super(fm);
        mSize = 3;
    }

    public SamplePagerAdapter(int count,FragmentManager fm) {
        super(fm);
        mSize = count;
    }

    @Override public int getCount() {
        return mSize;
    }







    @Override
    public Fragment getItem(int position) {
        return new TourFragment(position);
    }

    /*@Override public Object instantiateItem(ViewGroup view, int position) {
        TextView textView = new TextView(view.getContext());
        textView.setText(String.valueOf(position + 1));
        textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(48);
        view.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return textView;
    }*/

    public void addItem() {
        mSize++;
        notifyDataSetChanged();
    }

    public void removeItem() {
        mSize--;
        mSize = mSize < 0 ? 0 : mSize;

        notifyDataSetChanged();
    }
}