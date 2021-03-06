package xavier.jorda.cat.recipe.detailsRecipe;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import xavier.jorda.cat.recipe.R;
import xavier.jorda.cat.recipe.model.RecipeModel;
import xavier.jorda.cat.recipe.model.StepModel;
import xavier.jorda.cat.recipe.model.StepsComponents;
import xavier.jorda.cat.recipe.util.Constants;

import static android.view.View.VISIBLE;

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
        TextView textView_;
        ImageView imageView_;
        ViewHolder(View v)
        {
            super(v);
            textView_ = (TextView)v.findViewById(R.id.recipe_step_name);
            textView_.setOnClickListener(this);

            imageView_ = (ImageView)v.findViewById(R.id.recipe_step_photo);
        }

        @Override
        public void onClick(View v)
        {
            TextView textView = (TextView)v;
            delegate_.onStepItemSelected(parseBoxText(textView.getText().toString()));
        }
    }

    private final static String TAG = StepsAdapter.class.getSimpleName();
    private Context context_;
    private List<StepModel> dataSet_= new ArrayList<>();
    private StepsFragment.OnItemSelectedListener delegate_;

    StepsAdapter(Context context, RecipeModel recipe, StepsFragment.OnItemSelectedListener delegate)
    {
        this.context_ = context;
        this.delegate_ = delegate;
        getStepsFromRecipe(recipe);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step,parent, false);

        ViewHolder rcv = new ViewHolder(view);
        return rcv;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView_.setText(dataSet_.get(position).label_);
        String imagePath = dataSet_.get(position).url_;

        Log.d(TAG, "imagePath = " + imagePath);
        if(!imagePath.isEmpty()) {
            holder.imageView_.setVisibility(VISIBLE);
            Picasso.with(context_).load(imagePath).into(holder.imageView_);
        }
    }

    @Override
    public int getItemCount()
    {
        return this.dataSet_.size();
    }

    private void getStepsFromRecipe(RecipeModel recipe)
    {
        StepModel stepModelTmp;
        stepModelTmp = new StepModel(Constants.INGREDIENTS, "");
        dataSet_.add(stepModelTmp);

        int stepsNum = recipe.getSteps_().size();
        for(int i = 0; i < stepsNum; i++)
        {
            String label = Constants.STEP + "#" + i + " " + recipe.getSteps_().get(i).getShortDescription_();
            String imageUrl = recipe.getSteps_().get(i).getThumbNailURL_();
            stepModelTmp = new StepModel(label,imageUrl);
            dataSet_.add(stepModelTmp);
        }

        this.notifyDataSetChanged();
    }

    private int parseBoxText(String text)
    {
        if(text.equals(Constants.INGREDIENTS))
            return -1;
        else
        {
            String[] tmpStepNumArr = text.split(" ");
            String tmp = tmpStepNumArr[0].split("#")[1];

            return Integer.parseInt(tmp);
        }
    }
}
