package com.squorpikkor.app.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.squorpikkor.app.mymovies.data.Movie;
import com.squorpikkor.app.mymovies.utils.JSONUtils;
import com.squorpikkor.app.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.squorpikkor.app.mymovies.utils.NetworkUtils.POPULARITY;
import static com.squorpikkor.app.mymovies.utils.NetworkUtils.TOP_RATED;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private Switch switchSort;
    private TextView textPopularity;
    private TextView textTopRated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 3));//Для отображения сеткой
        movieAdapter = new MovieAdapter();
        recyclerViewPosters.setAdapter(movieAdapter);
        switchSort = findViewById(R.id.switchSort);
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener((compoundButton, isChecked) -> setMethodOfSort(isChecked));
        switchSort.setChecked(false);
        textPopularity.setOnClickListener(view -> setPopularity());
        textPopularity.setOnClickListener(view -> setTopRated());
    }

    private void setPopularity() {
        switchSort.setChecked(false);
    }

    private void setTopRated() {
        switchSort.setChecked(true);
    }

    void setMethodOfSort(boolean isTopRated) {
        int methodOfSort;
        if (isTopRated) {
            methodOfSort = TOP_RATED;
            textTopRated.setTextColor(getResources().getColor(R.color.teal_200));
            textPopularity.setTextColor(getResources().getColor(R.color.white));
        } else {
            methodOfSort = POPULARITY;
            textTopRated.setTextColor(getResources().getColor(R.color.white));
            textPopularity.setTextColor(getResources().getColor(R.color.teal_200));
        }
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort, 1);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        movieAdapter.setMovies(movies);
    }
}