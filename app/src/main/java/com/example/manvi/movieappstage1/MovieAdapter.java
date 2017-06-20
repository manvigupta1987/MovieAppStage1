package com.example.manvi.movieappstage1;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by manvi on 1/3/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<MovieData> mMovieList;
    private final String TAG = MovieAdapter.class.getSimpleName();

    private Context mContext;
    private ListItemClickListener mlistItemClickListener;

    MovieAdapter(Context context, ListItemClickListener listItemClickListener){
        mContext = context;
        mlistItemClickListener = listItemClickListener;
    }

    public interface ListItemClickListener{
        public void onItemClicked(MovieData movie);
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_list_item, viewGroup,false);
        MovieAdapterViewHolder movieAdapterViewHolder = new MovieAdapterViewHolder(view);
        return movieAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position)
    {
        String poster_path = mMovieList.get(position).getPoster_path(mContext);
        if(poster_path!=null) {
            //RGB_565 is used for the memory optimization. R plane spends 5 bit per pixel instead of 8 bits. Same applies to other plane.
            Picasso.with(mContext).load(poster_path).config(Bitmap.Config.RGB_565).into(holder.mMovieImage);
        }
    }


    @Override
    public int getItemCount() {
        if(mMovieList == null)
        {
            return 0;
        }
        return mMovieList.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.movie_image) ImageView mMovieImage;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieData movieData = mMovieList.get(adapterPosition);
            mlistItemClickListener.onItemClicked(movieData);
        }
    }

    public void setMovieListData(ArrayList<MovieData> movieData)
    {
        mMovieList = movieData;
        notifyDataSetChanged();
    }

    public ArrayList<MovieData> getMovieListData()
    {
        return mMovieList;
    }
}
