package com.example.manvi.movieappstage1;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by manvi on 1/3/17.
 */

public class MovieData implements Parcelable {

    private final String TAG = MovieData.class.getSimpleName();
    private String mPoster;
    private String mOverview;
    private String mReleaseDate;
    private String mTitle;
    private Double mVoteAverage;

    public MovieData(String title, String poster_path, String overview, String release_date, Double vote_count)
    {
        this.mTitle = title;
        this.mPoster = poster_path;
        this.mOverview = overview;
        this.mReleaseDate = release_date;
        this.mVoteAverage = vote_count;
    }

    //returns the complete path of the image poster.
    public String getPoster_path(Context context)
    {

        Resources res= context.getResources();
        if(mPoster!=null && !mPoster.equals(""))
        {
            String poster_path = res.getString(R.string.base_url_for_downloading_movie_poster) + res.getString(R.string.movie_poster_size) + mPoster;
            return (poster_path);
        }
        else
        {
            return null;
        }
    }

    public String getTitle()
    {
        return this.mTitle;
    }

    public String getOverview(){
        return this.mOverview;
    }

    public String getReleaseDate()
    {
        return this.mReleaseDate;
    }

    public Double getVoteCount()
    {
        return this.mVoteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private MovieData(Parcel in)
    {
        mTitle = in.readString();
        mPoster = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mVoteAverage = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mPoster);
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
        parcel.writeDouble(mVoteAverage);
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        public MovieData[] newArray(int size) {
            return new MovieData[size];

        }
    };
}
