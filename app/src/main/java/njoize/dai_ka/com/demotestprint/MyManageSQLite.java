package njoize.dai_ka.com.demotestprint;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MyManageSQLite {

    private Context context;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;


    public MyManageSQLite(Context context) {
        this.context = context;
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();
    }

    public long addValueToSQLite(String idFoodString,
                                 String nameFoodString,
                                 String priceString,
                                 String amountString) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("idFood", idFoodString);
        contentValues.put("NameFood", nameFoodString);
        contentValues.put("Price", priceString);
        contentValues.put("Amount", amountString);
        return sqLiteDatabase.insert("orderTABLE", null, contentValues);
    }



}
