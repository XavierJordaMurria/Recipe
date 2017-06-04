package xavier.jorda.cat.recipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = MainActivity.class.getSimpleName();

    private GridLayoutManager mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityViewAdapter rcAdapter = new MainActivityViewAdapter(MainActivity.this);
        RetrofitWrapper.getRecipesInto(this, rcAdapter);

        mLayout = new GridLayoutManager(MainActivity.this, 2);
        RecyclerView rView = (RecyclerView)findViewById(R.id.recipes_recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(mLayout);
        rView.setAdapter(rcAdapter);




    }
}
