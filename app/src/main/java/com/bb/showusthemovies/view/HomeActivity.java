package com.bb.showusthemovies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.bb.showusthemovies.R;
import com.bb.showusthemovies.adapter.MovieAdapter;
import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.network.MovieRetrofitInstance;
import com.bb.showusthemovies.util.Constants;
import com.bb.showusthemovies.util.DebugLogger;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;

    List<MoviePageResult> movieResults;

    private MovieRetrofitInstance retrofitInstance =  new MovieRetrofitInstance();

    MovieAdapter movieAdapter;

    @BindView(R.id.movie_recyclerview)
    RecyclerView movieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);

        getMovies();

    }

    @OnClick(R.id.movie_poster_imageview)
    public void displayMovie(View view){
        Intent displayMovie = new Intent(this, DetailActivity.class);
        startActivity(displayMovie);
    }

    private void getMovies() {

        retrofitInstance.getMovies(Constants.API_KEY)
        .enqueue(new Callback<List<MoviePageResult>>() {
            @Override
            public void onResponse(Call<List<MoviePageResult>> call, Response<List<MoviePageResult>> response) {

                showMovies(response.body());

            }

            @Override
            public void onFailure(Call<List<MoviePageResult>> call, Throwable t) {

                DebugLogger.logError(new Exception(t));
            }
        });
    }

    private void showMovies(List<MoviePageResult> movieResults) {


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        MenuItem searchViewItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                movieAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
