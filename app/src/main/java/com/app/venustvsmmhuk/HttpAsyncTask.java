package com.app.venustvsmmhuk;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.io.BaseEncoding;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 3/31/2017.
 */

public class HttpAsyncTask extends AsyncTask<String, Integer, Exception> {

    Map<Integer, List<VideoData>> videosMap = new HashMap<Integer, List<VideoData>>();
    private GoogleAccountCredential credential;
    private YoutubeCommunicationManager youtubeCommunicationManager;
    private Context context;
    private List<String> playlistTitles = new ArrayList<String>();


    public HttpAsyncTask(YoutubeCommunicationManager youtubeCommunicationManager, GoogleAccountCredential credential, Context context) {
        this.youtubeCommunicationManager = youtubeCommunicationManager;
        this.context = context;
        this.credential = credential;
    }

    @Override
    protected Exception doInBackground(String... param) {

        final YouTube youtube = new YouTube.Builder(new NetHttpTransport(),
                new JacksonFactory(), new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
//                request.getHeaders().set("key", Constants.YOUTUBE_API_KEY);
//                request.getHeaders().set("Key", Constants.YOUTUBE_API_KEY);
//                request.getHeaders().set("X-Android-Key", Constants.YOUTUBE_API_KEY);;
//                request.getHeaders().set("X-Android-Package", context.getPackageName());
//                request.getHeaders().set("X-Ios-Bundle-Identifier", context.getPackageName());
//                request.getHeaders().set("X-Android-Cert", getSHA1(context.getPackageName()));
//                request.getHeaders().set("Referer", ".dummyvenus.com/");
            }
        }).setApplicationName(context.getString(R.string.app_name)).build();

//        YouTube youtube = new YouTube.Builder(new NetHttpTransport(),
//                new JacksonFactory(), credential).setApplicationName(context.getString(R.string.app_name)).build();

        try {
            YouTube.Playlists.List query = youtube.playlists().list("id,snippet");
            query.set("key", Constants.YOUTUBE_API_KEY);
            query.setKey(Constants.YOUTUBE_API_KEY);
            query.setChannelId(Constants.YOUTUBE_CHANNEL_KEY);
            query.setMaxResults((long) 25);

            PlaylistListResponse plr = query.execute();

            List<String> playlistIds = new ArrayList<String>();
            List<PlaylistItemListResponse> pilrs = new ArrayList<PlaylistItemListResponse>();
            List<VideoListResponse> vlrs = new ArrayList<VideoListResponse>();

            Map<Integer, List<String>> videoIdsMap = new HashMap<Integer, List<String>>();

            for (int i = 0; i < plr.getItems().size(); i++) {
                playlistIds.add(i, plr.getItems().get(i)
                        .getId());

                playlistTitles.add(i, plr.getItems().get(i)
                        .getSnippet().getTitle());

                pilrs.add(i, youtube.playlistItems()
                        .list("id,contentDetails")
                        .set("key", Constants.YOUTUBE_API_KEY)
                        .setKey(Constants.YOUTUBE_API_KEY)
                        .setPlaylistId(playlistIds.get(i))
                        .setMaxResults(20l).execute());

                List<String> videoIds = new ArrayList<String>();

                for (PlaylistItem item : pilrs.get(i).getItems()) {
                    videoIds.add(item.getContentDetails().getVideoId());
                }
                videoIdsMap.put(i, videoIds);

                vlrs.add(i, youtube.videos()
                        .list("id,snippet,status")
                        .set("key", Constants.YOUTUBE_API_KEY)
                        .setKey(Constants.YOUTUBE_API_KEY)
                        .setId(TextUtils.join(",", videoIdsMap.get(i))).execute());

                List<VideoData> videos = new ArrayList<VideoData>();

                for (Video video : vlrs.get(i).getItems()) {
                    if ("public".equals(video.getStatus()
                            .getPrivacyStatus())) {
                        VideoData videoData = new VideoData();
                        videoData.setVideo(video);
                        videos.add(videoData);
                    }
                }

                Collections.sort(videos, new Comparator<VideoData>() {
                    @Override
                    public int compare(VideoData videoData,
                                       VideoData videoData2) {
                        return videoData.getTitle().compareTo(
                                videoData2.getTitle());
                    }
                });

                videosMap.put(i, videos);
            }
        } catch (Exception e) {
            return e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Exception e) {
        if (e != null) {
            if (e instanceof UserRecoverableAuthIOException) {
                youtubeCommunicationManager.onError((UserRecoverableAuthIOException) e);
            } else {
                youtubeCommunicationManager.onError(e);
            }
        } else {
            youtubeCommunicationManager.onResponse(playlistTitles, videosMap, youtubeCommunicationManager);
        }
        return;
    }

    private String getSHA1(String packageName){
        try {
            Signature[] signatures = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            for (Signature signature: signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                Log.i("SHA1", BaseEncoding.base16().encode(md.digest()));
                return BaseEncoding.base16().encode(md.digest());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
