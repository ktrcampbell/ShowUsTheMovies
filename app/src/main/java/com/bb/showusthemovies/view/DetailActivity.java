package com.bb.showusthemovies.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bb.showusthemovies.R;
import com.bb.showusthemovies.model.Result;
import com.bb.showusthemovies.util.Constants;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bb.showusthemovies.util.Constants.MOVIE_KEY;

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.detail_imageView)
    ImageView movieImageView;

    @BindView(R.id.detail_title_textview)
    TextView titleTextView;

    @BindView(R.id.detail_release_year_textview)
    TextView releaseDateTextView;

    @BindView(R.id.detail_overview_textview)
    TextView overviewTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_layout);
        ButterKnife.bind(this);


        Result displayMovie = ((Result) getIntent().getSerializableExtra(MOVIE_KEY));
        if(displayMovie != null) {
            titleTextView.setText(displayMovie.getTitle());
            releaseDateTextView.setText(displayMovie.getReleaseDate());
            overviewTextView.setText(displayMovie.getOverview());
            Glide.with(this)
                    .load(Constants.BASE_IMAGE + displayMovie.getPosterPath())
                    .into(movieImageView);
        }
    }

    @OnClick(R.id.close_details_imageview)
    public void closeMovieDetails(View view) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

}
