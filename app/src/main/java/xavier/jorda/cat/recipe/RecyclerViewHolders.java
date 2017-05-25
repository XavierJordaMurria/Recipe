package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 25/05/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xavier.jorda.cat.recipe.fcm.MyFirebaseInstanceIdService;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private final static String TAG = MyFirebaseInstanceIdService.class.getSimpleName();
    public TextView mRecipeName;
    public ImageView mRecipePhoto;

    public RecyclerViewHolders(View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        mRecipeName = (TextView)itemView.findViewById(R.id.recipe_name);
        mRecipePhoto = (ImageView)itemView.findViewById(R.id.recipe_photo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}