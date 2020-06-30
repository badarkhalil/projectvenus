package com.app.venustvsmmhuk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by DELL on 10/19/2017.
 */

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    private Context context;
    private List<VideoData> videosList;

    public PlaylistAdapter(Context context, List<VideoData> videosList) {
        super();
        this.context = context;
        this.videosList = videosList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.playlist_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(videosList.get(i).getTitle());
        Picasso.with(context).load(videosList.get(i).getVideo().getSnippet().getThumbnails().getHigh().getUrl()).into(viewHolder.thumbnail);

        viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((YoutubeVideoInterface) context).playVideo(videosList.get(i).getVideo().getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return videosList == null ? 0 : videosList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public ImageView thumbnail;
        public TextView title;

        public ViewHolder(View view) {
            super(view);
            itemView = view;
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
