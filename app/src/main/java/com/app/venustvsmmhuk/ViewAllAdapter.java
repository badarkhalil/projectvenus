package com.app.venustvsmmhuk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 10/23/2017.
 */

public class ViewAllAdapter extends BaseAdapter {
    private Context context;
    private List<String> thumnailUrlList = new ArrayList<String>();
    private List<String> titleList = new ArrayList<String>();


    public ViewAllAdapter(Context context, List<String> titleList, List<String> thumnailUrlList) {
        this.context = context;
        this.titleList = titleList;
        this.thumnailUrlList = thumnailUrlList;
    }

    @Override
    public int getCount() {
        return thumnailUrlList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.playlist_item, viewGroup, false);

        ImageView thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        TextView title = (TextView) v.findViewById(R.id.title);

        title.setText(titleList.get(i));
        Picasso.with(context).load(thumnailUrlList.get(i)).into(thumbnail);

        return v;
    }
}
