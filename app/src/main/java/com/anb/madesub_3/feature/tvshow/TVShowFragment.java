package com.anb.madesub_3.feature.tvshow;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.anb.madesub_3.R;
import com.anb.madesub_3.data.model.TVShow;
import com.anb.madesub_3.data.model.TVShowResponse;
import com.anb.madesub_3.feature.tvshow_detail.TVShowDetailActivity;
import com.anb.madesub_3.feature.tvshow.adapter.TVShowAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment implements TVShowAdapter.OnItemClickListener{

    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.progress_bar) ProgressBar progressBar;

    private Context context;
    private TVShowAdapter adapter;
    private ArrayList<TVShow> list = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private TVShowViewModelFactory viewModelFactory;
    private TVShowViewModel viewModel;
    private Observer<TVShowResponse> observer;

    public TVShowFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        context = view.getContext();
        viewModelFactory = new TVShowViewModelFactory(context);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new TVShowAdapter(context, this);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TVShowViewModel.class);
        observer = tvShowResponse -> {
            progressBar.setVisibility(View.GONE);
            list = tvShowResponse.getResults();
            setLayoutManager(context);
            adapter.setListMovie(list);
            rv.setAdapter(adapter);
        };
        viewModel.getResponse().observe(getViewLifecycleOwner(), observer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.getResponse().removeObserver(observer);
    }

    @Override
    public void onItemClick(TVShow tvShow) {
        Intent intent = new Intent(context, TVShowDetailActivity.class);
        intent.putExtra(TVShowDetailActivity.EXTRA_TVSHOW_ID, tvShow.getId());
        startActivity(intent);
    }

    public void setLayoutManager(Context context) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(context, 2);
        } else {
            gridLayoutManager = new GridLayoutManager(context, 4);
        }
        rv.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setLayoutManager(context);
        super.onConfigurationChanged(newConfig);
    }
}
