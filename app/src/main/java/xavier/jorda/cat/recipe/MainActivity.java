package xavier.jorda.cat.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xavier.jorda.cat.recipe.fcm.MyFirebaseInstanceIdService;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = MyFirebaseInstanceIdService.class.getSimpleName();

    private GridLayoutManager mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ItemObject> rowListItem = getAllItemList();
        mLayout = new GridLayoutManager(MainActivity.this, 2);
        RecyclerView rView = (RecyclerView)findViewById(R.id.recipes_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(mLayout);

        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, rowListItem);
        rView.setAdapter(rcAdapter);



        RetrofirecipiAPIInterface apiService =
                RetrofitRecipe.getClient().create(RetrofirecipiAPIInterface.class);

        Call<RecipeModel> call = apiService.getAnswers();
        call.enqueue(new Callback<RecipeModel>()
        {
            @Override
            public void onResponse(Call<RecipeModel>call, Response<RecipeModel> response)
            {
                List<RecipeModel> recepies = (List<RecipeModel>) response.body();

                List<StepsComponents> steps = response.body().getSteps_();

                Log.d(TAG, "Number of steps received: " + steps.size());
            }

            @Override
            public void onFailure(Call<RecipeModel>call, Throwable t)
            {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject("United States", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Canada", R.drawable.ic_launcher));
        allItems.add(new ItemObject("United Kingdom", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Germany", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Sweden", R.drawable.ic_launcher));
        allItems.add(new ItemObject("United Kingdom", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Germany", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Sweden", R.drawable.ic_launcher));
        allItems.add(new ItemObject("United States", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Canada", R.drawable.ic_launcher));
        allItems.add(new ItemObject("United Kingdom", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Germany", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Sweden", R.drawable.ic_launcher));
        allItems.add(new ItemObject("United Kingdom", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Germany", R.drawable.ic_launcher));
        allItems.add(new ItemObject("Sweden", R.drawable.ic_launcher));

        return allItems;
    }
}
