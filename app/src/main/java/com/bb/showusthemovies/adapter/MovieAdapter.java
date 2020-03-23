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
import com.bb.showusthemovies.model.Result;
import com.bb.showusthemovies.util.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {

    private List<Result> resultList;
    private List<Result> filterMovieList;

    private MovieClickListener movieClickListener;

    public MovieAdapter(List<Result> resultList, MovieClickListener movieClickListener){
        this.resultList = resultList;
        this.movieClickListener = movieClickListener;
        this.filterMovieList = new ArrayList<>(resultList);
    }

    public MovieAdapter(List<MoviePageResult> moviePageResults) {
    }

    public interface MovieClickListener{
        void displayMovie(Result moviePageResult);
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

        holder.titleTextView.setText(resultList.get(position).getTitle());
        holder.releaseDateTextView.setText(resultList.get(position).getReleaseDate());
//        String moviePoster = "";
        if (resultList.get(position).getPosterPath() == null) {
        holder.movieImageView.setImageResource(R.drawable.ic_movie);
    }else

    {
        Glide.with(holder.itemView.getContext())
                .load(Constants.BASE_IMAGE + resultList.get(position).getPosterPath())
                .into(holder.movieImageView);
    }
        holder.itemView.setOnClickListener(view ->{
            movieClickListener.displayMovie(resultList.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }


    @Override
    public Filter getFilter() {
        return listFilter;
    }

    final Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Result> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filterMovieList);
            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Result moviePageResult : filterMovieList) {
                    if (moviePageResult.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
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

        @BindView(R.id.title_textView)
        TextView titleTextView;

        @BindView(R.id.release_date_textView)
        TextView releaseDateTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
