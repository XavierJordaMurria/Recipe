package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 25/05/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import xavier.jorda.cat.recipe.fcm.MyFirebaseInstanceIdService;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders>
{
    private final static String TAG = MyFirebaseInstanceIdService.class.getSimpleName();
    private List<ItemObject> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<ItemObject> itemList)
    {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position)
    {
        holder.mRecipeName.setText(itemList.get(position).getName());
        holder.mRecipePhoto.setImageResource(itemList.get(position).getPhoto());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}