package com.anb.madesub_3.data.api;

import com.anb.madesub_3.data.model.MovieDetail;
import com.anb.madesub_3.data.model.MovieResponse;
import com.anb.madesub_3.data.model.ReviewResponse;
import com.anb.madesub_3.data.model.TVShowDetail;
import com.anb.madesub_3.data.model.TVShowResponse;
import com.anb.madesub_3.data.model.VideoResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("discover/movie")
    Observable<MovieResponse> getMovie(
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("discover/tv")
    Observable<TVShowResponse> getTVShow(
            @Query("api_key") String api_key,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Observable<MovieDetail> getMovieDetail(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}/videos")
    Observable<VideoResponse> getMovieVideos(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}/reviews")
    Observable<ReviewResponse> getMovieReviews(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("tv/{tv_id}")
    Observable<TVShowDetail> getTVShowDetail(
            @Path("tv_id") String tv_id,
            @Query("api_key") String api_key
    );
}
