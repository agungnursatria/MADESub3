package com.anb.madesub_3.feature.movie_detail;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.anb.madesub_3.data.api.RetroServer;
import com.anb.madesub_3.data.model.MovieDetail;
import com.anb.madesub_3.data.model.ReviewResponse;
import com.anb.madesub_3.data.model.VideoResponse;
import com.anb.madesub_3.utils.Constant;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailViewModel extends ViewModel {

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<MovieDetail> movieResponse = new MutableLiveData<>();
    private final MutableLiveData<ReviewResponse> reviewResponse = new MutableLiveData<>();
    private final MutableLiveData<VideoResponse> videoResponse = new MutableLiveData<>();
    private Context context;

    MovieDetailViewModel(Context context, String id){
        this.context = context;
        loadMovie(id);
        loadReviews(id);
        loadVideos(id);
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }

    MutableLiveData<MovieDetail> getMovieResponse() {
        return movieResponse;
    }

    MutableLiveData<ReviewResponse> getReviewResponse() {
        return reviewResponse;
    }

    MutableLiveData<VideoResponse> getVideoResponse() {
        return videoResponse;
    }

    private void onErrorMovie(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        movieResponse.setValue(null);
    }
    private void setDataMovie(MovieDetail movieDetail) {
        movieResponse.setValue(movieDetail);
    }
    private void onErrorVideos(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        videoResponse.setValue(null);
    }
    private void setDataVideos(VideoResponse videoResponse) {
        this.videoResponse.setValue(videoResponse);
    }
    private void onErrorReviews(Throwable e) {
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        reviewResponse.setValue(null);
    }
    private void setDataReviews(ReviewResponse reviews) {
        this.reviewResponse.setValue(reviews);
    }

    void loadMovie(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieDetail(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataMovie, this::onErrorMovie)
        );
    }

    void loadVideos(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieVideos(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataVideos, this::onErrorVideos)
        );
    }

    void loadReviews(String id){
        disposable.add(
                RetroServer
                        .getRequestService()
                        .getMovieReviews(id, Constant.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setDataReviews, this::onErrorReviews)
        );
    }

}
