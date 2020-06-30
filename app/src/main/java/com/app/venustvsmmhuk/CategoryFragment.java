package com.app.venustvsmmhuk;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class CategoryFragment extends Fragment {

    private final String PREF_ACCOUNT_NAME = "accountName";
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private GoogleAccountCredential credential;
    private String mChosenAccountName;
    private List<String> playlistTitles;
    private Map<Integer, List<VideoData>> videosMap;

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        ((BaseActivity) getActivity()).initHeader();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.categorylist);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        credential = GoogleAccountCredential.usingOAuth2(getActivity(), Arrays.asList(Constants.SCOPES));

//        if (haveGooglePlayServices()) {
            request();
//        } else {
//            chooseAccount();
//        }

        return view;
    }

    void request() {
        ((BaseActivity) getActivity()).showLoading();
//        credential.setSelectedAccountName(mChosenAccountName);

        new HttpAsyncTask(new YoutubeCommunicationManager() {
            @Override
            public void onResponse(List<String> titles, Map<Integer, List<VideoData>> videos, YoutubeCommunicationManager youtubeCommunicationManager) {
                ((BaseActivity) getActivity()).hideLoading();

                playlistTitles = titles;
                videosMap = videos;

                mAdapter = new CategorylistAdapter(getActivity(), playlistTitles, videosMap);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onError(UserRecoverableAuthIOException userRecoverableException) {
                startActivityForResult(userRecoverableException.getIntent(), 1);
            }

            @Override
            public void onError(Exception e) {
                ((BaseActivity) getActivity()).hideLoading();
                e.printStackTrace();
            }
        }, credential, getActivity()).execute();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK && data != null && data.getExtras() != null) {
                    mChosenAccountName = data.getExtras().getString(AccountManager.KEY_ACCOUNT_NAME);
                    if (mChosenAccountName != null) {
                        SharedPreferences settings =
                                getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, mChosenAccountName);
                        editor.apply();
                        request();
                    }
                }
                break;
        }
    }

    private boolean haveGooglePlayServices() {
        mChosenAccountName = getActivity().getPreferences(Context.MODE_PRIVATE)
                .getString(PREF_ACCOUNT_NAME, null);
//        mChosenAccountName = credential.getSelectedAccountName();
        if (mChosenAccountName == null) {
            return false;
        } else {
            return true;
        }
    }

    private void chooseAccount() {
        startActivityForResult(credential.newChooseAccountIntent(), 1);
    }


    public void search() {
        if (videosMap != null) {
            List<String> thumnailUrl = new ArrayList<String>();
            List<String> title = new ArrayList<String>();
            List<String> videoId = new ArrayList<String>();

            for (int i = 0; i < videosMap.size(); i++) {
                for (VideoData videoData : videosMap.get(i)) {
                    thumnailUrl.add(videoData.getVideo().getSnippet().getThumbnails().getHigh().getUrl());
                    title.add(videoData.getTitle());
                    videoId.add(videoData.getVideo().getId());
                }
            }

            VideoListItemDetailModel model = new VideoListItemDetailModel();
            model.setThumnailUrlList(thumnailUrl);
            model.setTitleList(title);
            model.setVideoIdList(videoId);

            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtra("videoListModel", model);
            startActivity(intent);
        }
    }
}
