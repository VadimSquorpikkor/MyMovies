package com.squorpikkor.app.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squorpikkor.app.mymovies.data.FavouriteMovie;
import com.squorpikkor.app.mymovies.data.Movie;

public class DetailActivity extends AppCompatActivity {

    private ImageView bigPoster;
    private ImageView imageViewAddToFavorite;
    private ImageView favFalse;

    private TextView textTitle;
    private TextView textOriginalTitle;
    private TextView textRating;
    private TextView textReleaseDate;
    private TextView textOverview;

    private int id;
    private MainViewModel mainViewModel;
    private Movie movie;

    private FavouriteMovie favouriteMovie;

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
        setContentView(R.layout.activity_detail);

        bigPoster = findViewById(R.id.imageViewBigPoster);
        textTitle = findViewById(R.id.textViewTitle);
        textOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textRating = findViewById(R.id.textViewRating);
        textReleaseDate = findViewById(R.id.textViewReleaseDate);
        textOverview = findViewById(R.id.textViewOverview);
        imageViewAddToFavorite = findViewById(R.id.favoriteTrue);
//        favFalse = findViewById(R.id.fav)

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) id = intent.getIntExtra("id", -1);
        else finish();

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        movie = mainViewModel.getMovieById(id);
        Picasso.get().load(movie.getBigPosterPath()).into(bigPoster);

        textTitle.setText(movie.getTitle());
        textOriginalTitle.setText(movie.getOriginalTitle());
        textRating.setText(String.valueOf(movie.getVoteAverage()));
        textReleaseDate.setText(movie.getReleaseDate());
        textOverview.setText(movie.getOverview());

        imageViewAddToFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFavorite(view);
            }
        });

        setFavourite();
    }

    private void changeFavorite(View view) {
        if (movie == null) { //если null, значит такого в БД ещё не было, значит можно добавлять
            mainViewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
        } else {
            mainViewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, "Удалено из избранного", Toast.LENGTH_SHORT).show();
        }
        setFavourite();

    }

    private void setFavourite() {
        favouriteMovie = mainViewModel.getFavoriteMovieById(id);
        if (favouriteMovie==null) imageViewAddToFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        else imageViewAddToFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
    }
}