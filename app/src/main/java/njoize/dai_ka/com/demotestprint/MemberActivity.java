package njoize.dai_ka.com.demotestprint;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MemberActivity extends AppCompatActivity {

    private String idString;
    private boolean statusABoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        idString = getIntent().getStringExtra("id");
        statusABoolean = getIntent().getBooleanExtra("Status", false);
        Log.d("20FebV2", "id ==> " + idString);
        Log.d("20FebV2", "Status ==> " + statusABoolean);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentMemberFragment, MemberDetailFragment.memberDetailInstance(idString, statusABoolean))
                .commit();

    }
}
