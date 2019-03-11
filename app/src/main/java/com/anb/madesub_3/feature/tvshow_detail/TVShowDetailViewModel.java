package com.anb.madesub_3.feature.tvshow_detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

import com.anb.madesub_3.data.api.RetroServer;
import com.anb.madesub_3.data.model.MovieDetail;
import com.anb.madesub_3.data.model.ReviewResponse;
import com.anb.madesub_3.data.model.TVShowDetail;
import com.anb.madesub_3.data.model.VideoResponse;
import com.anb.madesub_3.utils.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetailViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<TVShowDetail> tvshowResponse = new MutableLiveData<>();
    private Context context;

    TVShowDetailViewModel(Context context, String id){
        this.context = context;
        loadTVShow(id);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    MutableLiveData<TVShowDetail> getMovieResponse() {
        return tvshowResponse;
    }

    private void onErrorMovie(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        tvshowResponse.setValue(null);
    }
    private void setDataMovie(TVShowDetail tvShowDetail) {
        tvshowResponse.setValue(tvShowDetail);
    }

    void loadTVShow(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getTVShowDetail(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataMovie, this::onErrorMovie)
        );
    }
}
