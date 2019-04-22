package njoize.dai_ka.com.demotestprint;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment implements SearchView.OnQueryTextListener {

    private MyConstant myConstant;
    private ArrayList<String> nameMemberStringArrayList;
    ArrayList<NameMemberModel> nameMemberModelArrayList = new ArrayList<NameMemberModel>();
    private String searchBar;
    private SearchView searchView;
    private MemberListViewAdapter memberListViewAdapter;
    private ArrayList<String> idStringArrayList;
    private boolean statusFrom = true; // true ==> From TabHost Member, false ==> From Bill Select Member Button


    public MemberFragment() {
        // Required empty public constructor
    }

//    status ==> true (from Packer), ==> false (Select Button)
    public static MemberFragment memberInstance(boolean status, ArrayList<String> stringArrayList) {
        MemberFragment memberFragment = new MemberFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("Status", status);
        bundle.putStringArrayList("KeyArrayList", stringArrayList);
        memberFragment.setArguments(bundle);
        return memberFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        statusFrom = getArguments().getBoolean("Status", true);
        Log.d("22AprilV1", "Receive Status ==> " + statusFrom);

        myConstant = new MyConstant();
        nameMemberStringArrayList = new ArrayList<>();
        idStringArrayList = new ArrayList<>();

//        Create Toolbar
        createToolbar();

//        Create RecyclerView
        createRecyclerView();

//        Demo ReadSQLite
        demoReadSQLite();


    } // Main Medthod

    private void demoReadSQLite() {
        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MasterOpenHelper.database_name,
                Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM testTABLE", null);
        cursor.moveToFirst();

        String name, surname, address, tel;

        for (int i = 0; i < cursor.getCount(); i += 1) {
            name = cursor.getString(1);
            surname = cursor.getString(2);
            Log.d("22AprilV1", "name ==> " + name + " surname ==> " + surname);
            cursor.moveToNext();
        }

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarMemberList);

        if (!statusFrom) {

            toolbar.setVisibility(View.VISIBLE);
            ((ShowMemberListActivity) getActivity()).setSupportActionBar(toolbar);
            ((ShowMemberListActivity) getActivity()).getSupportActionBar().setTitle("Member");
            ((ShowMemberListActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((ShowMemberListActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            final ArrayList<String> stringArrayList = getArguments().getStringArrayList("KeyArrayList");
            String[] strings = new String[7];
            for (int i = 0; i < strings.length; i += 1) {
                strings[i] = stringArrayList.get(i);
            }

            final String tidString = stringArrayList.get(7);
            String statusString = stringArrayList.get(8);
            final boolean statusBool = Boolean.parseBoolean(statusString);
            final String mid = stringArrayList.get(9);


            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), DetailActivity.class);

                    MyConstant myConstant = new MyConstant();
                    String[] columnStrings1 = myConstant.getDetailStrings();
                    for (int i = 0; i < columnStrings1.length; i += 1) {
                        intent.putExtra(columnStrings1[i], stringArrayList.get(i));
                    }
                    intent.putExtra("tid", tidString);
                    intent.putExtra("Status", statusBool);
                    intent.putExtra("mid", mid);

                    startActivity(intent);
                    getActivity().finish();
                }
            });
        }

    }

    private void createRecyclerView() {
        try {

            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlGetAllMember());
            String json = getAllData.get();

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameMemberStringArrayList.add(jsonObject.getString("name") + " " + jsonObject.getString("sname") + " " + jsonObject.getString("tel"));
                idStringArrayList.add(jsonObject.getString("id"));
            }
            Log.d("20FebV1", "nameMember ==> " + nameMemberStringArrayList.toString());



            for (int i = 0; i < nameMemberStringArrayList.size(); i += 1) {
                NameMemberModel nameMemberModel = new NameMemberModel(idStringArrayList.get(i), nameMemberStringArrayList.get(i));
                nameMemberModelArrayList.add(nameMemberModel);
            }

            ListView listView = getView().findViewById(R.id.listViewMember);
            memberListViewAdapter = new MemberListViewAdapter(getActivity(), nameMemberModelArrayList, statusFrom);

            listView.setAdapter(memberListViewAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("28FebV1", "click ListView");

                    /*Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
                    intent.putExtra("mid", idStringArrayList.get(position));

                    startActivity(intent);*/


                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });

//            https://stackoverflow.com/questions/17670685/custom-searchview-whole-clickable-in-android/33623277#33623277
//            searchView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    searchView.setIconified(false);
//                }
//            });

            getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchView.setIconified(false);
                }
            });

            searchView = getView().findViewById(R.id.searchViewMember);
            searchView.setOnQueryTextListener(this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member, container, false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        memberListViewAdapter.filter(text);
        return false;
    }
}
