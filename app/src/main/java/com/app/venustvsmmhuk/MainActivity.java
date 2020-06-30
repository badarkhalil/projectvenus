package com.app.venustvsmmhuk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener, YoutubeVideoInterface, ViewAllInterface {

    private ImageView home, live, categories, contact;
    private CategoryFragment categoryFragment;
    private HomeFragment homeFragment;
    private LiveFragment liveFragment;
    private ContactFragment contactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        home = (ImageView) findViewById(R.id.home);
        live = (ImageView) findViewById(R.id.live);
        categories = (ImageView) findViewById(R.id.categories);
        contact = (ImageView) findViewById(R.id.contact);

        home.setOnClickListener(this);
        live.setOnClickListener(this);
        categories.setOnClickListener(this);
        contact.setOnClickListener(this);

        onClick(home);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebook:
                openPageInsideBrowser("https://www.facebook.com/venustvsky805");
                break;
            case R.id.twitter:
                openPageInsideBrowser("https://www.twitter.com/Venus805Tv");
                break;
            case R.id.youtube:
                openPageInsideBrowser("http://www.youtube.com/c/VenusTv805");
                break;
            case R.id.search:
                categoryFragment.search();
                break;
            case R.id.home:
                home.setImageResource(R.drawable.homehover);
                live.setImageResource(R.drawable.live);
                categories.setImageResource(R.drawable.catchup);
                contact.setImageResource(R.drawable.feedback);

                homeFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, homeFragment).commit();
                break;
            case R.id.live:
                home.setImageResource(R.drawable.home);
                live.setImageResource(R.drawable.liveblack);
                categories.setImageResource(R.drawable.catchup);
                contact.setImageResource(R.drawable.feedback);

                liveFragment = new LiveFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, liveFragment).commit();
                break;
            case R.id.categories:
                home.setImageResource(R.drawable.home);
                live.setImageResource(R.drawable.live);
                categories.setImageResource(R.drawable.catchupblack);
                contact.setImageResource(R.drawable.feedback);

                categoryFragment = new CategoryFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, categoryFragment).commit();
                break;
            case R.id.contact:
                home.setImageResource(R.drawable.home);
                live.setImageResource(R.drawable.live);
                categories.setImageResource(R.drawable.catchup);
                contact.setImageResource(R.drawable.feedbackhover);

                contactFragment = new ContactFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, contactFragment).commit();
                break;
        }
    }

    public void openPageInsideBrowser(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void playVideo(String videoId) {
        Intent intent = new Intent(MainActivity.this, YoutubePlayerActivity.class);
        intent.putExtra("videoId", videoId);
        startActivity(intent);
    }

    @Override
    public void viewAllVideos(List<VideoData> videosList) {
        List<String> thumnailUrl = new ArrayList<String>();
        List<String> title = new ArrayList<String>();
        List<String> videoId = new ArrayList<String>();

        for (VideoData videoData : videosList) {
            thumnailUrl.add(videoData.getVideo().getSnippet().getThumbnails().getHigh().getUrl());
            title.add(videoData.getTitle());
            videoId.add(videoData.getVideo().getId());
        }

        VideoListItemDetailModel model = new VideoListItemDetailModel();
        model.setThumnailUrlList(thumnailUrl);
        model.setTitleList(title);
        model.setVideoIdList(videoId);

        Intent intent = new Intent(MainActivity.this, ViewAllActivity.class);
        intent.putExtra("videoListModel", model);
        startActivity(intent);
    }
}
