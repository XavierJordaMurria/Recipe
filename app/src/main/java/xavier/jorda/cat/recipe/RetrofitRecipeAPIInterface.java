package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 31/05/2017.
 */



import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import xavier.jorda.cat.recipe.model.RecipeModel;

interface RetrofitRecipeAPIInterface
{
    @GET("2017/May/59121517_baking/baking.json")
    Call<RecipeModel> getAnswers();

    @GET("2017/May/59121517_baking/baking.json")
    Call<List<RecipeModel>> getAnswers2();
}