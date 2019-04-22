package njoize.dai_ka.com.demotestprint;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class MasterManager {

    private Context context;
    private MasterOpenHelper masterOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MasterManager(Context context) {
        this.context = context;
        masterOpenHelper = new MasterOpenHelper(context);
        sqLiteDatabase = masterOpenHelper.getWritableDatabase();
    }

    public long addDataToMaster(String name, String surname, String address, String tel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Surname", surname);
        contentValues.put("Address", address);
        contentValues.put("Tel", tel);
        return sqLiteDatabase.insert("testTABLE", null, contentValues);
    }

}
