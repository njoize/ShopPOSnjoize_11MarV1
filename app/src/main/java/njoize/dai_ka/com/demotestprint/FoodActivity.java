package njoize.dai_ka.com.demotestprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        String amountCustomer = getIntent().getStringExtra("Amount");
        boolean totalBill = getIntent().getBooleanExtra("Bill", true);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.contentFoodFragment, FoodFragment.foodInstante(amountCustomer, totalBill))
//                    .commit();
//        }

    }
}
