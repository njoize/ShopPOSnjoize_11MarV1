package njoize.dai_ka.com.demotestprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private boolean statusABoolean; // true ==> From MemberDetail, false ==> DetailActivity
    private String mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Log.d("22AprilV1", "DetailActivity Work");

//        Get Value
        MyConstant myConstant = new MyConstant();
        String[] strings = myConstant.getDetailStrings();
        String[] valueStrings1 = new String[strings.length];

        String tidString = getIntent().getStringExtra("tid");
        statusABoolean = getIntent().getBooleanExtra("Status", false);
        mid = getIntent().getStringExtra("mid");
        Log.d("28FebV1", "mid DetailActivity ==> " + mid);

        for (int i = 0; i < strings.length; i += 1) {
            valueStrings1[i] = getIntent().getStringExtra(strings[i]);
        }




        if (savedInstanceState == null) {

            ArrayList<String> stringArrayList = new ArrayList<>();

            for (int i = 0; i < valueStrings1.length; i += 1) {
                stringArrayList.add(valueStrings1[i]);
            }

            stringArrayList.add(tidString);
            stringArrayList.add(Boolean.toString(statusABoolean));
            stringArrayList.add(mid);


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentDetailFragment, BillDetailFragment.billDetailInstance(
                            valueStrings1[0],
                            valueStrings1[1],
                            valueStrings1[2],
                            valueStrings1[3],
                            valueStrings1[4],
                            valueStrings1[5],
                            valueStrings1[6],
                            tidString,
                            statusABoolean,
                            mid, stringArrayList)).commit();
        }


    }
}
