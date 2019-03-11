package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String database_name = "shop.db";
    public static final String database_table = "orderTABLE";
    private static final int database_version = 1;
    private static final String sql_command = "CREATE TABLE orderTABLE (" +
            "id INTEGER PRIMARY KEY, " +
            "idFood TEXT," +
            "NameFood TEXT," +
            "Price TEXT," +
            "Amount TEXT);";


    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
