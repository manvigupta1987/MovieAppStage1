package com.example.manvi.movieappstage1.NetworkUtils;

/**
 * Created by manvi on 6/3/17.
 */

import android.content.Context;
import android.os.AsyncTask;

import com.example.manvi.movieappstage1.MovieData;

import java.net.URL;
import java.util.ArrayList;


/**
 * Created by manvi on 6/3/17.
 */

public class fetchMovieDataTask extends AsyncTask<String,Void,ArrayList<MovieData>> {

    private NetworkListener mNetworkListener;
    private Context mContext;

    //Constructor
    public fetchMovieDataTask(Context context, NetworkListener networkListener) {
        this.mContext = context;
        this.mNetworkListener = networkListener;
    }

    public interface NetworkListener{
        public void onPreExecuteListener(Context context);
        public void onPostExecuteListener(Context context, ArrayList<MovieData> movieData);
    }
    @Override
    protected ArrayList<MovieData> doInBackground(String... strings) {
        String sortBy = strings[0];

        URL url = NetworkCallUtils.buildURL(sortBy);
        try {
            String jsonMoviesResponse = NetworkCallUtils.getResponseFromHttpUrl(url);
            ArrayList<MovieData> simpleJsonMovieData = OpenJsonMovieData.getSimpleMovieDataFromJson(jsonMoviesResponse);
            return simpleJsonMovieData;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mNetworkListener.onPreExecuteListener(mContext);
    }

    @Override
    protected void onPostExecute(ArrayList<MovieData> movieDatas) {
        super.onPostExecute(movieDatas);
        mNetworkListener.onPostExecuteListener(mContext,movieDatas);
    }
}