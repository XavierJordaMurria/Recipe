package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 31/05/2017.
 */



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RetrofirecipiAPIInterface
{
    @GET("/2017/May/59121517_baking/baking.json")
    Call<RecipeModel> getAnswers();

    @GET("/2017/May/59121517_baking/baking.json")
    Call<RecipeModel> getAnswers2(@Query("tagged") String tags);
}