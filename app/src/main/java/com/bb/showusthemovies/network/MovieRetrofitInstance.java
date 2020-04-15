package com.bb.showusthemovies.network;

import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRetrofitInstance {

    private static MovieService movieService;

    public MovieRetrofitInstance(){
        movieService = createService(createRetrofit());
    }

    private Retrofit createRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private MovieService createService(Retrofit retrofit){
        return retrofit.create(MovieService.class);
    }

    public Call<MoviePageResult> getRecentMovies(String apiKey){
        return movieService.getLatestMovie(Constants.API_KEY, Constants.SORT_BY);
    }
}
