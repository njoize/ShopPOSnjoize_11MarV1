package njoize.dai_ka.com.demotestprint;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment {

    //    Explicit
    private String amountCustomerString, tidString, tnameString, tznameString;
    private boolean totalBillABoolean;
    private String idCategoryClick;
    private MyManageSQLite myManageSQLite;
    private int valueCatAnInt;

    public FoodFragment() {
        // Required empty public constructor
    }

    public static FoodFragment foodInstante(String amountCustomer,
                                            boolean totalBill,
                                            String tidString,
                                            String tnameString,
                                            String tznameString) {

        FoodFragment foodFragment = new FoodFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Amount", amountCustomer);
        bundle.putString("Tid", tidString);
        bundle.putString("Tname", tnameString);
        bundle.putString("Tzname", tznameString);
        bundle.putBoolean("Bill", totalBill);
        foodFragment.setArguments(bundle);
        return foodFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myManageSQLite = new MyManageSQLite(getActivity());

//        Receive Value
        receiveValue();

//        Category RecyclerView
        categoryRecyclerView();

//        Check Order
        checkOrder();

//        Cancel Controller
        cancelController();

//        Order Controller
        orderController();

//        Payment Controller
        paymentController();


    }   // Main Method

    private void paymentController() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        String catString = sharedPreferences.getString("Cat", "0");
        valueCatAnInt = Integer.parseInt(catString);

        MyConstant myConstant = new MyConstant();
        int[] allowPaymentInts = myConstant.getAllowPayment();

        for (int i = 0; i < allowPaymentInts.length; i += 1) {
            if (valueCatAnInt == allowPaymentInts[i]) {
                Button button = getView().findViewById(R.id.btnPayment);
                button.setVisibility(View.VISIBLE);
            }
        }
    }

    private void orderController() {
        Button button = getView().findViewById(R.id.btnOrder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Confirm Order").setMessage("You want this Order?").setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String tag = "2janV3";

                        MyConstant myConstant = new MyConstant();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(myConstant.getSharePreferFile(), Context.MODE_PRIVATE);

                        String user = sharedPreferences.getString("User", "");
                        String numcus = amountCustomerString;
                        String billtype = "";
                        if (totalBillABoolean) {
                            billtype = "2";
                        } else {
                            billtype = "1";
                        }
                        String tid = tidString;
                        String pid = "";
                        String price = "";
                        String amount = "";

                        Log.d(tag, "user" + user);
                        Log.d(tag, "numcus" + numcus);
                        Log.d(tag, "billtype" + tid);
                        Log.d(tag, "tid" + tid);

                        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTable", null);
                        cursor.moveToFirst();

                        for (int i = 0; i < cursor.getCount(); i += 1) {

                            pid = cursor.getString(1);
                            price = cursor.getString(3);
                            amount = cursor.getString(4);

                            Log.d(tag, "pid[" + i + "] = " + pid);
                            Log.d(tag, "price[" + i + "] = " + price);
                            Log.d(tag, "amount[" + i + "] = " + amount);

//                            Upload to Server
                            try {

                                OrderThread orderThread = new OrderThread(getActivity());
                                orderThread.execute(user, numcus, billtype, tid, pid, price, amount, myConstant.getUrlAddOrder());

                                emptySQLite();


                                GetUserWhereName getUserWhereName = new GetUserWhereName(getActivity());

                                getUserWhereName.execute(user, myConstant.getUrlGetOrderWhereUser());
                                String resultJSoN = getUserWhereName.get();
                                Log.d("10marV1", "resultJSoN ==> " + resultJSoN);

                                JSONArray jsonArray = new JSONArray(resultJSoN);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//
                                Intent intent = new Intent(getActivity(), DetailActivity.class); // go to DetailActivity
//                                intent.putExtra("Login", resultJSoN);
////                                intent.putExtra("Status", true);
////                                intent.putExtra("mid", idString);
////                                Log.d("28FebV1", "mid adapter ==> " + idString);
//
//
                                intent.putExtra("idBill", jsonObject.getString("id"));
                                intent.putExtra("Time", jsonObject.getString("date"));
                                intent.putExtra("cnum", jsonObject.getString("cnum"));
                                intent.putExtra("type", jsonObject.getString("type"));
                                intent.putExtra("name", jsonObject.getString("name"));
                                intent.putExtra("Zone", jsonObject.getString("tzname"));
                                intent.putExtra("Desk", jsonObject.getString("tname"));
                                intent.putExtra("tid", jsonObject.getString("tid"));



                                startActivity(intent);
                                getActivity().finish();


                            } catch (Exception e) {
                                Log.d(tag, "e upload ==> " + e.toString());
                            }

                            cursor.moveToNext();
                        }


                        dialog.dismiss();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


            }
        });
    }

    private void cancelController() {
        Button button = getView().findViewById(R.id.btnCancle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emptySQLite();

            }
        });
    }

    private void emptySQLite() {
        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
            sqLiteDatabase.delete(MyOpenHelper.database_table, null, null);
            checkOrder();

        } catch (Exception e) {
            Log.d("2janV1", "e cancel ==> " + e.toString());
        }
    }

    private void checkOrder() {

        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
            cursor.moveToFirst();

            Log.d("25decV2", "Cursor Work");

            ArrayList<String> nameFoodStringArrayList = new ArrayList<>();
            ArrayList<String> amounStringArrayList = new ArrayList<>();
            ArrayList<String> priceSumStringArrayList = new ArrayList<>();
            final ArrayList<String> idSQLiteStringArrayList = new ArrayList<>();

            int totalAInt = 0;

            for (int i = 0; i < cursor.getCount(); i += 1) {

                nameFoodStringArrayList.add(cursor.getString(2));
                amounStringArrayList.add(cursor.getString(4));
                idSQLiteStringArrayList.add(cursor.getString(0));

                int priceInt = Integer.parseInt(cursor.getString(3));
                int amountInt = Integer.parseInt(amounStringArrayList.get(i));

                priceSumStringArrayList.add(Integer.toString(priceInt * amountInt));

                totalAInt = totalAInt + (priceInt * amountInt);

                cursor.moveToNext();
            }   // for

            cursor.close();

            Log.d("25decV2", "nameFood ==> " + nameFoodStringArrayList.toString());

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerOrder);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            OrderAdapter orderAdapter = new OrderAdapter(getActivity(), nameFoodStringArrayList, amounStringArrayList,
                    priceSumStringArrayList, new OnClickImage() {
                @Override
                public void onClickImage(View view, int position, boolean status) {
                    Log.d("11MarV1", "position = " + position + " Status" + status);

                    toolIncDec(idSQLiteStringArrayList.get(position),status);

                }
            });

