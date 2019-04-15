package njoize.dai_ka.com.demotestprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MemberDetailActivity extends AppCompatActivity {

    private String idString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);

//        Get Value
        idString = getIntent().getStringExtra("idString");


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentMemberDetailFragment, MemberDetailFragment.memberDetailInstance(idString)).commit();
        }
    }
}
