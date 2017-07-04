package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 03/07/2017.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

@SuppressLint("NewApi")
public class WidgetService extends RemoteViewsService
{
    private final static String TAG = WidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        Log.d(TAG, "onGetViewFactory");
        WidgetDataProvider dataProvider = new WidgetDataProvider(getApplicationContext(), intent);
        return dataProvider;
    }
}
