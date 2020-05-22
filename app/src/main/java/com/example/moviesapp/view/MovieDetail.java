package com.example.moviesapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviesapp.R;
import com.example.moviesapp.moduleController.MovieDetailsModule;
import com.example.moviesapp.presenter.MovieDetailsPresenter;

public class MovieDetail extends AppCompatActivity implements MovieDetailsModule.DetailsView{
    private static final String ID_KEY = "movie_id";
    private int movie_id;

    ImageView moviePosterDetail;
    TextView originalName, userRating, releaseDate, plotSummary;
    ProgressBar progressBar;

    MovieDetailsPresenter movieDetailsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent = getIntent();
        movie_id = intent.getIntExtra(ID_KEY, 0);

        movieDetailsPresenter = new MovieDetailsPresenter(this, movie_id);
    }

    @Override
    public void initView() {
        moviePosterDetail = findViewById(R.id.movie_poster_detail);
        originalName = findViewById(R.id.original_name_tv);
        userRating = findViewById(R.id.user_rating_tv);
        releaseDate = findViewById(R.id.release_date_tv);
        plotSummary = findViewById(R.id.plot_summary_tv);
        progressBar = findViewById(R.id.movie_detail_progress_bar);

        createSupportActionBar();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void createSupportActionBar(){
        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayMovieDetails(String imageUrl, String originalName, String userRating, String releaseDate, String plotSummary){

        if(!this.isFinishing()){
            Glide.with(this)
                    .load(imageUrl)
                    .into(moviePosterDetail);
        }

        this.originalName.setText(originalName);
        this.userRating.setText(userRating);
        this.releaseDate.setText(releaseDate);
        this.plotSummary.setText(plotSummary);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(item_id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
