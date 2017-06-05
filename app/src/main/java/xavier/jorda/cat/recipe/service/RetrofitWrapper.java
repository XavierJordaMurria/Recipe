package xavier.jorda.cat.recipe.service;

import android.app.Activity;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import xavier.jorda.cat.recipe.MainActivityViewAdapter;
import xavier.jorda.cat.recipe.MyApplication;
import xavier.jorda.cat.recipe.model.RecipeModel;

/**
 * Created by xj1 on 03/06/2017.
 */

public class RetrofitWrapper
{
    private static MyApplication myApp;
    private final static String TAG = RetrofitWrapper.class.getSimpleName();

    private static WeakReference<Activity> activityWeakRef_;

    public static void getRecipesInto(final Activity activity, final MainActivityViewAdapter adapter)
    {
        activityWeakRef_ =  new WeakReference<>(activity);
        myApp = ((MyApplication)activityWeakRef_.get().getApplication());


        RetrofitRecipeAPIInterface apiService = RetrofitRecipe.getClient().create(RetrofitRecipeAPIInterface.class);

        Call<List<RecipeModel>> call = apiService.getAnswers();

        call.enqueue(new Callback<List<RecipeModel>>()
        {
            @Override
            public void onResponse(Call<List<RecipeModel>>call, Response<List<RecipeModel>> response)
            {
                myApp.recipes = response.body();

                Log.d(TAG, "Number of recipesReceived received: " +  myApp.recipes.size());

                adapter.addRecipeList(myApp.recipes);
            }

            @Override
            public void onFailure(Call<List<RecipeModel>>call, Throwable t)
            {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    public static void getRecipesInto2(final Activity activity, final MainActivityViewAdapter adapter)
    {
        RetrofitRecipeAPIInterface recipeAPIInterface = RetrofitRecipe.createServiceFrom(RetrofitRecipeAPIInterface.class);

        recipeAPIInterface.getAnswers2()
                .flatMap(Observable::from)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::addRecipe);
    }
}
