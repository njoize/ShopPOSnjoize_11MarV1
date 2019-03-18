package njoize.dai_ka.com.demotestprint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zj.wfsdk.WifiCommunication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {

    //    Explicit
    private MyConstant myConstant = new MyConstant();
    private String loginJSONString, nameString;
    private String tag = "1devV2";
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private boolean aBoolean = true; //true ==> Check Internet
    private WifiCommunication wifiCommunication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

//        Check Internet
        checkInternet();

//        Check Printer
        checkPrinter();

//        Get ValueLogin
        getValueLogin();

//        Create Toolbar
        createToolbar();

//        Add Fragment
        addFragment(savedInstanceState);

//        Create DrawerMenu
        createDrawerMenu();


    } // Main Method

    private void createDrawerMenu() {
        RecyclerView recyclerView = findViewById(R.id.recyclerDrawerMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ServiceActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        MyConstant myConstant = new MyConstant();
        int[] ints = myConstant.getIconDrawerInts();
        String[] strings = myConstant.getTitleDrawerStrings();

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        for (int i = 0; i < strings.length; i += 1) {
            integerArrayList.add(ints[i]);
            stringArrayList.add(strings[i]);
        }

        DrawerMenuAdapter drawerMenuAdapter = new DrawerMenuAdapter(ServiceActivity.this,
                integerArrayList, stringArrayList, new OnClickItem() {
            @Override
            public void onClickItem(View view, int positions) {
                Log.d("23decV2", "You Click menu ==> " + positions);
                activeClick(positions);
                drawerLayout.closeDrawers();
            }
        });
        recyclerView.setAdapter(drawerMenuAdapter);


    }

    private void activeClick(int positions) {

        switch (positions) {
            case 0:
                break;
            case 1:
                break;
            case 2:

//                Sign Out
                MyConstant myConstant = new MyConstant();
                SharedPreferences sharedPreferences = getSharedPreferences(myConstant.getSharePreferFile(), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(ServiceActivity.this, MainActivity.class));
                finish();

                break;
            case 3:

//                Test Print
                startActivity(new Intent(ServiceActivity.this, TestPrintActivity.class));


                break;
        }


    }

    private void checkPrinter() {

        MyConstant myConstant = new MyConstant();
        wifiCommunication = new WifiCommunication(handler);
        wifiCommunication.initSocket(myConstant.getIpAddressPrinter(), myConstant.getPortPrinter());


    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == WifiCommunication.WFPRINTER_CONNECTED) {
                Log.d("23decV1", "Printer Connected");
                Log.wtf("23decV1", "Printer Connected");
                Toast.makeText(ServiceActivity.this, "Check Connected Printer OK", Toast.LENGTH_SHORT).show();
                wifiCommunication.close();
            } else {
                Log.d("23decV1", "Printer Cannot Connected");
                Log.wtf("23decV1", "Printer Cannot Connected");
            }


        }
    };

    private void checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected() && aBoolean)) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ServiceActivity.this);
            alertDialogBuilder.setTitle("Check Internet").setMessage("Cannot Connected Internet")
                    .setPositiveButton("Exit App", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Continue App", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    aBoolean = false;
                    dialog.dismiss();
                }
            }).show();

        }
    }

    private void addFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentServiceFragment, ServiceFragment.serviceInstant(1,"",true,
                            "","", ""))
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        actionBarDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();

    }

    private void getValueLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("userLogin", MODE_PRIVATE);
        nameString = sharedPreferences.getString("User", "");
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.ic_action_logo);
        getSupportActionBar().setTitle(myConstant.getNameShopToolbar());
        getSupportActionBar().setSubtitle("   Login by : " + nameString);

        drawerLayout = findViewById(R.id.layoutDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                ServiceActivity.this,
                drawerLayout,
                R.string.open,
                R.string.close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_hamberger);


    }
} // Main Class