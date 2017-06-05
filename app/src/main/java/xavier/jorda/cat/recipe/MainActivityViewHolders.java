package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 25/05/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xavier.jorda.cat.recipe.detailsRecipe.DetailsFragmentActivity;
import xavier.jorda.cat.recipe.util.Constants;

public class MainActivityViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final static String TAG = MainActivityViewHolders.class.getSimpleName();
    public TextView mRecipeName_;
    public ImageView mRecipePhoto_;

    private Context mainViewContext_;


    public MainActivityViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        mRecipeName_ = (TextView)itemView.findViewById(R.id.recipe_name);
        mRecipePhoto_ = (ImageView)itemView.findViewById(R.id.recipe_photo);

        mainViewContext_ = itemView.getContext();
    }

    @Override
    public void onClick(View view)
    {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();

        Log.d(TAG,"Clicked item at position:" + getLayoutPosition());

        Intent mIntent = new Intent(mainViewContext_, DetailsFragmentActivity.class);
        mIntent.putExtra(Constants.RECIPE_CARD_POSITION, getLayoutPosition());

        mainViewContext_.startActivity(mIntent);
    }
}