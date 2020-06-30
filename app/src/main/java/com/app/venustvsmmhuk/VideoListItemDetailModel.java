package com.app.venustvsmmhuk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 10/23/2017.
 */

public class VideoListItemDetailModel implements Serializable {
    private List<String> thumnailUrlList;
    private List<String> titleList;
    private List<String> videoIdList;

    public VideoListItemDetailModel() {

    }

    public VideoListItemDetailModel(VideoListItemDetailModel duplicate) {
        thumnailUrlList = new ArrayList<String>();
        for (String thumnail : duplicate.getThumnailUrlList()) {
            thumnailUrlList.add(thumnail);
        }
        titleList = new ArrayList<String>();
        for (String title : duplicate.getTitleList()) {
            titleList.add(title);
        }
        videoIdList = new ArrayList<String>();
        for (String videoId : duplicate.getVideoIdList()) {
            videoIdList.add(videoId);
        }
    }

    public List<String> getThumnailUrlList() {
        return thumnailUrlList;
    }

    public void setThumnailUrlList(List<String> thumnailUrlList) {
        this.thumnailUrlList = thumnailUrlList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public List<String> getVideoIdList() {
        return videoIdList;
    }

    public void setVideoIdList(List<String> videoIdList) {
        this.videoIdList = videoIdList;
    }
}
