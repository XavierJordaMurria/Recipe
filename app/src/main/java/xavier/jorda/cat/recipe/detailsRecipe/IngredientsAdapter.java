package xavier.jorda.cat.recipe.detailsRecipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import xavier.jorda.cat.recipe.R;
import xavier.jorda.cat.recipe.model.IngredientsComponents;
import xavier.jorda.cat.recipe.model.RecipeModel;
import xavier.jorda.cat.recipe.util.Constants;

/**
 * Created by xj1 on 04/06/2017.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>
{

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public TextView ingName_;
        public TextView ingMeasureLbl_;
        public TextView ingQuantityLbl_;

        public ViewHolder(View v)
        {
            super(v);

            ingName_    = (TextView)v.findViewById(R.id.ing_name);
            ingMeasureLbl_   = (TextView)v.findViewById(R.id.ing_measure_label);
            ingQuantityLbl_ = (TextView)v.findViewById(R.id.ing_quantity_label);
        }
    }

    private final static String TAG = IngredientsAdapter.class.getSimpleName();
    private Context context;
    private List<IngredientsComponents> dataSet_= new ArrayList<>();

    public IngredientsAdapter(RecipeModel recipe)
    {
        getIngredientsFromRecipe(recipe);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient,parent, false);
        ViewHolder rcv = new ViewHolder(view);
        return rcv;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.ingName_.setText(dataSet_.get(position).getIngredientName_());
        holder.ingMeasureLbl_.setText(dataSet_.get(position).getMeasure_());
        holder.ingQuantityLbl_.setText(Float.toString(dataSet_.get(position).getQuantity_()));
    }

    @Override
    public int getItemCount()
    {
        return this.dataSet_.size();
    }

    private void getIngredientsFromRecipe(RecipeModel recipe)
    {
        dataSet_    = recipe.getIngredients_();
        this.notifyDataSetChanged();
    }
}
