package com.squorpikkor.app.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favourite_movies")
public
class FavouriteMovie extends Movie{
    public FavouriteMovie(int id, int voteCount, String title, String originalTitle, String overview, String posterPath, String bigPosterPath, String backDropPath, double voteAverage, String releaseDate) {
        super(id, voteCount, title, originalTitle, overview, posterPath, bigPosterPath, backDropPath, voteAverage, releaseDate);
    }

    @Ignore
    public FavouriteMovie(Movie movie) {
        super(movie.getId(), movie.getVoteCount(), movie.getTitle(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(), movie.getBigPosterPath(), movie.getBackDropPath(), movie.getVoteAverage(), movie.getReleaseDate());
    }
}
