package com.bb.showusthemovies.view;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.bb.showusthemovies.R;
import com.bb.showusthemovies.adapter.MovieAdapter;
import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.network.MovieRetrofitInstance;
import com.bb.showusthemovies.util.Constants;
import com.bb.showusthemovies.util.DebugLogger;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {

    Toolbar toolbar;

    List<MoviePageResult> resultList = new ArrayList<>();

    private MovieRetrofitInstance retrofitInstance = new MovieRetrofitInstance();

    MovieAdapter movieAdapter;

    @BindView(R.id.movie_recyclerview)
    RecyclerView movieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieRecyclerView.setAdapter(new MovieAdapter(resultList, this));
        movieRecyclerView.addItemDecoration(itemDecoration);
        getMovies();

    }

    private void getMovies() {

        retrofitInstance.getMovies(Constants.API_KEY)
                .enqueue(new Callback<MoviePageResult>() {
                    @Override
                    public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {

                        List<MoviePageResult> list = new ArrayList<>();
                        list.add(response.body());
                        showMovies(list);
                        DebugLogger.logDebug("URL" + call.request().url());
                    }

                    @Override
                    public void onFailure(Call<MoviePageResult> call, Throwable t) {

                        DebugLogger.logError(new Exception(t));
                    }
                });
    }

    private void showMovies(List<MoviePageResult> resultList) {
        MovieAdapter adapter = new MovieAdapter(resultList, this);
        movieRecyclerView.setAdapter(adapter);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
    public void displayMovie(MoviePageResult moviePageResult) {

        Intent displayMovieIntent = new Intent(this, DetailActivity.class);
        displayMovieIntent.putExtra(Constants.MOVIE_KEY, (Serializable) moviePageResult);
        startActivity(displayMovieIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
