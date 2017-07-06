package xavier.jorda.cat.recipe;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

import xavier.jorda.cat.recipe.util.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider
{

    private static String TAG = RecipeWidget.class.getSimpleName();
    private AppWidgetManager appWidgetManager_;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds)
    {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        updateWidgetView(context,
                appWidgetManager,
                AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RecipeWidget.class)),
                null);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        ArrayList<String> stepList = intent.getStringArrayListExtra(Constants.STEP_LIST);

        if (stepList == null)
            return;

        AppWidgetManager manager = AppWidgetManager.getInstance(context);

        if(manager != null) {
            updateWidgetView(context,
                    manager,
                    AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RecipeWidget.class)),
                    stepList);
        }
    }

    @Override
    public void onEnabled(Context context)
    {
        Log.d(TAG, "onEnabled");
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        Log.d(TAG, "onDisabled");
        // Enter relevant functionality for when the last widget is disabled
    }

    private void updateWidgetView(Context context, AppWidgetManager appWidgetManager,
                                  int[] appWidgetIds, ArrayList<String> stepList)
    {
        if (stepList == null)
            return;

        for (int widgetId : appWidgetIds)
        {
            RemoteViews mView = initViews(context, widgetId, stepList);
            appWidgetManager.updateAppWidget(widgetId, mView);
        }
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context, int widgetId, ArrayList<String> stepList)
    {
        if(stepList == null)
            return null;

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.recipe_widget);

        remoteViews.setTextViewText(R.id.appwidget_text, stepList.get(stepList.size()-1));
        stepList.remove(stepList.size()-1);

        stepList.remove(stepList.size()-1);
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        intent.putStringArrayListExtra(Constants.STEP_LIST,stepList);

        remoteViews.setRemoteAdapter(widgetId, R.id.widgetCollectionList, intent);

        return remoteViews;
    }
}

