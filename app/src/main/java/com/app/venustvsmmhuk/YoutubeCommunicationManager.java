package com.app.venustvsmmhuk;

import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 4/5/2017.
 */

public interface YoutubeCommunicationManager {
    public void onResponse(List<String> playlistTitles, Map<Integer, List<VideoData>> videosMap, YoutubeCommunicationManager youtubeCommunicationManager);
    public void onError(UserRecoverableAuthIOException userRecoverableException);
    public void onError(Exception e);
}
