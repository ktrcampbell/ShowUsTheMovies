package com.bb.showusthemovies.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.model.Result;
import com.bb.showusthemovies.network.MovieRetrofitInstance;
import com.bb.showusthemovies.util.DebugLogger;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bb.showusthemovies.util.DebugLogger.logDebug;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<Result>> movieResultLiveData = new MutableLiveData<>();

    private MovieRetrofitInstance movieRetrofitInstance = new MovieRetrofitInstance();

    public MutableLiveData<List<Result>> getMovieList(String apiKey){

        logDebug("Call being made!");
        movieRetrofitInstance.getRecentMovies(apiKey)
                .enqueue(new Callback<MoviePageResult>() {
                    @Override
                    public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
                        if(response.isSuccessful() && response.body() != null){
                            movieResultLiveData.setValue(response.body().getResults());
                            logDebug("Got the results -> "+call.request().url());
                        }
                        else
                            logDebug("Empty List");

                    }

                    @Override
                    public void onFailure(Call<MoviePageResult> call, Throwable t) {
                        logDebug("Error : " +t.getLocalizedMessage());
                    }
                });

        return movieResultLiveData;
    }
}
