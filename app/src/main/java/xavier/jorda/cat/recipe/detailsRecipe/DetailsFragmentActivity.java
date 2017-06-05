package xavier.jorda.cat.recipe.detailsRecipe;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    private int recipeCardPosition = -1; // or other values

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_recipe);

        Bundle b = getIntent().getExtras();

        if(b != null)
            recipeCardPosition = b.getInt(Constants.RECIPE_CARD_POSITION);


        if (savedInstanceState == null)
        {
            StepsFragment newFragment = new StepsFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition);
            newFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.recipe_steps_fragment_container, newFragment)
//                    .addToBackStack(newFragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    public void onStepItemSelected(int position)
    {
        DetailsFragment secondFragment;

        if( position == -1)
            secondFragment  = new IngredientsFragment();
        else
            secondFragment  = new IngredientsFragment();

        Bundle args = new Bundle();
        args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition);
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

}
