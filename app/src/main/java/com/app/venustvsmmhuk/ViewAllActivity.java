package com.app.venustvsmmhuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class ViewAllActivity extends BaseActivity {

    private GridView mGridView;
    private VideoListItemDetailModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        initHeader();

        model = (VideoListItemDetailModel) getIntent().getExtras().getSerializable("videoListModel");

        mGridView = (GridView) findViewById(R.id.view_all_videos);

        ViewAllAdapter viewAllAdapter = new ViewAllAdapter(this, model.getTitleList(), model.getThumnailUrlList());
        mGridView.setAdapter(viewAllAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ViewAllActivity.this, YoutubePlayerActivity.class);
                intent.putExtra("videoId", model.getVideoIdList().get(i));
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
