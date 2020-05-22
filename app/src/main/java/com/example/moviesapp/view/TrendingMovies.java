package com.example.moviesapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviesapp.R;
import com.example.moviesapp.adapter.MovieAdapter;
import com.example.moviesapp.moduleController.TrendingModule;
import com.example.moviesapp.presenter.MoviePresenter;

public class TrendingMovies extends AppCompatActivity implements TrendingModule.View, MovieAdapter.ClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "MainActivity ";
    private MoviePresenter moviePresenter;

    private static final String ID_KEY = "movie_id";
    private String sort_keyword = "trending";

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView networkError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSharedPreferenceListener();
        moviePresenter = new MoviePresenter(this, this, sort_keyword);
    }

    @Override
    public void initView() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);
        networkError = (TextView) findViewById(R.id.errorText);
    }

    @Override
    public void setupSharedPreferenceListener(){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sort_keyword = sharedPreferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_default_value));
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void displayMovieData(MovieAdapter movieAdapter){
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void progressBarVisible(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        networkError.setVisibility(View.INVISIBLE);
    }

    @Override
    public void recyclerViewVisible(){
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        networkError.setVisibility(View.INVISIBLE);
    }

    @Override
    public void noResponse(){
        networkError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Integer id = item.getItemId();
        if(id.equals(R.id.settings)){
            openSettings();
        }
        return true;
    }

    @Override
    public void openSettings(){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    @Override
    public void showDetails(int itemId){
        Intent detailIntent = new Intent(this, MovieDetail.class);
        detailIntent.putExtra(ID_KEY, itemId);
        startActivity(detailIntent);
    }

    @Override
    public void onItemClickListener(int itemClicked) {
        showDetails(itemClicked);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        sort_keyword = sharedPreferences.getString(key, getString(R.string.pref_sort_default_value));
        moviePresenter = new MoviePresenter(this, this, sort_keyword);
    }
}