//            increaseOrDecrease(idSQLiteStringArrayList.get(positions));

            recyclerView.setAdapter(orderAdapter);

//            Show AmountPrice
            TextView textView = getView().findViewById(R.id.txtTotal);
            textView.setText("รวมทั้งสิ้น :   " + Integer.toString(totalAInt));
//            textView.setText("รวมทั้งสิ้น : " + Integer.toString(totalAInt) + " บาท");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void increaseOrDecrease(final String idSQLit) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Increase or Decrease")
                .setMessage("Please Click Button")
                .setPositiveButton("Increase", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        toolIncDec(idSQLit, true);
                    }
                }).setNegativeButton("Decrease", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                toolIncDec(idSQLit, false);
            }
        }).show();


    }

    private void toolIncDec(String idSQLite, boolean status) {

        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE WHERE id=" + "'" + idSQLite + "'", null);
        cursor.moveToFirst();

        String amountString = cursor.getString(4);
        Log.d("25decV4", "Current Amount ==> " + amountString);

        int amountInt = Integer.parseInt(amountString);
        cursor.close();

        if (status) {
//            Increase Status
            amountInt += 1;
            sqLiteDatabase.execSQL("UPDATE orderTABLE SET Amount=" + "'" + Integer.toString(amountInt) + "'" + " WHERE id=" + "'" + idSQLite + "'");
            checkOrder();
        } else {
//            Decrease Status
            if (amountInt == 1) {
                sqLiteDatabase.delete("orderTABLE", "id" + "=" + idSQLite, null);
                checkOrder();
            } else {
                amountInt -= 1;
                sqLiteDatabase.execSQL("UPDATE orderTABLE SET Amount=" + "'" + Integer.toString(amountInt) + "'" + " WHERE id=" + "'" + idSQLite + "'");
                checkOrder();
            }

        }


    }

    private void foodRecyclerView(String idCategoryString) {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerFood);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        MyConstant myConstant = new MyConstant();
        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(myConstant.getSharePreferFile(), Context.MODE_PRIVATE);
        String userLogin = sharedPreferences.getString("User", "");

        Log.d("25decV1", "idCat ==> " + idCategoryString);
        Log.d("25decV1", "userLogin ==> " + userLogin);

        final ArrayList<String> foodStringArrayList = new ArrayList<>();
        final ArrayList<String> priceStringArrayList = new ArrayList<>();
        final ArrayList<String> idFoodStringArrayList = new ArrayList<>();

        try {

            GetFoodWhereIdAndUser getFoodWhereIdAndUser = new GetFoodWhereIdAndUser(getActivity());
            getFoodWhereIdAndUser.execute(idCategoryString, userLogin, myConstant.getUrlGetFoodWhereIdAndUser());
            String jsonString = getFoodWhereIdAndUser.get();
            Log.d("25decV1", "jsonString123 ==> " + jsonString);

            if (jsonString.equals("null")) {
                recyclerView.setAdapter(null);
            }

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                foodStringArrayList.add(jsonObject.getString("pname"));
                priceStringArrayList.add(jsonObject.getString("price"));
                idFoodStringArrayList.add(jsonObject.getString("id"));
            }

            Log.d("25decV1", "food ==> " + foodStringArrayList.toString());
            FoodAdapter foodAdapter = new FoodAdapter(getActivity(), foodStringArrayList, priceStringArrayList, new OnClickItem() {
                @Override
                public void onClickItem(View view, int positions) {
                    Log.d("25DecV2", "Food ID Choose ==> " + idFoodStringArrayList.get(positions));
                    Log.d("25DecV2", "foodName Choose ==> " + foodStringArrayList.get(positions));
                    Log.d("25DecV2", "Food Price ==> " + priceStringArrayList.get(positions));

                    if (checkHaveFood(idFoodStringArrayList.get(positions))) {
//                            Increase Old Order
                        increaseAmountFood(idFoodStringArrayList.get(positions));
                    } else {
//                        Add New Order
                        myManageSQLite.addValueToSQLite(idFoodStringArrayList.get(positions),
                                foodStringArrayList.get(positions), priceStringArrayList.get(positions), "1");
                    }

                    checkOrder();

                }
            });

            recyclerView.setAdapter(foodAdapter);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("25decV2", "e ==> " + e.toString());
        }


    }

    private void increaseAmountFood(String idFoodString) {

        SQLiteDatabase sqLiteDatabase = getActivity()
                .openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE WHERE idFood = " + "'" + idFoodString + "'", null);
        cursor.moveToFirst();

        String amountString = cursor.getString(4);
        int amountInt = Integer.parseInt(amountString);
        amountInt += 1;
        amountString = Integer.toString(amountInt);
        cursor.close();

        sqLiteDatabase.execSQL("UPDATE orderTABLE SET Amount=" + "'" + amountString + "'" + " WHERE idFood=" + "'" + idFoodString + "'");

    }

    private boolean checkHaveFood(String idFoodString) {

        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
            cursor.moveToFirst();

            boolean resule = false; // false Without IdFood in Database

            for (int i = 0; i < cursor.getCount(); i += 1) {

                if (idFoodString.equals(cursor.getString(1))) {
                    resule = true;
                }

                cursor.moveToNext();
            }
            cursor.close();

            Log.d("25decV4", "result ==> " + resule);
            return resule;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    private void categoryRecyclerView() {

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerCategory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        try {

            MyConstant myConstant = new MyConstant();
            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlGetCategoryString());

            String jsonString = getAllData.get();
            Log.d("24decV3", "jsonString ==> " + jsonString);

            ArrayList<String> categoryStringArrayList = new ArrayList<>();
            final ArrayList<String> idCategoryStringArrayList = new ArrayList<>();

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                categoryStringArrayList.add(jsonObject.getString("prcname"));
                idCategoryStringArrayList.add(jsonObject.getString("id"));
            }

            CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categoryStringArrayList, new OnClickItem() {
                @Override
                public void onClickItem(View view, int positions) {
                    Log.d("24decV3", "Position ==> " + positions);
                    foodRecyclerView(idCategoryStringArrayList.get(positions));
                }
            });

            recyclerView.setAdapter(categoryAdapter);

            foodRecyclerView(idCategoryStringArrayList.get(0));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void receiveValue() {
        amountCustomerString = getArguments().getString("Amount");
        tidString = getArguments().getString("Tid");
        tnameString = getArguments().getString("Tname");
        tznameString = getArguments().getString("Tzname");
        totalBillABoolean = getArguments().getBoolean("Bill");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("OrderFood", Context.MODE_PRIVATE);
        amountCustomerString = sharedPreferences.getString("Amount", "");
        tidString = sharedPreferences.getString("Tid", "");
        tnameString = sharedPreferences.getString("Tname", "");
        tznameString = sharedPreferences.getString("Tzname", "");
        totalBillABoolean = sharedPreferences.getBoolean("Total", true);


        Log.d("2janV1", "amount รับ Food ==> " + amountCustomerString);
        Log.d("2janV1", "Tid รับ Food ==> " + tidString);
        Log.d("2janV1", "Tname รับ Food ==> " + tnameString);
        Log.d("2janV1", "Tzname รับ Food ==> " + tznameString);
        Log.d("2janV1", "Bill รับ  Food ==> " + totalBillABoolean);

//        Show Desk
        TextView deskTextView = getView().findViewById(R.id.txtDesk);
        deskTextView.setText("Table: " + tnameString);

//        Show Zone
        TextView zoneTextView = getView().findViewById(R.id.txtZone);
        zoneTextView.setText(tznameString);

//        Show Amount User
        TextView amountTextView = getView().findViewById(R.id.txtAmountUser);
        amountTextView.setText("Customer: " + amountCustomerString);

//        Show Total Bill
        TextView totalTextView = getView().findViewById(R.id.txtTotalBill);
        totalTextView.setText("Bill: " + findTotalText(totalBillABoolean));


    }

    private String findTotalText(boolean totalBillABoolean) {

        String result = "แยกบิล";
        if (totalBillABoolean) {
            result = "รวมบิล";
        }

        return result;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food, container, false);
    }

}