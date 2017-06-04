package xavier.jorda.cat.recipe;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by xj1 on 04/06/2017.
 */

public class DetailsFragmentActivity extends AppCompatActivity implements DetailsRecipeMessages
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Top_Fragment frg=new Top_Fragment();
//        frg2=new BottomFragment();
//        FragmentManager manager=getSupportFragmentManager();
//        transaction=manager.beginTransaction();
//        transaction.add(R.id.My_Container_1_ID, frg, "Frag_Top_tag");
//        transaction.add(R.id.My_Container_3_ID, frg2, "Frag_Bottom_tag");
//        transaction.commit();
    }

    public void onRecipeCardClicked(int recipeNum)
    {

    }
}
