package com.anb.madesub_3.feature.tvshow_detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anb.madesub_3.R;
import com.anb.madesub_3.data.model.TVShowDetail;
import com.anb.madesub_3.utils.Utils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TVShowDetailActivity extends AppCompatActivity {
    public static final String EXTRA_TVSHOW_ID = "extra_tvshow_id";

    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.iv_poster) ImageView iv_poster;
    @BindView(R.id.iv_backdrop_poster) ImageView iv_backdrop_poster;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.tv_genres) TextView tv_genres;
    @BindView(R.id.tv_overview) TextView tv_overview;
    @BindView(R.id.tv_first_air_date) TextView tv_first_air_date;
    @BindView(R.id.tv_last_air_date) TextView tv_last_air_date;
    @BindView(R.id.tv_vote_average) TextView tv_vote_average;

    private TVShowDetailViewModelFactory viewModelFactory;
    private TVShowDetailViewModel viewModel;
    private Observer<TVShowDetail> observerTVShow;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressBar.setVisibility(View.VISIBLE);

        id = Objects.requireNonNull(getIntent().getExtras()).getString(EXTRA_TVSHOW_ID);
        viewModelFactory = new TVShowDetailViewModelFactory(this, id);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TVShowDetailViewModel.class);
        observerTVShow = tvShowDetail -> {
            progressBar.setVisibility(View.GONE);
            if (tvShowDetail != null){
                collapsingToolbarLayout.setTitle(tvShowDetail.getName());
                tv_title.setText(tvShowDetail.getName());
                tv_genres.setText(tvShowDetail.genreToString());
                tv_first_air_date.setText(tvShowDetail.getFirst_air_date());
                tv_last_air_date.setText(tvShowDetail.getLast_air_date());
                tv_overview.setText(tvShowDetail.getOverview());
                tv_vote_average.setText("Rating " + tvShowDetail.getVote_average());
                Utils.setBackdropImage(tvShowDetail.getBackdrop_path(), iv_backdrop_poster);
                Utils.setImage(tvShowDetail.getPoster_path(), iv_poster);
            }
        };
        viewModel.getMovieResponse().observe(this, observerTVShow);
    }
}
