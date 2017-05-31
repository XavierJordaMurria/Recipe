package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 31/05/2017.
 */

import com.google.gson.annotations.SerializedName;
public class IngredientsComponents
{
    @SerializedName("quantity")
    private int quantity_;
    @SerializedName("measure")
    private String measure_;
    @SerializedName("ingredient")
    private String ingredientName_;

    public IngredientsComponents(int quantity, String mesure, String name)
    {
        quantity_   = quantity;
        measure_     = mesure;
        ingredientName_ = name;
    }

    public int getQuantity_()
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
