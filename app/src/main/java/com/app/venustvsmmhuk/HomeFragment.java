package com.app.venustvsmmhuk;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment {

    private static final long ANIM_VIEWPAGER_DELAY = 3000;
    private Integer[] mThumbIds1 = {R.drawable.punjabiandiballeeballee, R.drawable.cooking};
    private Integer[] mThumbIds2 = {R.drawable.astrology, R.drawable.cookingshow,
            R.drawable.currentaffairs, R.drawable.politicalshow, R.drawable.religioushow,
            R.drawable.stagedrama};
    private ViewPager mViewPager1, mViewPager2;
    private Runnable animateViewPager;
    private Handler handler;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((BaseActivity) getActivity()).initHeader();

        mViewPager1 = (ViewPager) view.findViewById(R.id.viewpager1);
        mViewPager1.setAdapter(new ImageSlideAdapter(mThumbIds1));

        mViewPager2 = (ViewPager) view.findViewById(R.id.viewpager2);
        mViewPager2.setAdapter(new ImageSlideAdapter(mThumbIds2));

        runnable();
        handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);

        return view;
    }

    public void runnable() {
        if (handler == null) {
            handler = new Handler();
        }

        animateViewPager = new Runnable() {
            public void run() {
                if (mViewPager1.getCurrentItem() == mThumbIds1.length - 1) {
                    mViewPager1.setCurrentItem(0);
                } else {
                    mViewPager1.setCurrentItem(
                            mViewPager1.getCurrentItem() + 1, true);
                }

                if (mViewPager2.getCurrentItem() == mThumbIds2.length - 1) {
                    mViewPager2.setCurrentItem(0);
                } else {
                    mViewPager2.setCurrentItem(
                            mViewPager2.getCurrentItem() + 1, true);
                }
                handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
            }
        };
    }
}
