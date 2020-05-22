package com.example.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.model.MovieJsonData;
import com.example.moviesapp.model.Result;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private static final String TAG = "MovieAdapter";
    private MovieJsonData movieJsonData;
    private Context context;
    private ClickListener clickListener;
    private List<Result> results;

    public interface ClickListener{
       void onItemClickListener(int itemId);
    }

    public MovieAdapter(MovieJsonData movieJsonData, ClickListener clickListener){
        this.movieJsonData = movieJsonData;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        //Initializing a Layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_view_holder, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {

        results = movieJsonData.getResults();
        String url = "http://image.tmdb.org/t/p/w185" + results.get(position).getPosterPath();

        Glide.with(context)
                .load(url)
                .into(holder.moviePoster);

        if(results.get(position).getTitle() != null) {
            holder.movieName.setText(results.get(position).getTitle());
        }
        else{
            holder.movieName.setText(results.get(position).getOriginalName());
        }
    }

    @Override
    public int getItemCount() {
        return movieJsonData.getResults().size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView moviePoster;
        TextView movieName;
        CardView cardView;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            movieName = itemView.findViewById(R.id.movie_name);
            cardView = itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int itemId = results.get(getAdapterPosition()).getId();
            clickListener.onItemClickListener(itemId);
        }
    }
}
