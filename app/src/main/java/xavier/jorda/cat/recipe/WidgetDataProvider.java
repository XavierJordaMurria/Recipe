package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 03/07/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import xavier.jorda.cat.recipe.util.Constants;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsFactory
{
    private ArrayList<String> collections_ = new ArrayList<>();
    private Context context_ = null;

    WidgetDataProvider(Context context, Intent intent)
    {
        context_ = context;
        collections_ = intent.getStringArrayListExtra(Constants.STEP_LIST);
    }

    @Override
    public int getCount()
    {
        return collections_.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public RemoteViews getLoadingView()
    {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        RemoteViews mView = new RemoteViews(context_.getPackageName(),
                android.R.layout.simple_list_item_1);
        mView.setTextViewText(android.R.id.text1, collections_.get(position));
        mView.setTextColor(android.R.id.text1, Color.BLACK);
        return mView;
    }

    @Override
    public int getViewTypeCount()
    {
        return 1;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy()
    {
    }
}
