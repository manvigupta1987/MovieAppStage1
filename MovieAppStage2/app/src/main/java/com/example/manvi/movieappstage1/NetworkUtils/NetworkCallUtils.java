package com.example.manvi.movieappstage1.NetworkUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.example.manvi.movieappstage1.BuildConfig;
import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by manvi on 1/3/17.
 */

public final class NetworkCallUtils {

    private final static String TAG = NetworkCallUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String  API_KEY_PARAM = "api_key";

    /**
     * Builds the URL for popular movies which is used to talk to the moviedb server.
     *
     * @return The URL to use to query the weather server.
     */
    public static URL buildURL(String sortBy)
    {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                          .appendPath(sortBy)
                          .appendQueryParameter(API_KEY_PARAM,BuildConfig.API_KEY)
                          .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());

        }catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("//A");

            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                return (scanner.next());
            } else {
                return null;
            }
        }finally {
            httpURLConnection.disconnect();
        }
    }

    /**Function checks for the network connection. If network connection exists
     * return true else false;
     * @param context
     * @return: true or false
     */
    public static boolean isNetworkConnectionAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null && activeNetwork.isConnectedOrConnecting())
        {
            return true;
        }
        else{
            return false;
        }
    }
}
