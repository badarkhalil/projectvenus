package com.app.venustvsmmhuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.SearchView;


public class SearchActivity extends BaseActivity implements Filterable {

    private SearchView searchView;
    private GridView mGridView;
    private ValueFilter valueFilter;
    private VideoListItemDetailModel allVideosModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initHeader();

        allVideosModel = (VideoListItemDetailModel) getIntent().getExtras().getSerializable("videoListModel");
        init(allVideosModel);

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search");

        searchView.setFocusableInTouchMode(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getFilter().filter(newText);
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void init(final VideoListItemDetailModel model) {
        mGridView = (GridView) findViewById(R.id.search_videos);

        ViewAllAdapter viewAllAdapter = new ViewAllAdapter(SearchActivity.this,
                model.getTitleList(),
                model.getThumnailUrlList());
        mGridView.setAdapter(viewAllAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, YoutubePlayerActivity.class);
                intent.putExtra("videoId", model.getVideoIdList().get(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        VideoListItemDetailModel searchVideosModel;

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                searchVideosModel = new VideoListItemDetailModel(allVideosModel);

                int i = 0;
                while (i < searchVideosModel.getTitleList().size()) {
                    if (!(searchVideosModel.getTitleList().get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        searchVideosModel.getTitleList().remove(i);
                        searchVideosModel.getThumnailUrlList().remove(i);
                        searchVideosModel.getVideoIdList().remove(i);
                    } else {
                        i++;
                    }
                }

                results.count = searchVideosModel.getTitleList().size();
                results.values = searchVideosModel;
            } else {
                searchVideosModel = new VideoListItemDetailModel(allVideosModel);

                results.count = 0;
                results.values = searchVideosModel;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            init(searchVideosModel);
        }
    }
}
