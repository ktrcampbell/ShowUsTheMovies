package com.bb.showusthemovies.view;

import androidx.appcompat.app.AppCompatActivity;


import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.bb.showusthemovies.R;
import com.bb.showusthemovies.adapter.MovieAdapter;
import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.model.Result;
import com.bb.showusthemovies.network.MovieRetrofitInstance;
import com.bb.showusthemovies.util.Constants;
import com.bb.showusthemovies.util.DebugLogger;
import com.bb.showusthemovies.viewmodel.MovieViewModel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;

    List<MoviePageResult> resultList = new ArrayList<>();

    private MovieViewModel viewModel;
    private Observer<MoviePageResult> listObserver;

    private MovieRetrofitInstance retrofitInstance = new MovieRetrofitInstance();

    MovieAdapter movieAdapter;

    private MovieListFragment listFragment = new MovieListFragment();

    @BindView(R.id.movie_recyclerview)
    RecyclerView movieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);

        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        listObserver = listResults -> displayInformation(listResults);

        viewModel.getMovieList(Constants.API_KEY)
                .observe(this, listObserver);

        //getMovies();

    }

    private void displayInformation(MoviePageResult listResults) {
        for (int i = 0; i < listResults.size() ; i++) {

        }
    }

//    private void getMovies() {
//
//        retrofitInstance.getRecentMovies(Constants.API_KEY)
//                .enqueue(new Callback<MoviePageResult>() {
//                    @Override
//                    public void onResponse(Call<MoviePageResult> call, Response<MoviePageResult> response) {
//
//                        if (response.isSuccessful() && response.body() != null && response.body().getResults() != null) {
//                            showMovies(response.body().getResults());
//                        } else {
//                            DebugLogger.logError(new Exception("Results or null or empty "));
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<MoviePageResult> call, Throwable t) {
//
//                        DebugLogger.logError(new Exception(t));
//                    }
//                });
//
//    }

    private void showMovies(List<Result> results) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.MOVIE_KEY, (Serializable) results);
        listFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.movie_list_frame, listFragment)
                .addToBackStack(listFragment.getTag())
                .commit();
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
