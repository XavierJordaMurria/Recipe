package xavier.jorda.cat.recipe.model;

/**
 * Created by xj1 on 31/05/2017.
 */


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeModel
{
    @SerializedName("id")
    private int id_;
    @SerializedName("name")
    private String name_;
    @SerializedName("ingredients")
    private List<IngredientsComponents> ingredients_;
    @SerializedName("steps")
    private  List<StepsComponents> steps_;
    @SerializedName("servings")
    private String servings_;
    @SerializedName("image")
    private String image;

    public int getId_()
    {
        return id_;
    }

    public void setId_(int id_)
    {
        this.id_ = id_;
    }

    public String getName_()
    {
        return name_;
    }

    public void setName_(String name_)
    {
        this.name_ = name_;
    }

    public List<IngredientsComponents> getIngredients_()
    {
        return ingredients_;
    }

    public void setIngredients_(List<IngredientsComponents> ingredients_)
    {
        this.ingredients_ = ingredients_;
    }

    public List<StepsComponents> getSteps_()
    {
        return steps_;
    }

    public void setSteps_(List<StepsComponents> steps_)
    {
        this.steps_ = steps_;
    }

    public String getServings_()
    {
        return servings_;
    }

    public void setServings_(String servings_)
    {
        this.servings_ = servings_;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }
}
