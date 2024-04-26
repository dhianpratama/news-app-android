package com.praise.newsapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.praise.newsapp.Model.Articles;
import com.praise.newsapp.Model.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//visit "https://newsapi.org/v2/" and request for your own private API KEY
public class MainActivity extends AppCompatActivity {
    final String API_KEY = "2fdf8ffd223d466384b5bd5c0a89094f";
    RecyclerView newsRecyclerView;

    RecyclerView storiesRecyclerView;
    NewsAdapter newsAdapter;

    StoriesAdapter storiesAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Articles> articles = new ArrayList<>();
    EditText editText;

    DrawerLayout drawerLayout;

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);

    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }

    public static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure you want to Exit the app ?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        swipeRefreshLayout = findViewById(R.id.swipe);
        newsRecyclerView = findViewById(R.id.recyclerView);
        newsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        storiesRecyclerView = findViewById(R.id.idRVCategories);
        storiesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        String country = getCountry();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson("", country, API_KEY);
            }
        });
        retrieveJson("", country, API_KEY);
    }

    public void retrieveJson(String query, String country, String apikey) {
        swipeRefreshLayout.setRefreshing(true);
        Call<Headlines> call;

        call = ApiClient.getInstance().getApi().getHeadines(country, apikey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    newsAdapter = new NewsAdapter(MainActivity.this, articles);
                    newsRecyclerView.setAdapter(newsAdapter);

                    storiesAdapter = new StoriesAdapter(MainActivity.this, articles);
                    storiesRecyclerView.setAdapter(storiesAdapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);

    }

    public void ClickHome(View view) {
        closeDrawer(drawerLayout);
    }


    public void ClickLogout(View view) {
        logout(this);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}