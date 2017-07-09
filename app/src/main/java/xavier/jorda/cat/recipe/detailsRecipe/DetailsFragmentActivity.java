package xavier.jorda.cat.recipe.detailsRecipe;


import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import xavier.jorda.cat.recipe.MyApplication;
import xavier.jorda.cat.recipe.R;
import xavier.jorda.cat.recipe.util.Constants;

/**
 * Created by xj1 on 04/06/2017.
 */

public class DetailsFragmentActivity extends AppCompatActivity implements StepsFragment.OnItemSelectedListener {
    private static String TAG = DetailsFragmentActivity.class.getSimpleName();

    private int recipeCardPosition_ = -1; // or other values
    private int stepNum_ = -1; // or other values
    private DetailsFragment newFragment;
    private MyApplication myApp;

    private enum loadFrgType_ {ADD_FRG, REPLACE_FRG};

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);
        outState.putInt(Constants.STEP_NUMBER, stepNum_);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        ActionBar actionBar = getActionBar();

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        myApp = (MyApplication) getApplication();

        if (myApp.idlingResource_ != null)
            myApp.idlingResource_.setIdleState(false);

        setContentView(R.layout.details_recipe);

        Bundle b = getIntent().getExtras();
        if (b != null)
            recipeCardPosition_ = b.getInt(Constants.RECIPE_CARD_POSITION);

        Bundle args = new Bundle();
        args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);

        FrameLayout details = (FrameLayout) findViewById(R.id.recipe_step_fragment_details);

        if (savedInstanceState == null) {

            loadFragment(new StepsFragment(), loadFrgType_.REPLACE_FRG, R.id.recipe_steps_fragment_container, args);

            if (details != null)
                loadFragment(new IngredientsDetailsFragment(), loadFrgType_.REPLACE_FRG, R.id.recipe_step_fragment_details, args);

        } else if (currentFragment() instanceof StepDetailsFragment &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            args.putInt(Constants.RECIPE_CARD_POSITION, savedInstanceState.getInt(Constants.RECIPE_CARD_POSITION));
            args.putInt(Constants.STEP_NUMBER, savedInstanceState.getInt(Constants.STEP_NUMBER));

            loadFragment(new StepDetailsFragment(), loadFrgType_.REPLACE_FRG, R.id.recipe_steps_fragment_container, args);

        } else if (currentFragment() instanceof StepsFragment &&
                (details != null)) {

            args.putInt(Constants.RECIPE_CARD_POSITION, savedInstanceState.getInt(Constants.RECIPE_CARD_POSITION));
            args.putInt(Constants.STEP_NUMBER, savedInstanceState.getInt(Constants.STEP_NUMBER));

            loadFragment(new StepsFragment(), loadFrgType_.REPLACE_FRG, R.id.recipe_steps_fragment_container, args);
            loadFragment(new StepDetailsFragment(), loadFrgType_.REPLACE_FRG, R.id.recipe_step_fragment_details, args);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (myApp.idlingResource_ != null)
            myApp.idlingResource_.setIdleState(true);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        if (currentFragment() instanceof StepsFragment)
            super.onBackPressed();
        else {
            Bundle args = new Bundle();
            args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);

            loadFragment(new StepsFragment(), loadFrgType_.REPLACE_FRG, R.id.recipe_steps_fragment_container, args);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG, "onConfigurationChanged");

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }

        if (currentFragment() instanceof StepDetailsFragment) {
            newFragment = new StepDetailsFragment();

            Bundle args = new Bundle();
            args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);
            newFragment.setArguments(args);
        }
    }

    @Override
    public void onStepItemSelected(int position) {

        if (myApp.idlingResource_ != null)
            myApp.idlingResource_.setIdleState(false);

        DetailsFragment secondFragment;
        Bundle args = new Bundle();

        if (position == -1) {
            secondFragment = new IngredientsDetailsFragment();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            stepNum_ = position;
            secondFragment = new StepDetailsFragment();
            args.putInt(Constants.STEP_NUMBER, position);
        }

        args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);

        FrameLayout details = (FrameLayout) findViewById(R.id.recipe_step_fragment_details);

        if (details == null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                loadFragment(secondFragment, loadFrgType_.REPLACE_FRG, R.id.recipe_steps_fragment_container, args);
            else
                loadFragment(secondFragment, loadFrgType_.REPLACE_FRG, R.id.recipe_steps_fragment_container, args);
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                loadFragment(secondFragment, loadFrgType_.REPLACE_FRG, R.id.recipe_step_fragment_details, args);
            else
                loadFragment(secondFragment, loadFrgType_.REPLACE_FRG, R.id.recipe_step_fragment_details, args);
        }
    }

    private Fragment currentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.recipe_steps_fragment_container);
    }

    private void loadFragment(Fragment newFragment, loadFrgType_ loadFrgType, int containerViewID, Bundle args) {
        if (args != null)
            newFragment.setArguments(args);

        FragmentManager frgManager = getSupportFragmentManager();

        if (loadFrgType == loadFrgType_.ADD_FRG) {
            frgManager
                    .beginTransaction()
                    .add(containerViewID, newFragment)
                    .commit();
        } else {
            frgManager
                    .beginTransaction()
                    .replace(containerViewID, newFragment)
                    .commit();
        }
    }
}
