package njoize.dai_ka.com.demotestprint;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends Fragment {

    private MyConstant myConstant = new MyConstant();
    private String tag = "2decV1";
    private int tabAnInt = 0;


    public BillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create TabLayout
        createTabLayout();


//        Create RecyclerView
        createRecyclerView(tabAnInt);

    } // Method Main

    @Override
    public void onResume() {
        super.onResume();

        Log.d("11MarV1", "onResume BillFragment Work");

        createRecyclerView(tabAnInt);


    }

    private void createTabLayout() {
        TabLayout tabLayout = getView().findViewById(R.id.tabLayoutBill);
        String[] strings = myConstant.getBillTitleStrings();
        for (String s : strings) {
            tabLayout.addTab(tabLayout.newTab().setText(s));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabAnInt = tab.getPosition();
                Log.d("8devV1", "tabAnInt ==> " + tabAnInt);
                createRecyclerView(tabAnInt);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    } // createTabLayout

    private void createRecyclerView(int tabPosition) {

        if (tabPosition == 3) {
//            Online Status
            tabPosition = 0;

        }

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(myConstant.getSharePreferFile(), Context.MODE_PRIVATE);
        String userLogined = sharedPreferences.getString("User", "");


        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewBill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        final ArrayList<String> zoneStringArrayList = new ArrayList<>();
        final ArrayList<String> deskStringArrayList = new ArrayList<>();
        ArrayList<String> detail1StringArrayList = new ArrayList<>();
        ArrayList<String> detail2StringArrayList = new ArrayList<>();
        ArrayList<String> detail3StringArrayList = new ArrayList<>();
        final ArrayList<String> idBillStringArrayList = new ArrayList<>();

        final ArrayList<String> cnumStringArrayList = new ArrayList<>();
        final ArrayList<String> typeStringArrayList = new ArrayList<>();
        final ArrayList<String> nameStringArrayList = new ArrayList<>();
        final ArrayList<String> timeStringArrayList = new ArrayList<>();

        ArrayList<String> bgColorStringArrayList = new ArrayList<>();

        final ArrayList<String> tidStringArrayList = new ArrayList<>();

        try {

            ReadAllDataThread readAllDataThread = new ReadAllDataThread(getActivity());
            readAllDataThread.execute(userLogined,
                    Integer.toString(tabPosition),
                    myConstant.getUrlBillWhereOrder());
            String jsonString = readAllDataThread.get();
            Log.d(tag, "jsonString ==> " + jsonString);

            if (jsonString.equals("null")) {
                recyclerView.setAdapter(null);
            }

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                zoneStringArrayList.add("Zone " + jsonObject.getString("tzname"));
                zoneStringArrayList.add(jsonObject.getString("tzname"));
                deskStringArrayList.add(jsonObject.getString("tname"));
                detail1StringArrayList.add(jsonObject.getString("TotalList") + " รายการ " + jsonObject.getString("TotalPrice") + " บาท");
                detail2StringArrayList.add(jsonObject.getString("date") + " ลูกค้า " + jsonObject.getString("cnum") + " คน " + jsonObject.getString("type"));
                detail3StringArrayList.add("โดย " + jsonObject.getString("name"));
                idBillStringArrayList.add(jsonObject.getString("id"));
                timeStringArrayList.add(jsonObject.getString("date"));
                cnumStringArrayList.add(jsonObject.getString("cnum"));
                typeStringArrayList.add(jsonObject.getString("type"));
                nameStringArrayList.add(jsonObject.getString("name"));

                bgColorStringArrayList.add(Integer.toString(tabPosition));

                // อย่าลืมแก้เป็น tid ดึง database << ดึงแล้ว
                tidStringArrayList.add(jsonObject.getString("tid"));

            } // for

            BillRecyclerViewAdapter billRecyclerViewAdapter = new BillRecyclerViewAdapter(getActivity(),
                    zoneStringArrayList, deskStringArrayList, detail1StringArrayList,
                    detail2StringArrayList, detail3StringArrayList, bgColorStringArrayList, new OnClickItem() {
                @Override
                public void onClickItem(View view, int positions) {
                    Log.d("2decV2", "You Click ==> " + positions);

                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    MyConstant myConstant = new MyConstant();
                    String[] strings = myConstant.getDetailStrings();

                    intent.putExtra(strings[0], idBillStringArrayList.get(positions));
                    intent.putExtra(strings[1], timeStringArrayList.get(positions));
                    intent.putExtra(strings[2], cnumStringArrayList.get(positions));
                    intent.putExtra(strings[3], typeStringArrayList.get(positions));
                    intent.putExtra(strings[4], nameStringArrayList.get(positions));
                    intent.putExtra(strings[5], zoneStringArrayList.get(positions));
                    intent.putExtra(strings[6], deskStringArrayList.get(positions));

                    intent.putExtra("tid", tidStringArrayList.get(positions));

                    startActivity(intent);
                    
                }
            });
            recyclerView.setAdapter(billRecyclerViewAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        } // try


    } // createRecyclerView


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

} // Main Class
