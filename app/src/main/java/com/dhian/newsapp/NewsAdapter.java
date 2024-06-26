package com.dhian.newsapp;

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

import com.dhian.newsapp.Model.Articles;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    Context context;
    List<Articles> articles;

    public NewsAdapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  NewsAdapter.ViewHolder holder, int position) {
        final Articles a = articles.get(position);
        holder.tvTitle.setText(a.getSource().getName());
        holder.tvSource.setText(a.getTitle());
        holder.tvDate.setText("\u2022"+dateTime(a.getPublishedAt()));
        String imageUrl = a.getUrlToImage();
        Picasso.get().load(imageUrl).into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("title", a.getTitle());
                intent.putExtra("source", a.getSource().getName());
                intent.putExtra("time", dateTime(a.getPublishedAt()));
                intent.putExtra("imageUrl", a.getUrlToImage());
                intent.putExtra("url",a.getUrl());
                intent.putExtra("desc",a.getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public  class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvTitle, tvSource, tvDate;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRelatedTitle);
            tvSource = itemView.findViewById(R.id.tvRelatedDescription);
            tvDate = itemView.findViewById(R.id.tvRelatedDate);
            imageView = itemView.findViewById(R.id.idNewsImage);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }
    public  String getCountry(){
        Locale locale = Locale.getDefault();
        String country  = locale.getCountry();
        return  country.toLowerCase();
    }
    public  String  dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
