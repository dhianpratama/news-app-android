package com.dhian.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dhian.newsapp.Model.Articles;
import com.dhian.newsapp.Model.Headlines;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    final String API_KEY = "2fdf8ffd223d466384b5bd5c0a89094f";

    TextView tvTitle, tvSource, tvDate, tvDesc;
    ImageView i;
    ProgressBar progressBar;

    RecyclerView newsRecyclerView;

    RelatedNewsAdapter newsAdapter;

    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvTitle = findViewById(R.id.tvRelatedTitle);
        tvSource = findViewById(R.id.tvRelatedDescription);
        tvDate = findViewById(R.id.tvRelatedDate);
        tvDesc = findViewById(R.id.tvDesc);
         i = findViewById(R.id.imageV);


        newsRecyclerView = findViewById(R.id.idRVRelatedNews);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String source = intent.getStringExtra("source");
        String time = intent.getStringExtra("time");
        String desc = intent.getStringExtra("desc");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");
        tvTitle.setText(title);
        tvSource.setText(source);
        tvDate.setText(time);
        tvDesc.setText(desc);
        Picasso.get().load(imageUrl).into(i);

        retrieveJson("", getCountry(), API_KEY);
    }

    public void retrieveJson(String query, String country, String apikey) {
        Call<Headlines> call;

        call = ApiClient.getInstance().getApi().getHeadines(country, apikey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles = response.body().getArticles();
                    newsAdapter = new RelatedNewsAdapter(DetailsActivity.this, articles);
                    newsRecyclerView.setAdapter(newsAdapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) { }
        });
    }

    public  String getCountry(){
        Locale locale = Locale.getDefault();
        String country  = locale.getCountry();
        return  country.toLowerCase();
    }
}