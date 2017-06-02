package xavier.jorda.cat.recipe.model;

/**
 * Created by xj1 on 31/05/2017.
 */

import com.google.gson.annotations.SerializedName;
public class IngredientsComponents
{
    @SerializedName("quantity")
    private float quantity_;
    @SerializedName("measure")
    private String measure_;
    @SerializedName("ingredient")
    private String ingredientName_;

    public IngredientsComponents(float quantity, String mesure, String name)
    {
        quantity_   = quantity;
        measure_     = mesure;
        ingredientName_ = name;
    }

    public float getQuantity_()
    {
        return quantity_;
    }

    public String getMeasure_()
    {
        return measure_;
    }

    public String getIngredientName_()
    {
        return ingredientName_;
    }
}
