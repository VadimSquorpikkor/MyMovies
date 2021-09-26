package com.squorpikkor.app.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.squorpikkor.app.mymovies.R;
import com.squorpikkor.app.mymovies.data.Movie;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<Movie> movies;
    private OnPosterClickListener onPosterClickListener;
    private OnReachEndListener onReachEndListener;

    public MovieAdapter() {
        this.movies = new ArrayList<>();
    }

    public interface OnPosterClickListener {
        void onPosterClick(int position);
    }

    /**Срабатывания конца загруженных постеров (чтобы можно было догрузить следующие)*/
    public interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> list) {
        this.movies.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
//        if (i == movies.size() - 1 && onReachEndListener != null) {
        if (i > movies.size() - 4 && onReachEndListener != null) { //лисенер будет срабатывать, когда до конца списка ещё будет оставаться 4 элемента (дозагрузка наснется чуть заранее чем пользователь дойдет до конца списка)
            onReachEndListener.onReachEnd();
        }
        Movie movie = movies.get(i);
        ImageView imageView = movieViewHolder.imageViewSmallPoster;
        Picasso.get().load(movie.getPosterPath()).into(imageView);//это всё, что нужно для загрузки изображения с помощью Picasso. Кроме простоты, пикассо ещё кэширует загруженные картинки
//        Picasso.get().load("https://uchastokrir2.web.app/imgs/bdkg02/bdkg-02-1.jpg").into(imageView);//это всё, что нужно для загрузки изображения с помощью Picasso. Кроме простоты, пикассо ещё кэширует загруженные картинки
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPosterClickListener != null) {
                        onPosterClickListener.onPosterClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
