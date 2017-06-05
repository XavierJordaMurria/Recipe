package xavier.jorda.cat.recipe.detailsRecipe;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import xavier.jorda.cat.recipe.R;
import xavier.jorda.cat.recipe.util.Constants;

/**
 * Created by xj1 on 04/06/2017.
 */

public class DetailsFragmentActivity extends AppCompatActivity implements StepsFragment.OnItemSelectedListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_recipe);

        Bundle b = getIntent().getExtras();
        int recipeCardPosition = -1; // or other values

        if(b != null)
            recipeCardPosition = b.getInt(Constants.RECIPE_CARD_POSITION);


        if (savedInstanceState == null)
        {
            StepsFragment newFragment = new StepsFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.RECIPE_CARD_POSITION, recipeCardPosition);
            newFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle

            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
            ft1.add(R.id.recipe_steps_fragment_container, newFragment).commit();
        }
    }

    @Override
    public void onStepItemSelected(int position)
    {
        Toast.makeText(this, "Called By Fragment A: position - "+ position, Toast.LENGTH_SHORT).show();

        // Load Pizza Detail Fragment
//        PizzaDetailFragment secondFragment = new PizzaDetailFragment();
//
//        Bundle args = new Bundle();
//        args.putInt("position", position);
//        secondFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
//
//
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
//        {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.flContainer2, secondFragment) // replace flContainer
//                    //.addToBackStack(null)
//                    .commit();
//        }
//        else
//        {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.flContainer, secondFragment) // replace flContainer
//                    .addToBackStack(null)
//                    .commit();
//        }
    }
}
