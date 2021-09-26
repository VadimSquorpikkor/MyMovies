package com.squorpikkor.app.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.squorpikkor.app.mymovies.adapters.MovieAdapter;
import com.squorpikkor.app.mymovies.data.Movie;
import com.squorpikkor.app.mymovies.utils.JSONUtils;
import com.squorpikkor.app.mymovies.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.squorpikkor.app.mymovies.utils.NetworkUtils.POPULARITY;
import static com.squorpikkor.app.mymovies.utils.NetworkUtils.TOP_RATED;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private Switch switchSort;
    private TextView textPopularity;
    private TextView textTopRated;
    private MainViewModel mViewModel;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itemMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intent2 = new Intent(this, FavoriteActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

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
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Movie movie = movieAdapter.getMovies().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
        movieAdapter.setOnReachEndListener(() -> Log.e(TAG, "onReachEnd: конец списка (4 постера до конца)"));

        LiveData<List<Movie>> moviesFromLiveData = mViewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });
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
        downloadData(methodOfSort, 1);
    }

    private void downloadData(int methodOfSort, int page) {
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort, 1);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        if (movies != null && !movies.isEmpty()) {
            mViewModel.deleteAllMovies();
            for (Movie movie : movies) {
                mViewModel.insertMovie(movie);
            }
        }
    }
}