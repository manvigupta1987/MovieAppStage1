package com.example.manvi.movieappstage1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private final String TAG =MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = new Bundle();
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(CONSTANTS.MOVIE_DETAIL))
        {
            bundle.putParcelable(CONSTANTS.MOVIE_DETAIL, intent.getParcelableExtra(CONSTANTS.MOVIE_DETAIL));
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            movieDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.container ,movieDetailFragment).commit();
        }
    }
}
