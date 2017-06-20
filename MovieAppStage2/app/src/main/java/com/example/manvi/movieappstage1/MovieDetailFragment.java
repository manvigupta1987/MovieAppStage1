package com.example.manvi.movieappstage1;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    @BindView(R.id.image_view) ImageView mImageView;
    @BindView(R.id.movie_title)TextView mTitleTextView;
    @BindView(R.id.movie_plot)TextView mOverview;
    @BindView(R.id.movie_releaseDate)TextView mReleaseDate;
    @BindView(R.id.movie_rating)TextView mRating;

    @BindDrawable(R.drawable.no_image_found)
    Drawable error;

    private Unbinder unbinder;

    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        unbinder = ButterKnife.bind(this,rootView);

        if(getArguments().containsKey(CONSTANTS.MOVIE_DETAIL))
        {
            MovieData movieData = getArguments().getParcelable(CONSTANTS.MOVIE_DETAIL);
            mTitleTextView.setText(movieData.getTitle());
            mOverview.setText(movieData.getOverview());
            mReleaseDate.setText(movieData.getReleaseDate());
            mRating.setText(movieData.getVoteCount().toString()+"/10");
            //changes to avoid crashing down due to empty string values or null values.
            Picasso.with(getActivity()).load(movieData.getPoster_path(getActivity())).placeholder(error).error(error).config(Bitmap.Config.RGB_565).into(mImageView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
