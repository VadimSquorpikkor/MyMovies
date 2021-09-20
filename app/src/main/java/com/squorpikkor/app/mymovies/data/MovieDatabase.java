package com.squorpikkor.app.mymovies.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class, FavouriteMovie.class}, version = 2, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase movieDatabase;
    public static final String DB_NAME = "movies.db";
    public static final Object LOCK = new Object();

    public static MovieDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (movieDatabase == null) {
                movieDatabase = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration()//Это добавлено для того, чтобы при изменении версии на новую, старые данные вместе с таблицами автоматом удалялись (иначе нужно вручную удалять) и новые таблицы создавались
                        .build();
            }
            return movieDatabase;
        }

    }

    public abstract MovieDAO movieDAO();
}
