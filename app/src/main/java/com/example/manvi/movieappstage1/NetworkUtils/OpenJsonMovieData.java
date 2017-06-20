package com.example.manvi.movieappstage1.NetworkUtils;

import android.text.TextUtils;

import com.example.manvi.movieappstage1.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by manvi on 1/3/17.
 */

public class OpenJsonMovieData {

    private static final String TAG = OpenJsonMovieData.class.getSimpleName();

    public static ArrayList<MovieData> getSimpleMovieDataFromJson(String movieJsonStr) throws JSONException
    {

        // If the JSON movie string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJsonStr)) {
            return null;
        }
                /* Movie information. Each Movie's info is an element of the "result" array */
        final String MD_RESULTS = "results";

        /* poster path is children of the "poster_path" object */
        final String MD_POSTER_PATH = "poster_path";

        /* overview of the movie */
        final String MD_OVERVIEW = "overview";
        /*release date of the movie */
        final String MD_RELEASE_DATE = "release_date";

        final String MD_TITLE = "title";
        final String MD_VOTE = "vote_average";

        ArrayList<MovieData> movieItemList = new ArrayList<>();

        JSONObject MovieJsonObject = new JSONObject(movieJsonStr);

        JSONArray movieJsonArray = MovieJsonObject.getJSONArray(MD_RESULTS);

        for (int i = 0; i < movieJsonArray.length(); i++) {
           /* Get the JSON object representing the day */
            JSONObject movieItem = movieJsonArray.getJSONObject(i);
            String poster_path = movieItem.getString(MD_POSTER_PATH);
            String release_date = movieItem.getString(MD_RELEASE_DATE);
            String overview = movieItem.getString(MD_OVERVIEW);
            String title = movieItem.getString(MD_TITLE);
            Double vote = Double.parseDouble(movieItem.getString(MD_VOTE));

            MovieData movieData = new MovieData(title,poster_path, overview, release_date, vote);
            movieItemList.add(movieData);
        }
        return movieItemList;
    }


}
