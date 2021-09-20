package com.squorpikkor.app.mymovies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squorpikkor.app.mymovies.data.Movie;

import java.util.ArrayList;

class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.DeleteViewHolder> {

    private ArrayList<Movie> movieList;
    private OnItemClickListener onItemClickListener;
    private OnReachEndListener onReachEndListener;

    public DeleteAdapter(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }

    interface OnReachEndListener {
        void onReachEnd();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public DeleteAdapter.DeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new DeleteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteAdapter.DeleteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class DeleteViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView text;
        public DeleteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageViewSmallPoster);
//            text = itemView.findViewById(R.id.)

        }
    }
}
