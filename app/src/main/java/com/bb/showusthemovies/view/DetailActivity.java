package com.bb.showusthemovies.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bb.showusthemovies.R;
import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.util.Constants;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_KEY = "get.info.movie";

    @BindView(R.id.detail_imageView)
    ImageView movieImageView;

    @BindView(R.id.detail_title_textview)
    TextView titleTextView;

    @BindView(R.id.detail_genre_textview)
    TextView genreTextView;

    @BindView(R.id.detail_release_year_textview)
    TextView releaseDateTextView;

    @BindView(R.id.detail_overview_textview)
    TextView overviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_layout);
        ButterKnife.bind(this);

        MoviePageResult displayMovie = ((MoviePageResult) getIntent().getSerializableExtra(MOVIE_KEY));
        titleTextView.setText(displayMovie.getTitle());
//        genreTextView.setText(displayMovie.(String.valueOf(getGenres());
        releaseDateTextView.setText(displayMovie.getReleaseDate());
        overviewTextView.setText(displayMovie.getOverview());
        Glide.with(this)
                .load(Constants.BASE_IMAGE + displayMovie.getPosterPath())
                .into(movieImageView);

    }
}
