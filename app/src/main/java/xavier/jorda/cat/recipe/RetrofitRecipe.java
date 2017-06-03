package xavier.jorda.cat.recipe;

/**
 * Created by xj1 on 31/05/2017.
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitRecipe
{

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static <T> T createServiceFrom(final Class<T> serviceClass)
    {
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();

        T service = adapter.create(serviceClass);

        return service;
    }
}