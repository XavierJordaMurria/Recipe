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
import xavier.jorda.cat.recipe.model.RecipeModel;
import xavier.jorda.cat.recipe.util.Constants;

/**
 * Created by xj1 on 04/06/2017.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>
{

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // each data item is just a string in this case
        public TextView textView_;
        public ViewHolder(TextView v)
        {
            super(v);
            textView_ = v;
        }

        @Override
        public void onClick(View v)
        {

        }
    }

    public interface AdapterDelegate
    {
        public void onStepClicked(int i);
    }

    private final static String TAG = StepsAdapter.class.getSimpleName();
    private Context context;
    private List<String> dataSet_= new ArrayList<>();
    private AdapterDelegate delegate_;

    public StepsAdapter(Context context, RecipeModel recipe, AdapterDelegate delegate)
    {
        this.context = context;
        this.delegate_ = delegate;
        getStepsFromRecipe(recipe);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        TextView textView = (TextView)LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step,parent, false);
        ViewHolder rcv = new ViewHolder(textView);
        return rcv;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView_.setText(dataSet_.get(position));

    }

    @Override
    public int getItemCount()
    {
        return this.dataSet_.size();
    }

    private void getStepsFromRecipe(RecipeModel recipe)
    {
        dataSet_.add(Constants.INGREDIENTS);

        int stepsNum = recipe.getSteps_().size();

        for(int i = 0; i < stepsNum; i++)
            dataSet_.add(Constants.SETP+"#"+i+" "+recipe.getSteps_().get(i).getShortDescription_());

        this.notifyDataSetChanged();
    }
}
