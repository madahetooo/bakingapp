package com.eslam.du.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eslam.du.bakingapp.activities.RecipeDetailsActivity;
import com.eslam.du.bakingapp.modules.Recipe;
import com.eslam.du.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private ArrayList<Recipe> list;
    private Context context;

    public RecipesAdapter(ArrayList<Recipe> Array_list) {
        this.list = Array_list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView recipeImage;
        final TextView recipeName;

        private ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, RecipeDetailsActivity.class);
            String RECIPE_KEY = context.getResources().getString(R.string.recipe_extras);
            intent.putExtra(RECIPE_KEY, list.get(getAdapterPosition()));
            int posttion = getAdapterPosition();
            String img = list.get(posttion).getImage();
            String substance_img = context.getResources().getString(R.string.substance_img);
            intent.putExtra(substance_img, substance_img(img, posttion));

            context.startActivity(intent);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int item_layout = R.layout.recipe_item_view;
        View Recipe_item = LayoutInflater.from(context).inflate(item_layout, parent, false);
        return new ViewHolder(Recipe_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String img = list.get(position).getImage();

        try {
            Picasso.get()
                    .load(substance_img(img, position))
                    .error(R.drawable.errors)
                    .into(holder.recipeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.recipeName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    private String substance_img(String Img, int position) {

        if (Img.equals("")) {

            String alternative = null;
            Resources r = context.getResources();

            if (position <= 3) {
                switch (position) {
                    case 0:
                        alternative = r.getString(R.string.image1);
                        break;
                    case 1:
                        alternative = r.getString(R.string.image2);
                        break;
                    case 2:
                        alternative = r.getString(R.string.image3);
                        break;
                    case 3:
                        alternative = r.getString(R.string.image4);
                        break;
                }
                return alternative;
            } else {
                alternative = r.getString(R.string.image5);
                return alternative;
            }

        } else {
            return Img;
        }

    }

}
