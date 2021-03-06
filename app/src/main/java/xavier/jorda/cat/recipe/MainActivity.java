package xavier.jorda.cat.recipe;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import xavier.jorda.cat.recipe.IdlingResource.SimpleIdlingResource;
import xavier.jorda.cat.recipe.service.RetrofitWrapper;

public class MainActivity extends AppCompatActivity implements MainActivityViewAdapter.AdapterCallBack {
    private final static String TAG = MainActivity.class.getSimpleName();

    private GridLayoutManager mLayout;
    private MyApplication myApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myApp = (MyApplication) getApplication();

        if (myApp.idlingResource_ != null)
            myApp.idlingResource_.setIdleState(false);

        setContentView(R.layout.activity_main);

        MainActivityViewAdapter rcAdapter = new MainActivityViewAdapter(MainActivity.this, this);
        RetrofitWrapper.getRecipesInto(this, rcAdapter);

        mLayout = new GridLayoutManager(MainActivity.this, getCardsNumberOnScreen());
        RecyclerView rView = (RecyclerView) findViewById(R.id.recipes_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(mLayout);
        rView.setAdapter(rcAdapter);
    }

    private int getCardsNumberOnScreen() {
        int cardsNumber;

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                cardsNumber = 3;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                cardsNumber = 1;
                break;
            default:
                cardsNumber = 1;
        }
        return cardsNumber;
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (myApp.idlingResource_ == null)
            myApp.idlingResource_ = new SimpleIdlingResource();

        return myApp.idlingResource_;
    }

    @Override
    public void onDatarecieved() {
        if (myApp.idlingResource_ != null)
            myApp.idlingResource_.setIdleState(true);
    }
}
