package com.eslam.du.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eslam.du.bakingapp.modules.Ingredients;
import com.eslam.du.bakingapp.R;

import java.util.ArrayList;
public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private ArrayList<Ingredients> IngredientsList;
    private Context context;

    public IngredientsAdapter(ArrayList<Ingredients> Ingredients_list) {
        this.IngredientsList =Ingredients_list;
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {
        final TextView ingredient;
        final TextView quantity;
        final TextView measure;

        private ViewHolder(View itemView) {
            super(itemView);
            ingredient=itemView.findViewById(R.id.ingredient);
            quantity=itemView.findViewById(R.id.quantity);
            measure=itemView.findViewById(R.id.measure);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        context=parent.getContext();
        int item_layout=R.layout.ingredient_item_view;
        View Ingredient_item = LayoutInflater.from(context).inflate(item_layout, parent, false);
        return new ViewHolder(Ingredient_item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.ingredient.setText(IngredientsList.get(position).getIngredient());
        holder.quantity.setText(String.valueOf(IngredientsList.get(position).getQuantity()));
        holder.measure.setText(IngredientsList.get(position).getMeasure());


    }
    @Override
    public int getItemCount() {
        if (IngredientsList==null) return 0;
        return IngredientsList.size();
    }


}


