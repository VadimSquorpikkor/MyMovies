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
        switchSort = findViewById(R.id.switchSort);
        textPopularity = findViewById(R.id.textViewPopularity);
        textTopRated = findViewById(R.id.textViewTopRated);

        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 3));//Для отображения сеткой
        movieAdapter = new MovieAdapter();
        recyclerViewPosters.setAdapter(movieAdapter);
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener((compoundButton, isChecked) -> setMethodOfSort(isChecked));
        switchSort.setChecked(false);
        textPopularity.setOnClickListener(view -> setPopularity());
        textTopRated.setOnClickListener(view -> setTopRated());
        movieAdapter.setOnPosterClickListener(position -> Log.e(TAG, "onPosterClick: "+position));
        movieAdapter.setOnReachEndListener(() -> Log.e(TAG, "onReachEnd: конец списка (4 постера до конца)"));
    }

    private void setPopularity() {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }

    private void setTopRated() {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }

    void setMethodOfSort(boolean isTopRated) {
        int methodOfSort;
        if (isTopRated) {
            methodOfSort = TOP_RATED;
            textTopRated.setTextColor(getResources().getColor(R.color.teal_200));
            textPopularity.setTextColor(getResources().getColor(R.color.white));
//            switchSort.setChecked(false);
        } else {
            methodOfSort = POPULARITY;
            textTopRated.setTextColor(getResources().getColor(R.color.white));
            textPopularity.setTextColor(getResources().getColor(R.color.teal_200));
//            switchSort.setChecked(true);
        }
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort, 1);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        movieAdapter.setMovies(movies);
    }
}