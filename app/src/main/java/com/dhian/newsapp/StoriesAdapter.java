package com.dhian.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dhian.newsapp.Model.Articles;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.ViewHolder> {

    Context context;
    List<Articles> articles;

    public StoriesAdapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public StoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stories,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  StoriesAdapter.ViewHolder holder, int position) {
        final Articles a = articles.get(position);
        String title = a.getTitle();
        holder.tvSource.setText(a.getSource().getName());
        String imageUrl = a.getUrlToImage();
        holder.image.setImageAlpha(70);
        Picasso.get().load(imageUrl).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvSource;

        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSource = itemView.findViewById(R.id.idTVCategory);
            image = itemView.findViewById(R.id.idIVCategory);
        }
    }
}
