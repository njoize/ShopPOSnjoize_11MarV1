package njoize.dai_ka.com.demotestprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MemberDetailActivity extends AppCompatActivity {

    private String idString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("22AprilV1", "MemberDetailActivity Work");

        setContentView(R.layout.activity_member_detail);

//        Get Value
        idString = getIntent().getStringExtra("idString");


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentMemberDetailFragment, MemberDetailFragment.memberDetailInstance(idString, true)).commit();
        }
    }
}
