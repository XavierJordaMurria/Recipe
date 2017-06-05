package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 25/05/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xavier.jorda.cat.recipe.model.RecipeModel;

public class MainActivityViewAdapter extends RecyclerView.Adapter<MainActivityViewHolders>
{
    private final static String TAG = MainActivityViewAdapter.class.getSimpleName();

    private List<RecipeModel> itemList = new ArrayList<>();

    private Context context;

    public MainActivityViewAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public MainActivityViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list, null);
        MainActivityViewHolders rcv = new MainActivityViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MainActivityViewHolders holder, int position)
    {
        holder.mRecipeName_.setText(itemList.get(position).getName_());

        String imgURL = itemList.get(position).getImage();

        if(!imgURL.isEmpty())
        {
            Picasso.with(context)
                    .load(imgURL)
                    .resize(50, 50)
                    .centerCrop()
                    .into(holder.mRecipePhoto_);
        }
        else
        {
            Picasso.with(context).load(R.drawable.ic_launcher).into(holder.mRecipePhoto_);
        }
    }

    @Override
    public int getItemCount()
    {
        return this.itemList.size();
    }

    public void addRecipeList(List<RecipeModel> itemList)
    {
        this.itemList = itemList;
        this.notifyDataSetChanged();
    }

    public void addRecipe(RecipeModel repo)
    {
        this.itemList.add(repo);
        notifyItemInserted(this.itemList.size() - 1);
    }
}