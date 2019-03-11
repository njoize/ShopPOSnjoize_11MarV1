package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class MyAlertDialog {

    private Context context;

    public MyAlertDialog(Context context) {
        this.context = context;
    }

    public void normalDialog(String titleString, String messageSring){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_alert);
        builder.setTitle(titleString);
        builder.setMessage(messageSring);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();


    }


} // Main Class