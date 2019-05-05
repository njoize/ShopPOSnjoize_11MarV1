package njoize.dai_ka.com.demotestprint;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeskFragment extends Fragment implements View.OnClickListener {

    //    Explicit
    private MyConstant myConstant = new MyConstant();
    private int tabAnInt = 0;
    private String zoneIDString, branchIDString;
    private ArrayList<String> zoneNameStringArrayList = new ArrayList<>();
    private ArrayList<String> zoneIDStringArrayList = new ArrayList<>();

    private TextView[][] textViews = new TextView[10][10];
    private int[][] ints = new int[][]{
            {R.id.imv0_0, R.id.imv0_1, R.id.imv0_2, R.id.imv0_3, R.id.imv0_4, R.id.imv0_5, R.id.imv0_6, R.id.imv0_7, R.id.imv0_8, R.id.imv0_9},
            {R.id.imv1_0, R.id.imv1_1, R.id.imv1_2, R.id.imv1_3, R.id.imv1_4, R.id.imv1_5, R.id.imv1_6, R.id.imv1_7, R.id.imv1_8, R.id.imv1_9},
            {R.id.imv2_0, R.id.imv2_1, R.id.imv2_2, R.id.imv2_3, R.id.imv2_4, R.id.imv2_5, R.id.imv2_6, R.id.imv2_7, R.id.imv2_8, R.id.imv2_9},
            {R.id.imv3_0, R.id.imv3_1, R.id.imv3_2, R.id.imv3_3, R.id.imv3_4, R.id.imv3_5, R.id.imv3_6, R.id.imv3_7, R.id.imv3_8, R.id.imv3_9},
            {R.id.imv4_0, R.id.imv4_1, R.id.imv4_2, R.id.imv4_3, R.id.imv4_4, R.id.imv4_5, R.id.imv4_6, R.id.imv4_7, R.id.imv4_8, R.id.imv4_9},
            {R.id.imv5_0, R.id.imv5_1, R.id.imv5_2, R.id.imv5_3, R.id.imv5_4, R.id.imv5_5, R.id.imv5_6, R.id.imv5_7, R.id.imv5_8, R.id.imv5_9},
            {R.id.imv6_0, R.id.imv6_1, R.id.imv6_2, R.id.imv6_3, R.id.imv6_4, R.id.imv6_5, R.id.imv6_6, R.id.imv6_7, R.id.imv6_8, R.id.imv6_9},
            {R.id.imv7_0, R.id.imv7_1, R.id.imv7_2, R.id.imv7_3, R.id.imv7_4, R.id.imv7_5, R.id.imv7_6, R.id.imv7_7, R.id.imv7_8, R.id.imv7_9},
            {R.id.imv8_0, R.id.imv8_1, R.id.imv8_2, R.id.imv8_3, R.id.imv8_4, R.id.imv8_5, R.id.imv8_6, R.id.imv8_7, R.id.imv8_8, R.id.imv8_9},
            {R.id.imv9_0, R.id.imv9_1, R.id.imv9_2, R.id.imv9_3, R.id.imv9_4, R.id.imv9_5, R.id.imv9_6, R.id.imv9_7, R.id.imv9_8, R.id.imv9_9}

    };

    private ArrayList<String> tidStringArrayList = new ArrayList<>();
    private ArrayList<String> idNameStringArrayList = new ArrayList<>();
    private ArrayList<String> tnameStringArrayList = new ArrayList<>();
    private ArrayList<String> tznameStringArrayList = new ArrayList<>();

    private ArrayList<Integer> tstatusIntegerArrayList = new ArrayList<>();

    private int position;


    public DeskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create TabLayout
        createTabLayout();

//        Initial View
        initialView();

//      Draw Desk
        drawDesk();

//        Test Show
/*        TextView textView = getView().findViewById(R.id.txtTest);
        String stringTest = "Test1" + "\n" + "Test2" + "\n" + "123";
        textView.setText(stringTest);*/


//        buildDesk(textViews[6][1],3, "3 CT", "12:00", "5");

    } // Main Method

    private void createTabLayout() {
        TabLayout tabLayout = getView().findViewById(R.id.tabLayoutDesk);

        try {
            branchIDString = "1";

            GetMemberWhereID getMemberWhereID = new GetMemberWhereID(getActivity());
            getMemberWhereID.execute(branchIDString, myConstant.getUrlGetZoneWhereBranch());
            String json = getMemberWhereID.get();

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                zoneNameStringArrayList.add(jsonObject.getString("tzname"));
                zoneIDStringArrayList.add(jsonObject.getString("id"));

            }
            Log.d("5MayV1", "tzname ==> " + zoneNameStringArrayList.toString());

            zoneIDString = zoneIDStringArrayList.get(tabAnInt);

            for (int i = 0; i < zoneNameStringArrayList.size(); i += 1) {
                tabLayout.addTab(tabLayout.newTab().setText(zoneNameStringArrayList.get(i)));
            }


//        String[] strings = myConstant.getBillTitleStrings();
//        for (String s : strings) {
//            tabLayout.addTab(tabLayout.newTab().setText(s));
//        }
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    tabAnInt = tab.getPosition();
                    Log.d("5MayV1", "tabAnInt ==> " + tabAnInt);

                    zoneIDString = zoneIDStringArrayList.get(tabAnInt);
                    Log.d("5MayV1", "zoneIDString ==> " + zoneIDString);
                    // Draw Desk
                    drawDesk();

                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("5MayV1", "e ==> " + e);
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("11MarV1", "setUserVisibleHind on DeskFragment");
        drawDesk();
    }

    private void buildDesk(TextView textView, int deskFactorStart, int deskFactorEnd, String cnum, String time, String desk , int tstatus) {
        // the following change is what fixed it
//        TableRow.LayoutParams paramsExample = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

        ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
        layoutParams.width = deskFactorEnd * 120;
        layoutParams.height = deskFactorStart * 120;
        textView.setLayoutParams(layoutParams);


//        int factor = 12 * deskFactor;
//        int factor2 = 2;

        if (tstatus == 0) {
            textView.setBackgroundColor(Color.LTGRAY);
        } else if (tstatus == 1) {
            textView.setBackgroundColor(Color.MAGENTA);
        } else {
            textView.setBackgroundColor(Color.RED);
        }
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(android.R.color.white));
//        paramsExample.setMargins(factor2, factor2, factor2, factor2);
//        textView.setPadding(factor, factor, factor, factor);
        textView.setTextSize(20);
        textView.setText(desk);
