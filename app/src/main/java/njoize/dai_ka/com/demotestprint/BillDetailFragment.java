package njoize.dai_ka.com.demotestprint;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zj.wfsdk.WifiCommunication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BillDetailFragment extends Fragment {

    //    Explicit
    private WifiCommunication wifiCommunication;
    private boolean aBoolean = false;
    private boolean communicationABoolean = true; // true ==> Can Print, false ==> Disable Print
    private Button button, printAgainButton;
    private boolean buttonBoolean = true; // true ==> กำลังเชือมต่อPrinter
    private int anInt = 0;
    private int total, myTotal, discount, discountp;

    private ArrayList<String> nameStringArrayList, numStringArrayList, priceStringArrayList;


    private String idBillString, timeString, cnumString, typeString, nameString, zoneString, deskString;
    private String tidString;
    private String midString = "0";
    private String discountString = "0";
    private String tag = "2decV2";
    private MyConstant myConstant = new MyConstant();

    private EditText couponEditText;


    public BillDetailFragment() {
        // Required empty public constructor
    }

    public static BillDetailFragment billDetailInstance(String idString,
                                                        String timeString,
                                                        String cnumString,
                                                        String typeString,
                                                        String nameString,
                                                        String zoneString,
                                                        String deskString,
                                                        String tidString,
                                                        boolean status,
                                                        String midString) {

        BillDetailFragment billDetailFragment = new BillDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("idBill", idString);
        bundle.putString("Time", timeString);
        bundle.putString("cnum", cnumString);
        bundle.putString("type", typeString);
        bundle.putString("name", nameString);
        bundle.putString("Zone", zoneString);
        bundle.putString("Desk", deskString);
        bundle.putString("tid", tidString);
        bundle.putBoolean("Status", status);
        bundle.putString("mid", midString);
        billDetailFragment.setArguments(bundle);
        return billDetailFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Create Toolbar
        createToolbar();

//        Check Connected Printer
        createCommunicationPrinter();

//        Print Controller
        printController();

//        Get OID
        getOID();

//        Create Detail
        createDetail();

//        Show Text
        showText();

//        SelectMember Controller
        selectMemberController();


    } // Main Method

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            Log.d("28FebV1", "Result OK");
        }


    }

    private void selectMemberController() {
        Button button = getView().findViewById(R.id.btnSelectMember);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowMemberListActivity.class);
                startActivity(intent);
                getActivity().finish();

//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.contentDetailFragment, MemberFragment.memberInstance(false))
//                        .addToBackStack(null)
//                        .commit();


            }
        });
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarDetail);
        ((DetailActivity) getActivity()).setSupportActionBar(toolbar);
        ((DetailActivity) getActivity()).getSupportActionBar().setTitle("Detail");
        ((DetailActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((DetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().finish();
                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
    }

    private void printController() {
        button = getView().findViewById(R.id.btnPayment);

        if (buttonBoolean) {

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        Log.d("11MarV1", "You Click กำลังเชื่อมต่อปรินเตอร์");

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("Payment Method");

                        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                        final View view = layoutInflater.inflate(R.layout.alertdialog_payment, null);
                        alertDialogBuilder.setView(view);

                        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });


                        final TextView titleTextView = view.findViewById(R.id.txtTitle);
                        TextView paymentTextView = view.findViewById(R.id.txtPayment);
                        paymentTextView.setText("ยอดชำระ : " + Integer.toString(total) + " บาท");


                        final int myTotal = total;
                        final String prefix = "เงินทอน : ";

//                    titleTextView.setText(prefix + alertCalculate(total) + " บาท");

                        final CheckBox cashCheckBox = view.findViewById(R.id.chbCash);
                        final CheckBox creditCheckBox = view.findViewById(R.id.chbCredit);
                        final CheckBox couponCheckBox = view.findViewById(R.id.chbCoupon);

                        final EditText cashEditText = view.findViewById(R.id.edtMoneyCash);
                        final String moneyCashString = cashEditText.getText().toString().trim();

                        final EditText creditEditText = view.findViewById(R.id.edtCredit);
                        final String moneyCreditString = creditEditText.getText().toString().trim();


                        couponEditText = view.findViewById(R.id.edtCoupon);
                        final String moneyCouponString = couponEditText.getText().toString().trim();


                        EditText discountEditText = view.findViewById(R.id.edtDiscount);
                        //final String discountString = discountEditText.getText().toString().trim();
                        discountEditText.setText(Integer.toString(discount));


//                        For Discount
                        discountEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                //String ediscount = s.toString();

                                //Log.d("15MarV2", "edtDiscount ==> " + ediscount);

                            }
                        });

