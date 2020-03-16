package com.bb.showusthemovies.network;

import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/movie/latest")
    Call<List<MoviePageResult>>getLatestMovie(@Query(Constants.API_KEY) String userkey);

    @GET("/configuration")
    Call<List<MoviePageResult>>getConfigMovie(@Query(Constants.API_KEY) String userkey);

    @GET("/movie/{movie_id}")
    Call<List<MoviePageResult>>getOneMovie(@Path("id") int movieId, @Query(Constants.API_KEY) String userkey);

}

