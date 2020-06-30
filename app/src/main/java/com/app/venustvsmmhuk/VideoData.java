package com.app.venustvsmmhuk;

import com.google.api.services.youtube.model.Video;

/**
 * Created by DELL on 4/5/2017.
 */

public class VideoData {
    private Video mVideo;

    public Video getVideo() {
        return mVideo;
    }

    public void setVideo(Video video) {
        mVideo = video;
    }

    public String getTitle() {
        return mVideo.getSnippet().getTitle();
    }
}