package com.eslam.du.bakingapp.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;

import com.eslam.du.bakingapp.adapters.RecipesAdapter;
import com.eslam.du.bakingapp.modules.Recipe;
import com.eslam.du.bakingapp.R;
import com.eslam.du.bakingapp.api.API;
import com.eslam.du.bakingapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static ArrayList<Recipe> list=null;
    public static Boolean dataLoaded=false;


    //OnCreate Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);// Set Content of the activity_main

        if (list == null) {
            callRetrofit();
        } else {
            loadData();
        }

    }


    private void callRetrofit(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(API.BASE_URL)  //Base Url from API .
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final API api=retrofit.create(API.class);

        Call<ArrayList<Recipe>> recipeCall=api.getRecipes();
        recipeCall.enqueue(new Callback<ArrayList<Recipe>>(){
            @Override
            public void onResponse(@NonNull Call<ArrayList<Recipe>> call, @NonNull Response<ArrayList<Recipe>> response) {

                list = response.body();
                loadData();
                dataLoaded=true;
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Recipe>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"Please Check The Connection",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void loadData(){
        RecipesAdapter recipeAdapter=new RecipesAdapter(list);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,calculateBestSpanCount()) ;
        binding.recipesRecylcer.setLayoutManager(gridLayoutManager);
        binding.recipesRecylcer.setAdapter(recipeAdapter);
    }

    private int calculateBestSpanCount() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / 800);
    }

}


