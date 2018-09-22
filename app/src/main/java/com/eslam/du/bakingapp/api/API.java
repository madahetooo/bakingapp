package com.eslam.du.bakingapp.api;


import com.eslam.du.bakingapp.modules.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    String BASE_URL = "http://go.udacity.com/";

    @GET("android-baking-app-json")
    Call<ArrayList<Recipe>> getRecipes();

}
