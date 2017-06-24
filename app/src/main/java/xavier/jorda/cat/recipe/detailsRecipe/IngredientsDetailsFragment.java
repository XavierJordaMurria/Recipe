package xavier.jorda.cat.recipe.detailsRecipe;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xavier.jorda.cat.recipe.MyApplication;
import xavier.jorda.cat.recipe.R;
import xavier.jorda.cat.recipe.model.RecipeModel;
import xavier.jorda.cat.recipe.util.Constants;

/**
 * Created by xj1 on 04/06/2017.
 */

public class IngredientsDetailsFragment extends DetailsFragment
{
    private final static String TAG = IngredientsDetailsFragment.class.getSimpleName();

    private int recipeCardPosition_;
    private MyApplication myApp;
    private IngredientsAdapter rcAdapter;
    private RecyclerView rView_;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        myApp = (MyApplication)getActivity().getApplication();

        if(savedInstanceState == null)
        {
            // Get back arguments
            if(getArguments() == null)
                return;

            recipeCardPosition_ = getArguments().getInt(Constants.RECIPE_CARD_POSITION, 0);
            RecipeModel recipe = myApp.recipes.get(recipeCardPosition_);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getActivity());

            rcAdapter = new IngredientsAdapter(recipe);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.details_ingredients_fragment,
                container, false);

        rView_ = (RecyclerView)view.findViewById(R.id.ingredients_recycler_view);
        rView_.setHasFixedSize(true);
        rView_.setLayoutManager(mLayoutManager);
        rView_.setAdapter(rcAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
}