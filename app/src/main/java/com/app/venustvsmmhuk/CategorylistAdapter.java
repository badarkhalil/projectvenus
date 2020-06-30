package com.app.venustvsmmhuk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by DELL on 10/20/2017.
 */

public class CategorylistAdapter extends RecyclerView.Adapter<CategorylistAdapter.ViewHolder> {
    private Context context;
    private List<String> playlistTitles;
    private Map<Integer, List<VideoData>> videosMap;

    public CategorylistAdapter(Context context, List<String> playlistTitles, Map<Integer, List<VideoData>> videosMap) {
        super();
        this.context = context;
        this.playlistTitles = playlistTitles;
        this.videosMap = videosMap;
    }

    @Override
    public CategorylistAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.playlist_category, viewGroup, false);
        CategorylistAdapter.ViewHolder viewHolder = new CategorylistAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategorylistAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.category_name.setText(playlistTitles.get(i));

        viewHolder.view_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ViewAllInterface) context).viewAllVideos(videosMap.get(i));
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerView.Adapter mAdapter = new PlaylistAdapter(context, videosMap.get(i));
        viewHolder.mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getItemCount() {
        return playlistTitles == null ? 0 : playlistTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView category_name;
        public TextView view_all;
        public RecyclerView mRecyclerView;

        public ViewHolder(View view) {
            super(view);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            view_all = (TextView) itemView.findViewById(R.id.view_all);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.playlist);
        }
    }
}
