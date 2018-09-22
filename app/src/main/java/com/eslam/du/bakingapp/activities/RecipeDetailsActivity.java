package com.eslam.du.bakingapp.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.eslam.du.bakingapp.R;
import com.eslam.du.bakingapp.adapters.IngredientsAdapter;
import com.eslam.du.bakingapp.databinding.ActivityRecipeDetailsBinding;
import com.eslam.du.bakingapp.modules.Ingredients;
import com.eslam.du.bakingapp.modules.Recipe;
import com.eslam.du.bakingapp.widget.NewAppWidget;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {
    private Recipe recipe;
    private Resources r;
    private ActivityRecipeDetailsBinding binding;
    private ArrayList<Ingredients> ingredients;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_details); //Get Content of the recipe details layout

        final Intent intent = getIntent();

        if (intent.getExtras() != null) {

            initUI(intent);

            initAdapter();

            FabOnClickListners();

        }

    }

    private void initUI(Intent intent) { //Intializing the UD

        r = this.getResources();
        String substance_img = r.getString(R.string.substance_img);
        recipe = intent.getParcelableExtra(r.getString(R.string.recipe_extras));

        ingredients = recipe.getIngredients();        //getGredients.
        img = intent.getStringExtra(substance_img);


        try {
            Picasso.get()
                    .load(img)
                    .error(R.drawable.errors)
                    .into(binding.imageView3);
        } catch (Exception e) {
            e.printStackTrace();
        }


        binding.DetailRecipeName.setText(recipe.getName() + " "); //set Recipe name
        binding.ServingsNumber.setText(String.valueOf(recipe.getServings()));  // set Servings Number.

    }

    private void initAdapter() {   //Initialize Adapter
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(ingredients);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.ingredients.setHasFixedSize(true);
        binding.ingredients.setLayoutManager(linearLayoutManager);
        binding.ingredients.setAdapter(ingredientsAdapter);
    }


    private void FabOnClickListners() {

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), stepListActivity.class);
                String RECIPE_KEY = getResources().getString(R.string.recipe_extras);
                intent.putParcelableArrayListExtra(RECIPE_KEY, recipe.getSteps());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

            }
        });


        binding.favouritesFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinatorLayout), "Added to widget on Home Screen", Snackbar.LENGTH_LONG);
                snackbar.show();
                addToSharedWidget();
                WidgetUpdate();

            }
        });

    }

    private void WidgetUpdate() {

        Intent intent = new Intent(getApplicationContext(), NewAppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication())
                .getAppWidgetIds(new ComponentName(getApplication(), NewAppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    private void addToSharedWidget() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        String WIDGET_NAME_KEY = getResources().getString(R.string.widget_recipe_name); //get widget recipeName
        String WIDGET_INGREDIENTS_KEY = getResources().getString(R.string.widget_recipe_ingredients);   //get Recipe Ingredients

        StringBuilder ingredientsSet = new StringBuilder();

        for (int i = 0; i < ingredients.size(); i++) {

            String s1 = ingredients.get(i).getIngredient();
            String s2 = String.valueOf(ingredients.get(i).getQuantity());
            String s3 = ingredients.get(i).getMeasure();

            ingredientsSet.append(s1 + "   ");
            ingredientsSet.append(s2);
            ingredientsSet.append(s3);
            ingredientsSet.append(" \n");
        }

        String ingredients = ingredientsSet.toString();
        editor.putString(WIDGET_NAME_KEY, recipe.getName());
        editor.putString(WIDGET_INGREDIENTS_KEY, ingredients);
        editor.apply();


    }


}
