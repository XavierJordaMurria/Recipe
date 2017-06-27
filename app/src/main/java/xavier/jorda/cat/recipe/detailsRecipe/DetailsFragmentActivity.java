package xavier.jorda.cat.recipe.detailsRecipe;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import xavier.jorda.cat.recipe.R;
import xavier.jorda.cat.recipe.util.Constants;

/**
 * Created by xj1 on 04/06/2017.
 */

public class DetailsFragmentActivity extends AppCompatActivity implements StepsFragment.OnItemSelectedListener
{
    private static String TAG = DetailsFragmentActivity.class.getSimpleName();

    private int recipeCardPosition_ = -1; // or other values
    private int stepNum_ = -1; // or other values
    private DetailsFragment newFragment;

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putInt("recipeCardPosition_", recipeCardPosition_);
        outState.putInt("stepNum_", stepNum_);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.details_recipe);

        Bundle b = getIntent().getExtras();
        if(b != null)
            recipeCardPosition_ = b.getInt(Constants.RECIPE_CARD_POSITION);


        if (savedInstanceState == null)
        {
//            setContentView(R.layout.details_recipe);
            newFragment = new StepsFragment();

            Bundle args = new Bundle();
            args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);
            newFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.recipe_steps_fragment_container, newFragment)
////                    .addToBackStack(newFragment.getClass().getSimpleName())
//                    .commit();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_steps_fragment_container, newFragment)
//                    .addToBackStack(newFragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onBackPressed()
    {
        if (currentFragment() instanceof StepsFragment)
            super.onBackPressed();
        else
        {
            StepsFragment newFragment = new StepsFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);
            newFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_steps_fragment_container, newFragment)
//                    .addToBackStack(newFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        Log.d(TAG, "onConfigurationChanged");

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }

        if (currentFragment() instanceof StepDetailsFragment)
        {
            newFragment = new StepDetailsFragment();

            Bundle args = new Bundle();
            args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);
            newFragment.setArguments(args);
        }

    }

    @Override
    public void onStepItemSelected(int position)
    {
        DetailsFragment secondFragment;
        Bundle args = new Bundle();

        if( position == -1)
        {
            secondFragment = new IngredientsDetailsFragment();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
            stepNum_ = position;
            secondFragment = new StepDetailsFragment();
            args.putInt(Constants.STEP_NUMBER, position);
        }

        args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition_);
        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_steps_fragment_container, secondFragment) // replace flContainer
//                    .addToBackStack(null)
                    .commit();
        }
        else
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_steps_fragment_container, secondFragment) // replace flContainer
//                    .addToBackStack(secondFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    private Fragment currentFragment()
    {
        return getSupportFragmentManager().findFragmentById(R.id.recipe_steps_fragment_container);
    }
}