//                        For Cash
                        cashEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                titleTextView.setText(alertCalculate(s.toString()));
                                //creditEditText.setText(showChangeMoney(s.toString(), ediscount,  ));
                                creditEditText.setText(showChangeMoney(s.toString()));
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


//                        For Credit
                        creditEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


//                        For Coupon
                        couponEditText.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });


                        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                uploadToServer(cashCheckBox.isChecked(), creditCheckBox.isChecked(), couponCheckBox.isChecked(),
                                        moneyCashString, moneyCreditString, moneyCouponString, discountString);


                            }
                        });
                        alertDialogBuilder.show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } // onClick
            });

        }   // if


        printAgainButton = getView().findViewById(R.id.btnPaymentAgain);
        printAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCommunicationPrinter();
                communicationABoolean = true;
            }
        });

    }

    //private String showChangeMoney(String cashString, discountString, couponString) {
    private String showChangeMoney(String cashString) {

//        for Cash
        int cashInt = 0;
        String result = "";

        if (cashString.length() == 0) {
            cashInt = 0;
        } else {
            cashInt = Integer.parseInt(cashString);
        }

//        for Coupon
        int answer = total - cashInt;

        if (answer <= 0) {
            result = "";
        } else {
            result = Integer.toString(answer);
        }

        return result;
    }

    private String alertCalculate(String moneyString) {

        int moneyInt = 0;

        if (moneyString.length() == 0) {
            moneyInt = 0;
        } else {
            moneyInt = Integer.parseInt(moneyString);
        }

        String couponString = couponEditText.getText().toString().trim();
        if (couponString.isEmpty()) {
            couponString = "0";
        }

        int answerInt = moneyInt + Integer.parseInt(couponString) - total;

        if (answerInt <= 0) {
            answerInt = 0;
            myTotal = total - moneyInt;
        }

        return "เงินทอน : " + Integer.toString(answerInt) + " บาท";
    }

    private void showText() {
        TextView leftTextView = getView().findViewById(R.id.txtLeft);
        TextView rightTextView = getView().findViewById(R.id.txtRight);

        leftTextView.setText(timeString + " ลูกค้า " + cnumString + " คน " + typeString + "\nโดย " + nameString);
        rightTextView.setText(zoneString + "\nโต๊ะ " + deskString);


    }

    private void createDetail() {

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewBillDetail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        nameStringArrayList = new ArrayList<>();
        ArrayList<String> detailStringArrayList = new ArrayList<>();
        numStringArrayList = new ArrayList<>();
        ArrayList<String> amountStringArrayList = new ArrayList<>();
        ArrayList<String> billStringArrayList = new ArrayList<>();
        priceStringArrayList = new ArrayList<>();


        try {

            GetDtailBillWhereID getDtailBillWhereID = new GetDtailBillWhereID(getActivity());
            getDtailBillWhereID.execute(idBillString, myConstant.getUrlBillDetailWhereOID());
            String jsonString = getDtailBillWhereID.get();
            Log.d(tag, jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStringArrayList.add(jsonObject.getString("pname"));
                detailStringArrayList.add(jsonObject.getString("des"));
                numStringArrayList.add(jsonObject.getString("num"));
                amountStringArrayList.add("ราคา " + jsonObject.getString("price") + " บาท จำนวน " + jsonObject.getString("num"));
                billStringArrayList.add(jsonObject.getString("setpr"));
                priceStringArrayList.add(jsonObject.getString("sumPrice"));
//                priceStringArrayList.add(jsonObject.getString("price") + ".-");
            }

            BillDetailAdapter billDetailAdapter = new BillDetailAdapter(getActivity(), nameStringArrayList,
                    detailStringArrayList, amountStringArrayList, billStringArrayList, priceStringArrayList);
            recyclerView.setAdapter(billDetailAdapter);


            total = 0;
            for (String s : priceStringArrayList) {
                total = total + Integer.parseInt(s.trim());
            }


            //        Show Status
            boolean status = getArguments().getBoolean("Status");

            Log.d("11MarV1", "Status ==> " + status);

            if (status) {


                try {

                    GetMemberWhereID getMemberWhereID = new GetMemberWhereID(getActivity());
                    getMemberWhereID.execute(midString, myConstant.getUrlGetMemberWhereID());
                    String jsonString2 = getMemberWhereID.get();
                    Log.d("15MarV1", "json ==> " + jsonString2);

                    JSONArray jsonArray2 = new JSONArray(jsonString2);
                    for (int i = 0; i < jsonArray2.length(); i += 1) {

                        JSONObject jsonObject = jsonArray2.getJSONObject(i);
                        String nameJsonString = jsonObject.getString("sname");
                        String discountJsonString = jsonObject.getString("discount");


                        discountp = Integer.parseInt(discountJsonString);
                        discount = total * discountp / 100;

                        String discountString = Integer.toString(discount);

                        TextView textView1 = getView().findViewById(R.id.txtMember);
                        textView1.setText(nameJsonString);

                        TextView textView2 = getView().findViewById(R.id.txtDiscountText);
                        textView2.setText("ส่วนลด " + discountJsonString + "% :");

                        TextView textView3 = getView().findViewById(R.id.txtMemberDiscount);
                        textView3.setText(discountString);

                        Log.d("28FebV1", "mid ==> " + midString);
                        Log.d("15MarV2", "total ==> " + total);
                        Log.d("15MarV2", "discount ==> " + discountp + "%");
                        Log.d("15MarV2", "discount ==> " + discountString);



                    } // for

                } catch (Exception e) {
                    e.printStackTrace();
//            Log.d("20FebV2", "e at createDetail ==> " + e.toString());
                }

            }

            TextView textView = getView().findViewById(R.id.txtTotal);
            textView.setText(Integer.toString(total));


        } catch (Exception e) {
            //e.printStackTrace();
            Log.d(tag, "e at createDetail ==> " + e.toString());
        }

    }

    private void getOID() {
        idBillString = getArguments().getString("idBill");
        timeString = getArguments().getString("Time");
        cnumString = getArguments().getString("cnum");
        typeString = getArguments().getString("type");
        nameString = getArguments().getString("name");
        zoneString = getArguments().getString("Zone");
        deskString = getArguments().getString("Desk");
        tidString = getArguments().getString("tid");
        midString = getArguments().getString("mid");
        Log.d(tag, "idBill ==> " + idBillString);

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("BillDetail", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("idBill", idBillString);
        editor.putString("Time", timeString);
        editor.putString("cnum", cnumString);
        editor.putString("type", typeString);
        editor.putString("name", nameString);
        editor.putString("Zone", zoneString);
        editor.putString("Desk", deskString);
        editor.putString("tid", tidString);
        editor.putString("mid", midString); // เตรียม Database เพิ่ม
        editor.commit();
    }


    private void createCommunicationPrinter() {
        MyConstant myConstant = new MyConstant();
        wifiCommunication = new WifiCommunication(handler);
        wifiCommunication.initSocket(myConstant.getIpAddressPrinter(), myConstant.getPortPrinter());
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String tag = "12decV2";
            switch (msg.what) {

                case WifiCommunication.WFPRINTER_CONNECTED:

                    buttonBoolean = false;

                    Log.d(tag, "Success Connected Printer");
                    button.setText("ชำระเงิน");

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            ShowAlert Payment
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setTitle("Choose Payment");

                            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
                            final View view = layoutInflater.inflate(R.layout.alertdialog_payment, null);
                            alertDialogBuilder.setView(view);

                            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    CheckBox cashCheckBox = view.findViewById(R.id.chbCash);
                                    CheckBox creditCheckBox = view.findViewById(R.id.chbCredit);
                                    CheckBox couponCheckBox = view.findViewById(R.id.chbCoupon);

                                    EditText cashEditText = view.findViewById(R.id.edtMoneyCash);
                                    String moneyCashString = cashEditText.getText().toString().trim();

                                    EditText creditEditText = view.findViewById(R.id.edtCredit);
                                    String moneyCreditString = creditEditText.getText().toString().trim();

                                    EditText couponEditText = view.findViewById(R.id.edtCoupon);
                                    String moneyCouponString = couponEditText.getText().toString().trim();


                                    EditText discountEditText = view.findViewById(R.id.edtDiscount);
                                    String discountString = discountEditText.getText().toString().trim();

                                    uploadToServer(cashCheckBox.isChecked(), creditCheckBox.isChecked(), couponCheckBox.isChecked(),
                                            moneyCashString, moneyCreditString, moneyCouponString, discountString);


                                    if (communicationABoolean) {

                                        anInt += 1;

                                        String printSring = "Print";
                                        String line2String = "OK";
                                        printSring = printSring + " " + Integer.toString(anInt);
                                        Log.d("12decV1", "You Click Payment: " + printSring);


                                        wifiCommunication.sndByte(Command.openCashDrawer);

//                                wifiCommunication.sndByte(Command.top);
                                        wifiCommunication.sndByte(Command.dbold);
                                        wifiCommunication.sndByte(Command.centered);
                                        wifiCommunication.sendMsg("Brainwake", "tis-620");
                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sendMsg("Matichon Academy", "tis-620");
                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sendMsg("02 003 4511", "tis-620");
                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sndByte(Command.dfont);
                                        wifiCommunication.sendMsg("-------------------------", "tis-620");
                                        wifiCommunication.sndByte(Command.lineup);

                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sendMsg("   REG  01", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightLongWord(nameString + "  "), "tis-620");
                                        wifiCommunication.sndByte(Command.lineup);

                                        wifiCommunication.sendMsg("   " + "24/12/2018", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightLongWord(timeString + "  "), "tis-620");
                                        wifiCommunication.sndByte(Command.lineup);

                                        wifiCommunication.sendMsg("  Table No. " + deskString + "  " + zoneString, "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightWord(cnumString + "CT "), "tis-620");
//                                wifiCommunication.sendMsg("CT", "tis-620");
                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sndByte(Command.lineup);

//                                Work Here

                                        Log.d("12decV1", "numArray ==> " + numStringArrayList.toString());
                                        Log.d("12decV1", "nameArray ==> " + nameStringArrayList.toString());
                                        Log.d("12decV1", "priceArray ==> " + priceStringArrayList.toString());


                                        for (int i = 0; i < nameStringArrayList.size(); i += 1) {

                                            wifiCommunication.sndByte(Command.left);
//                                    wifiCommunication.sendMsg(Integer.toString(i + 1) + " x ", "tis-620");
                                            wifiCommunication.sendMsg("   " + numStringArrayList.get(i) + " x ", "tis-620");
                                            wifiCommunication.sndByte(Command.tab);

                                            wifiCommunication.sendMsg(shortFood(nameStringArrayList.get(i)), "tis-620");
                                            wifiCommunication.sndByte(Command.tab);

//                                    wifiCommunication.sendMsg("80", "tis-620");
                                            wifiCommunication.sndByte(Command.tab);


                                            wifiCommunication.sendMsg(rightWord(priceStringArrayList.get(i)), "tis-620");
                                            wifiCommunication.sndByte(Command.lineup);


                                        }

                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sendMsg("   SUB TOTAL", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightWord(Integer.toString(total)), "tis-620");

                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sendMsg("   Discount", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightWord(Integer.toString(total)), "tis-620");

                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sendMsg("   Vat 7%", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightWord(Integer.toString(total)), "tis-620");

                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sendMsg("   Service Charge 10%", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightWord(Integer.toString(total)), "tis-620");


                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sndByte(Command.bold);
                                        wifiCommunication.sendMsg("   ", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.bold);
                                        wifiCommunication.sendMsg("TOTAL", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.dbold);
                                        wifiCommunication.sendMsg(rightWord(Integer.toString(total)), "tis-620");

                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sndByte(Command.dfont);
                                        wifiCommunication.sendMsg("   CASH", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightWord(Integer.toString(total)), "tis-620");

                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sendMsg("   Change", "tis-620");
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sndByte(Command.tab);
                                        wifiCommunication.sendMsg(rightWord(Integer.toString(total)), "tis-620");

                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sndByte(Command.lineup);
                                        wifiCommunication.sndByte(Command.dbold);
                                        wifiCommunication.sndByte(Command.centered);
                                        wifiCommunication.sendMsg("THANK YOU", "tis-620");
                                        wifiCommunication.sndByte(Command.lineup);

                                        wifiCommunication.sndByte(Command.cutterPaper);


                                        wifiCommunication.close();

                                        communicationABoolean = false;


                                    } else {

                                        //Log.d("24novV3", "Communication Disible");
                                        Toast.makeText(getActivity(), "Disable Printer Please Press Click Again", Toast.LENGTH_SHORT).show();
                                    }

                                    dialog.dismiss();
                                }
                            });
                            alertDialogBuilder.show();


                        } // onClick
                    });

                    break;
                case WifiCommunication.WFPRINTER_DISCONNECTED:
                    Log.d(tag, "Disconnected Printer");
                    break;
                default:
                    break;

            } // switch

        } // handleMessage
    };

    private void uploadToServer(boolean cashBool,
                                boolean creditBool,
                                boolean couponBool,
                                String moneyCashString,
                                String moneyCreditString,
                                String moneyCouponString,
                                final String discountString) {

        String tag = "3janV1";
        final String statusCash = changeBoolToString(cashBool);
        String statusCredit = changeBoolToString(creditBool);
        String statusCoupon = changeBoolToString(couponBool);

        Log.d(tag, "statusCash " + statusCash);
        Log.d(tag, "Cash = " + moneyCashString);
        Log.d(tag, "statusCredit " + statusCredit);
        Log.d(tag, "Credit = " + moneyCreditString);
        Log.d(tag, "statusCoupon " + statusCoupon);
        Log.d(tag, "Coupon = " + moneyCouponString);
        Log.d(tag, "Discount = " + discountString);

        Log.d(tag, "SumTotal ==> " + total);

        int payFromCustomer = 0;
        int payBackCustomer = 0;

        if (couponBool) {
            payFromCustomer = payFromCustomer + Integer.parseInt(moneyCouponString);
        }

        if (creditBool) {
            payFromCustomer = payFromCustomer + Integer.parseInt(moneyCreditString);
        }

        if (cashBool) {
            payFromCustomer = payFromCustomer + Integer.parseInt(moneyCashString);
        }

        Log.d(tag, "PayFromCustomer ==> " + payFromCustomer);
        payBackCustomer = payFromCustomer - total;
        Log.d(tag, "Payback ==> " + payBackCustomer);


        try {

//                    Upload Data to Server
            String discount = discountString;

            String mid;
            if (midString != null && !midString.isEmpty() && !midString.equals("null")) {
                mid = midString;
            } else {
                mid = "0";
            }

            String oid = idBillString;
            String tid = tidString;
            String user = nameString;
            String payment = statusCash; // ส่งไปเป็น 0,1 ต้องไปปรับ Database

            PaybackThread paybackThread = new PaybackThread(getActivity());
            paybackThread.execute(oid, tid, user, payment, mid, discount, myConstant.getUrlPaymentOrder());

            String result = paybackThread.get();


//            getActivity().finish();
            Intent intent = new Intent(getActivity(), ServiceActivity.class);
            getActivity().finish();
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String changeBoolToString(boolean statusBool) {

        if (statusBool) {
            return "1";
        } else {
            return "0";
        }


    }

    private String rightLongWord(String rightLongString) {

        int currentWord = rightLongString.length();
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < (15 - currentWord); i += 1) {
            stringBuilder.append(" ");
        }

        result = stringBuilder.toString() + rightLongString;

        return result;

    }


    private String shortTotal() {

        String s = "รวมทั้งสิ้น ";
        String s1 = " บาท";
        String result = s + Integer.toString(total) + s1;

        return result;
    }

    private String rightWord(String rightString) {

        int currentWord = rightString.length();
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < (5 - currentWord); i += 1) {
            stringBuilder.append(" ");
        }

        result = stringBuilder.toString() + rightString;

        return result;
    }

    private String shortFood(String foodString) {

        String result = foodString;

        if (result.length() >= 23) {
            result = result.substring(0, 20) + "...";
        } else {

            int currentWord = result.length();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < (23 - currentWord); i += 1) {
                stringBuilder.append(" ");
            }


            result = result + stringBuilder.toString();

        }

        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_detail, container, false);
    }

}