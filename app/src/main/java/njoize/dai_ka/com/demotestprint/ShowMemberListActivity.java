package njoize.dai_ka.com.demotestprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class ShowMemberListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_member_list);

        if (savedInstanceState == null) {

            ArrayList<String> stringArrayList = new ArrayList<>();
            stringArrayList = getIntent().getStringArrayListExtra("KeyArrayList");

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentShowMemberListFragment, MemberFragment.memberInstance(false, stringArrayList))
                    .commit();
        }


    }
}
