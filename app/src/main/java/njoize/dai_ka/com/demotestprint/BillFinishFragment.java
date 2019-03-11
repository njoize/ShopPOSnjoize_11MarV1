package njoize.dai_ka.com.demotestprint;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillFinishFragment extends Fragment {


    private MyConstant myConstant = new MyConstant();
    private String tag = "6decV1";


    public BillFinishFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        Create RecyclerView
        createRecyclerView();

    } // Method Main

    private void createRecyclerView() {

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewBillFinish);
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

        try {

            ReadAllDataThread readAllDataThread = new ReadAllDataThread(getActivity());
            readAllDataThread.execute(myConstant.getUrlBillFinishWhereOrder());
            String jsonString = readAllDataThread.get();
            Log.d(tag, "jsonString ==> " + jsonString);
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

            } // for




        } catch (Exception e) {
            e.printStackTrace();
        }
    } // createRecyclerView


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_finish, container, false);
    }

}
