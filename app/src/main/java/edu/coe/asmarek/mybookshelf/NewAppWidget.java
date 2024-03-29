package edu.coe.asmarek.mybookshelf;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);

        Intent addBook = new Intent("edu.coe.asmarek.mybookshelf.AddBookText");
        addBook.putExtra("TableName", "shelf");
        PendingIntent addBookPI = PendingIntent.getActivity(context, 1, addBook, 0);

        Intent addWish = new Intent("edu.coe.asmarek.mybookshelf.AddBookText");
        addWish.putExtra("TableName", "wishlist");
        PendingIntent addWishPI = PendingIntent.getActivity(context, 0, addWish, 0);

        views.setOnClickPendingIntent(R.id.btnAddShelf, addBookPI);
        views.setOnClickPendingIntent(R.id.btnAddWishlist, addWishPI);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

