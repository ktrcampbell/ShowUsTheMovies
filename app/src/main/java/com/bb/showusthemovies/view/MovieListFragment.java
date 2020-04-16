package com.bb.showusthemovies.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.showusthemovies.R;
import com.bb.showusthemovies.adapter.MovieAdapter;
import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.model.Result;
import com.bb.showusthemovies.util.Constants;
import com.bb.showusthemovies.viewmodel.MovieViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bb.showusthemovies.util.DebugLogger.logDebug;

public class MovieListFragment extends Fragment implements MovieAdapter.MovieClickListener {

    @BindView(R.id.movie_recyclerview)
    RecyclerView movieRecyclerView;

    private MovieViewModel viewModel;
    private Observer<List<Result>> listObserver;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.movie_framgent_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        logDebug("Got the movies ViewCreated");
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        listObserver = listResults -> displayInformation(listResults);
        viewModel.getMovieList(Constants.API_KEY).observe(this, listObserver);


    }

    private void displayInformation(List<Result> listResults) {
        logDebug("Got the movies " + listResults.size());
        MovieAdapter movieAdapter = new MovieAdapter(listResults, this);
        movieRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieRecyclerView.setAdapter(movieAdapter);
    }


    @Override
    public void displayMovie(Result movieItem) {
        Intent displayMovieIntent = new Intent(getContext(), DetailActivity.class);
        displayMovieIntent.putExtra(Constants.MOVIE_KEY, movieItem);
        startActivity(displayMovieIntent);
    }

}
