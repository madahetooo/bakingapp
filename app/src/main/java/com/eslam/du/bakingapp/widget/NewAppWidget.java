package com.eslam.du.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.eslam.du.bakingapp.activities.MainActivity;
import com.eslam.du.bakingapp.R;

public class NewAppWidget extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String WIDGET_NAME_KEY = context.getResources().getString(R.string.widget_recipe_name);
        String WIDGET_INGREDIENTS_KEY = context.getResources().getString(R.string.widget_recipe_ingredients);


        String name =pref.getString(WIDGET_NAME_KEY, null);
        String ingredients=pref.getString(WIDGET_INGREDIENTS_KEY, null);


        if(ingredients != null) {
            views.setTextViewText(R.id.widgetName, name);           //SetWidgetName
            views.setTextViewText(R.id.widgetIngredients, ingredients);     //SetWidgetIngredients
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widgetIngredients, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }
    @Override
    public void onDisabled(Context context) {
    }
}

