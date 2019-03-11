package njoize.dai_ka.com.demotestprint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ShowMemberListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_member_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentShowMemberListFragment, MemberFragment.memberInstance(false))
                    .commit();
        }


    }
}
