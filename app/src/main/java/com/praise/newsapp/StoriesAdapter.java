package com.praise.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.praise.newsapp.Model.Articles;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,Details.class);
//                intent.putExtra("title", a.getTitle());
//                intent.putExtra("source", a.getSource().getName());
//                intent.putExtra("time", dateTime(a.getPublishedAt()));
//                intent.putExtra("imageUrl", a.getUrlToImage());
//                intent.putExtra("url",a.getUrl());
//                intent.putExtra("desc",a.getDescription());
//                context.startActivity(intent);
//            }
//        });
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
