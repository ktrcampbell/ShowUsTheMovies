package com.bb.showusthemovies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bb.showusthemovies.R;
import com.bb.showusthemovies.model.MoviePageResult;
import com.bb.showusthemovies.util.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {

    private List<MoviePageResult> resultList;
    private List<MoviePageResult> filterMovieList;

    private MovieClickListener movieClickListener;

    public MovieAdapter(List<MoviePageResult> resultList, MovieClickListener movieClickListener){
        this.resultList = resultList;
        this.movieClickListener = movieClickListener;
        this.filterMovieList = new ArrayList<>(resultList);
    }

    public interface MovieClickListener{
        void displayMovie(MoviePageResult moviePageResult);
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {

        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + itemView.getPosterPath())
                .into(holder.movieImageView);

        holder.itemView.setOnClickListener(view ->{
            movieClickListener.displayMovie(resultList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public void setResultList(List<MoviePageResult> movieList){
        this.resultList.clear();
        this.resultList.addAll(movieList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    final Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<MoviePageResult> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filterMovieList);
            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MoviePageResult moviePageResult : filterMovieList) {
                    if (moviePageResult.toLowercase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(moviePageResult);
                    }
                }
            }
            FilterResults mResults = new FilterResults();
            mResults.values = filteredList;
            return mResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            resultList.clear();
            resultList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class MovieViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.movie_poster_imageview)
        ImageView movieImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
