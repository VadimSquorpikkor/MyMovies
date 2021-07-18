package com.squorpikkor.app.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.squorpikkor.app.mymovies.data.Movie;
import com.squorpikkor.app.mymovies.utils.JSONUtils;
import com.squorpikkor.app.mymovies.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.squorpikkor.app.mymovies.utils.NetworkUtils.getJSONFromNetwork;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //для проверки
        //String url = NetworkUtils.buildURL(NetworkUtils.POPULARITY, 1).toString();
        //Log.e(TAG, url);

        //для проверки 2
//        JSONObject jsonObject = getJSONFromNetwork(NetworkUtils.TOP_RATED, 3);
//        if (jsonObject == null) Toast.makeText(this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
//        else Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show();

        JSONObject jsonObject = getJSONFromNetwork(NetworkUtils.POPULARITY, 5);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        StringBuilder builder = new StringBuilder();
        for (Movie m:movies) {
            builder.append(m.getTitle()).append("\n");
        }
        Log.e(TAG, builder.toString());

    }


}