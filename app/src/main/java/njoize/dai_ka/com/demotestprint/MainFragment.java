package njoize.dai_ka.com.demotestprint;


import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zj.wfsdk.WifiCommunication;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

//    Explicit
    private WifiCommunication wifiCommunication;
    private boolean aBoolean = false;
    private boolean communicationABoolean = true; // true ==> Can Print, false ==> Disable Print
    private Button button, printAgainButton;
    private int anInt = 0;


    public MainFragment() {}


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Check Connected Printer
        createCommunicationPrinter();

//        Print Controller
        button = getView().findViewById(R.id.btnPrint);
        printAgainButton = getView().findViewById(R.id.btnPrintAgain);
        printAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCommunicationPrinter();
                communicationABoolean = true;
            }
        });


    } // Main Method

    private void createCommunicationPrinter() {
        wifiCommunication = new WifiCommunication(handler);
        wifiCommunication.initSocket( "192.168.1.88", 9100);
    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String tag = "12DecV1";
            switch (msg.what) {

                case WifiCommunication.WFPRINTER_CONNECTED:
                    Log.d(tag, "Success Connected Printer");
                    button.setText("Test Print");

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (communicationABoolean) {

                                anInt += 1;

                                String printSring = "ABC";
                                String line2String = "Thailand";
                                printSring = printSring + " " + Integer.toString(anInt);
                                Log.d("24novV3", "You Click Test Print" + printSring);


                                byte[] bytes = new byte[]{0x10, 0x04, 0x04}; // Space Front Bill
                                //byte[] bytes = new byte[]{0x1B, 0x4A, 40}; // Space Front Bill n*0.125 mm
                                wifiCommunication.sndByte(bytes);
                                wifiCommunication.sendMsg(printSring, "gbk");

                                byte[] bytes1 = new byte[]{0x0A, 0x0D}; // Update Line
                                wifiCommunication.sndByte(bytes1);

                                //-----Start Joyce-----


                                byte[] centered = new byte[]{0x1B, 0x61, 1}; // centered
                                wifiCommunication.sndByte(centered);
                                wifiCommunication.sendMsg("จัดกลาง centered", "tis-620");
                                wifiCommunication.sndByte(bytes1); // Update Line

                                byte[] left = new byte[]{0x1B, 0x61, 0}; // left
                                wifiCommunication.sndByte(left);
                                wifiCommunication.sendMsg("left", "gbk");
                                wifiCommunication.sndByte(bytes1); // Update Line

                                byte[] right = new byte[]{0x1B, 0x61, 2}; // right
                                wifiCommunication.sndByte(right);
                                wifiCommunication.sendMsg("right", "gbk");
                                wifiCommunication.sndByte(bytes1); // Update Line

                                byte[] cmd =  new  byte [ 3 ]; // Set the font (double height and width bold)
                                cmd[0] = 0x1B;
                                cmd[1] = 0x21;
                                cmd[2] |= 0x08; // 08 04 bold
                                cmd[2] |= 0x05; // 10 08 height
                                cmd[2] |= 0x20; // 20 10
                                wifiCommunication.sndByte(cmd); // Set the font (double height and width bold)
                                wifiCommunication.sendMsg("bold", "gbk");
                                wifiCommunication.sndByte(bytes1); // Update Line

                                byte[] dfont = new byte[]{0x1B, 0x21, 0x00}; // default font
                                wifiCommunication.sndByte(dfont);
                                wifiCommunication.sendMsg("dfont", "gbk");
                                wifiCommunication.sndByte(bytes1); // Update Line

                                //-----End Joyce-----

//                                    Line2
                                wifiCommunication.sndByte(bytes);
                                wifiCommunication.sendMsg(line2String, "gbk");
                                wifiCommunication.sndByte(bytes1);

                                //-----Start Joyce-----

                                byte[] openCashDrawer = new byte[]{0x1B, 0x70, 0x00, 0x40, 0x50}; // Open Cash Drawer
                                wifiCommunication.sndByte(openCashDrawer);

//                                byte[] cutterPaper = new byte[]{0x1D, 0x56, 0x42, 90}; // Cutter Paper command
//                                wifiCommunication.sndByte(cutterPaper);

                                //-----End Joyce-----


                                wifiCommunication.close();

                                communicationABoolean = false;

                            } else {

                                //Log.d("24novV3", "Communication Disible");
                                Toast.makeText(getActivity(), "Disable Printer Please Press Click Again", Toast.LENGTH_SHORT).show();
                            }

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
