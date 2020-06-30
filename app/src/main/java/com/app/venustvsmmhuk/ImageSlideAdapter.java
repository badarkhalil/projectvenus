package com.app.venustvsmmhuk;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

public class ImageSlideAdapter extends PagerAdapter {

    private Integer[] mThumbIds;

    public ImageSlideAdapter(Integer[] mThumbIds) {
        this.mThumbIds = mThumbIds;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.vp_image, container, false);

        ImageView mImageView = (ImageView) view.findViewById(R.id.image_display);
        mImageView.setBackgroundResource(mThumbIds[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}