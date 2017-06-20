package com.example.manvi.movieappstage1;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.manvi.movieappstage1.NetworkUtils.NetworkCallUtils;
import com.example.manvi.movieappstage1.NetworkUtils.fetchMovieDataTask;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment implements MovieAdapter.ListItemClickListener,fetchMovieDataTask.NetworkListener {

    private final String TAG = MainActivityFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView mRecylerView;
    @BindView(R.id.error_text_view)
    TextView mErrorTextView;
    @BindView(R.id.pb_loading_bar)
    ProgressBar mLoadingBar;

    private MovieAdapter mMovieAdapter;
    private static String mLastSelectedSortOption = "";

    public MainActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Control whether a fragment instance is retained across Activity re-creation
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Resources res = getResources();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),res.getInteger(R.integer.grid_column_number));

        mRecylerView.setLayoutManager(gridLayoutManager);
        mRecylerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(getActivity(),this);
        if(savedInstanceState!=null && savedInstanceState.containsKey(CONSTANTS.MOVIE_DETAIL_LIST)) {

            ArrayList<MovieData> movieDatas = savedInstanceState.getParcelableArrayList(CONSTANTS.MOVIE_DETAIL_LIST);
            mMovieAdapter.setMovieListData(movieDatas);
        }
        else
        {
            if(NetworkCallUtils.isNetworkConnectionAvailable(getActivity())) {
                loadMovieData(CONSTANTS.POPULAR_MOVIE);
            }
            else
            {
                showErrorTextView();
                mErrorTextView.setText(getResources().getString(R.string.no_internet_message));
            }
        }
        mRecylerView.setAdapter(mMovieAdapter);
    }


    private void showJsonDataView()
    {
        mRecylerView.setVisibility(View.VISIBLE);
        mErrorTextView.setVisibility(View.INVISIBLE);
    }

    private void showErrorTextView()
    {
        mRecylerView.setVisibility(View.INVISIBLE);
        mErrorTextView.setVisibility(View.VISIBLE);
    }

    private void loadMovieData(String sortBy)
    {
        showJsonDataView();
        //To avoid the duplicate calls of Network calls when user clicks on the same Item again.
        if(mLastSelectedSortOption != sortBy) {
            new fetchMovieDataTask(getActivity(),this).execute(sortBy);
            mLastSelectedSortOption = sortBy;
        }
    }

    @Override
    public void onItemClicked(MovieData movie) {

        Intent intent = new Intent(getActivity(),MovieDetailsActivity.class);
        intent.putExtra(CONSTANTS.MOVIE_DETAIL,movie);
        startActivity(intent);
    }

    /*public class fetchMovieDataTask extends AsyncTask<String,Void,ArrayList<MovieData>>
    {
        @Override
        protected ArrayList<MovieData> doInBackground(String... strings) {
            String sortBy = strings[0];

            URL url = NetworkCallUtils.buildURL(sortBy);
            try{
                String jsonMoviesResponse = NetworkCallUtils.getResponseFromHttpUrl(url);
                ArrayList<MovieData> simpleJsonMovieData = OpenJsonMovieData.getSimpleMovieDataFromJson(jsonMoviesResponse);
                return simpleJsonMovieData;

            }catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }*/

        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingBar.setVisibility(View.VISIBLE);
        }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main,menu);

        //This is to show that when first time app is run, movie list is sorted by popular movies.
        if(mLastSelectedSortOption == CONSTANTS.POPULAR_MOVIE) {
            menu.findItem(R.id.sort_by_popular_movie).setChecked(true);
        }
        else
        {
            menu.findItem(R.id.sort_by_top_rated_movie).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // this is to show the checked item.
        if (item.isChecked()) {
            item.setChecked(false);
        }
        else {
            item.setChecked(true);
        }
        switch (id){
            case R.id.sort_by_popular_movie:
                loadMovieData(CONSTANTS.POPULAR_MOVIE);
                return true;
            case R.id.sort_by_top_rated_movie:
                loadMovieData(CONSTANTS.TOP_RATED_MOVIE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CONSTANTS.MOVIE_DETAIL_LIST, mMovieAdapter.getMovieListData());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPreExecuteListener(Context context) {
        mLoadingBar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onPostExecuteListener(Context context,ArrayList<MovieData> movieData) {
        mLoadingBar.setVisibility(View.INVISIBLE);
        if(movieData!=null)
        {
            showJsonDataView();
            mMovieAdapter.setMovieListData(movieData);
        }
        else{
            showErrorTextView();
        }
    }
}
