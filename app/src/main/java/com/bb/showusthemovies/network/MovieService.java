package com.bb.showusthemovies.network;

import com.bb.showusthemovies.model.MoviePageResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("/3/discover/movie?language=en-US&sort_by=popularity.desc")
    Call<List<MoviePageResult>>getLatestMovie(@Query("api_key") String apiKey, @Query("sort_by") String sortBy);



}

