package com.eslam.du.bakingapp.modules;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("servings")
    private int servings;
    @Expose
    @SerializedName("steps")
    private ArrayList<Steps> steps;
    @Expose
    @SerializedName("ingredients")
    private ArrayList<Ingredients> ingredients;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("id")
    private int id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeInt(this.servings);
        dest.writeList(this.steps);
        dest.writeList(this.ingredients);
        dest.writeString(this.name);
        dest.writeInt(this.id);
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        this.image = in.readString();
        this.servings = in.readInt();
        this.steps = new ArrayList<Steps>();
        in.readList(this.steps, Steps.class.getClassLoader());
        this.ingredients = new ArrayList<Ingredients>();
        in.readList(this.ingredients, Ingredients.class.getClassLoader());
        this.name = in.readString();
        this.id = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