//        textView.setText(cnum + "\n" + time + "\n" + desk);
//        textView.setLayoutParams(paramsExample);
    }

    private void drawDesk() {


        String tag = "8decV2";

        try {

            MyConstant myConstant = new MyConstant();

            //joyce
            SharedPreferences sharedPreferences = getActivity()
                    .getSharedPreferences(myConstant.getSharePreferFile(), Context.MODE_PRIVATE);
            String userLogined = sharedPreferences.getString("User", "");
            Log.d("8decV2", "userLogined ==> " + userLogined);
            //---


            GetMemberWhereID getMemberWhereID = new GetMemberWhereID(getActivity());
            getMemberWhereID.execute(zoneIDString, myConstant.getUrlReadAllDesk());
            String jsonString = getMemberWhereID.get();
//
//            GetAllData getAllData = new GetAllData(getActivity());
//            getAllData.execute(myConstant.getUrlReadAllDesk());
//            String jsonString = getAllData.get();
            Log.d("8decV2", "jsonString ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String startDesk = jsonObject.getString("tbstart");
                String endDesk = jsonObject.getString("tbend");
                String tname = jsonObject.getString("tname");
                String tzname = jsonObject.getString("tzname");
                Integer tstatus = jsonObject.getInt("tstatus");

                tstatusIntegerArrayList.add(tstatus);

                tidStringArrayList.add(jsonObject.getString("tid"));
                tnameStringArrayList.add(tname);
                tznameStringArrayList.add(tzname);


                Log.d(tag, "startDesk ==> " + startDesk);
                Log.d(tag, "endDesk ==> " + endDesk);
//                convasDesk(startDesk, endDesk);

                int indexStartPre = findIndexPreAnSub(startDesk, true);
                int indexStartSub = findIndexPreAnSub(startDesk, false);

                int indexEndPre = findIndexPreAnSub(endDesk, true);
                int indexEndSub = findIndexPreAnSub(endDesk, false);

                int deskFactorStart = indexEndPre - indexStartPre + 1;
                int deskFactorEnd = indexEndSub - indexStartSub + 1;

                idNameStringArrayList.add("/imv" + Integer.toString(indexStartPre) + "_" + Integer.toString(indexStartSub) + "}");

                buildDesk(textViews[indexStartPre][indexStartSub], deskFactorStart, deskFactorEnd, "5 CT",
                        "12:30", tname, tstatus);

                textViews[indexStartPre][indexStartSub].setOnClickListener(this);

            } // for

            Log.d("2janV1", "tidStringArrayList ==> " + tidStringArrayList.toString());
            Log.d("2janV1", "idNameStringArrayList ==> " + idNameStringArrayList.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int findIndexPreAnSub(String destString, boolean aBoolean) {

        String[] strings = destString.split("-");
        int result = 0;
        if (aBoolean) {
            result = Integer.parseInt(strings[0]);
        } else {
            result = Integer.parseInt(strings[1]);
        }

        return result;
    }


    private void convasDesk(String startDesk, String endDesk) {

        String[] startStrings = startDesk.split("-");
        String[] endStrings = endDesk.split("-");
        boolean b = true;


        int startX = Integer.parseInt(startStrings[0].trim());
        int startY = Integer.parseInt(startStrings[1].trim());
        int endX = Integer.parseInt(endStrings[0].trim());
        int endY = Integer.parseInt(endStrings[1].trim());

        for (int i = startX; i <= endX; i += 1) {

            for (int i1 = startY; i1 <= endY; i1 += 1) {

                addRed(i, i1, b);
                b = false;

            } // for Y

        } // for X

    }

    private void addRed(int indexX, int indexY, boolean b) {
        textViews[indexX][indexY].setBackgroundColor(Color.RED);

        if (b) {
            textViews[indexX][indexY].setText("No.10");
            textViews[indexX][indexY].setTextColor(Color.WHITE);
            textViews[indexX][indexY].setTextSize(10);
        }

    }

    private void initialView() {
        for (int i = 0; i < 10; i += 1) {

            for (int i1 = 0; i1 < 10; i1 += 1) {

                textViews[i][i1] = getView().findViewById(ints[i][i1]);

            } // for1

        } // for0
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_desk, container, false);
    }

    @Override
    public void onClick(View v) {

        Log.d("24decV1", v.toString());

        String result = v.toString();
        result = result.substring(result.lastIndexOf("/"));
        Log.d("24decV1", "result ==> " + result);

        String tidClick = "";
        position = 0;

        for (int i = 0; i < idNameStringArrayList.size(); i += 1) {
            if (result.equals(idNameStringArrayList.get(i))) {
                position = i;
            }
        }   //for

        Log.d("24decV1", "tid Click ==> " + tidStringArrayList.get(position));

        if (tstatusIntegerArrayList.get(position) == 0) {
//            Show Status
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

            alertDialogBuilder.setCancelable(false);

            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            final View view = layoutInflater.inflate(R.layout.layout_alert, null);
            alertDialogBuilder.setView(view);

            alertDialogBuilder.setTitle("For Desk " + tnameStringArrayList.get(position))
                    .setMessage("Please Fill All Blank")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            EditText editText = view.findViewById(R.id.edtAmountCustomer);
                            String amountCustomerString = editText.getText().toString().trim();
                            if (amountCustomerString.isEmpty()) {
                                Toast.makeText(getActivity(), "Please Fill Amount Customer", Toast.LENGTH_SHORT).show();
                            } else {
                                boolean totalBill = true;
                                RadioButton radioButton = view.findViewById(R.id.radBill);
                                if (radioButton.isChecked()) {
                                    totalBill = true;
                                } else {
                                    totalBill = false;
                                }

                                String tidString = tidStringArrayList.get(position);
                                String tnameString = tnameStringArrayList.get(position);
                                String tznameString = tznameStringArrayList.get(position);


                                Log.d("2janV1", "Amunt ส่ง==> " + amountCustomerString);
                                Log.d("2janV1", "totalBill ส่ง==> " + totalBill);
                                Log.d("2janV1", "tidString ส่ง==> " + tidString);
                                Log.d("2janV1", "tnameString ส่ง==> " + tnameString);
                                Log.d("2janV1", "tznameString ส่ง==> " + tznameString);

                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.contentServiceFragment, ServiceFragment.serviceInstant(2,
                                                amountCustomerString,
                                                totalBill,
                                                tidString,
                                                tnameString,
                                                tznameString))
                                        .commit();


                            }

                            dialog.dismiss();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();


        } else {
//            To Do
            MyConstant myConstant = new MyConstant();
            String[] strings = myConstant.getDetailStrings();
//            strings ==> {"idBill", "Time", "cnum", "type", "name", "Zone", "Desk"}

            try {

                Intent intent = new Intent(getActivity(), DetailActivity.class);

                String tid = tidStringArrayList.get(position);

                GetDtailBillWhereID getDtailBillWhereID = new GetDtailBillWhereID(getActivity());
                getDtailBillWhereID.execute(tid, myConstant.getUrlGetOrderWhereTID());

                String resultJSoN = getDtailBillWhereID.get();

                Log.d("11MarV2", "TID ==> " + tid);
                Log.d("11MarV2", "resultJSoN ==> " + resultJSoN);

                JSONArray jsonArray = new JSONArray(resultJSoN);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
//
                intent.putExtra("idBill", jsonObject.getString("id"));
                intent.putExtra("Time", jsonObject.getString("date"));
                intent.putExtra("cnum", jsonObject.getString("cnum"));
                intent.putExtra("type", jsonObject.getString("type"));
                intent.putExtra("name", jsonObject.getString("name"));
                intent.putExtra("Zone", jsonObject.getString("tzname"));
                intent.putExtra("Desk", jsonObject.getString("tname"));
//                intent.putExtra("tid", jsonObject.getString("tid"));
                intent.putExtra("tid", tid);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } // if





    }   // onClick
}