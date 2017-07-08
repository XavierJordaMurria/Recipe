package xavier.jorda.cat.recipe.detailsRecipe;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xavier.jorda.cat.recipe.MyApplication;
import xavier.jorda.cat.recipe.R;
import xavier.jorda.cat.recipe.RecipeWidget;
import xavier.jorda.cat.recipe.model.RecipeModel;
import xavier.jorda.cat.recipe.model.StepsComponents;
import xavier.jorda.cat.recipe.util.Constants;

/**
 * Created by xj1 on 04/06/2017.
 */

public class StepsFragment extends DetailsFragment
{
    private final static String TAG = StepsFragment.class.getSimpleName();

    private OnItemSelectedListener listener_;
    private int recipeCardPosition_;
    private MyApplication myApp;
    private StepsAdapter rcAdapter;
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

            rcAdapter = new StepsAdapter(getContext(), recipe, listener_);

            sendIntent2Widget(recipe.getName_(), recipe.getSteps_());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.details_steps_fragment,
                container, false);

        rView_ = (RecyclerView)view.findViewById(R.id.steps_recycler_view);
        rView_.setHasFixedSize(true);

        rView_.setLayoutManager(mLayoutManager);
        rView_.setAdapter(rcAdapter);


        if (myApp.idlingResource_!= null)
            myApp.idlingResource_.setIdleState(true);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    //--OnItemSelectedListener listener;
    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof OnItemSelectedListener)  // context instanceof YourActivity
            this.listener_ = (OnItemSelectedListener) context; // = (YourActivity) context
        else
            throw new ClassCastException(context.toString()
                    + " must implement StepsFragment.OnItemSelectedListener");
    }

    // Define the events that the fragment will use to communicate
    public interface OnItemSelectedListener
    {
        // This can be any number of events to be sent to the activity
        void onStepItemSelected(int position);
    }

    private void sendIntent2Widget(String recipeName, List<StepsComponents> stepsComponents)
    {
        final ArrayList<String> stepsModel = getStepsFromRecipe(stepsComponents);
        Context context = getContext();
        stepsModel.add(recipeName);

        // Create the text message with a string
        Intent sendIntent = new Intent(context, RecipeWidget.class);
        sendIntent.setAction("xavier.jorda.cat.recipe.WIDGET_DATA");
        sendIntent.putStringArrayListExtra(Constants.STEP_LIST,stepsModel);

        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, RecipeWidget.class));
        if(ids != null && ids.length > 0) {
            sendIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(sendIntent);
        }
    }

    private ArrayList<String> getStepsFromRecipe(List<StepsComponents> stepsComponents)
    {
        ArrayList<String> dataSet = new ArrayList<>();
        int stepsNum = stepsComponents.size();

        for(int i = 0; i < stepsNum; i++)
        {
            String label = Constants.STEP + "#" + i + " " + stepsComponents.get(i).getShortDescription_();
            dataSet.add(label);
        }

        return dataSet;
    }
}
