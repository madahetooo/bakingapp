package com.eslam.du.bakingapp;

import android.support.test.espresso.IdlingResource;

import com.eslam.du.bakingapp.activities.MainActivity;

import java.util.Observable;

public class RecyclerViewIdlingResource extends Observable implements IdlingResource {

    private ResourceCallback  resourceCallback;


    @Override
    public String getName() {
        return RecyclerViewIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {

        boolean idle = MainActivity.dataLoaded;

        if (idle) { resourceCallback.onTransitionToIdle(); }

        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
