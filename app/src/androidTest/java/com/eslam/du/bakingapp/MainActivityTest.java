package com.eslam.du.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.eslam.du.bakingapp.activities.MainActivity;
import com.eslam.du.bakingapp.activities.RecipeDetailsActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private  RecyclerViewIdlingResource idlingRes;

    @Rule
    //check for opening Activity
    public IntentsTestRule<MainActivity> mainActivityActivityTestRule=new IntentsTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        idlingRes= new RecyclerViewIdlingResource();
        Espresso.registerIdlingResources(idlingRes);
    }

    @Test
    public void TestOpenRecipeDetailActivity(){

        Espresso.onView(withId(R.id.recipes_recylcer))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        intended(hasComponent(RecipeDetailsActivity.class.getName()));

    }

    @After
    public void unregisterIntentServiceIdlingResource() {
        Espresso.unregisterIdlingResources(idlingRes);
    }

}
