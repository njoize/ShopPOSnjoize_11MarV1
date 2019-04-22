package njoize.dai_ka.com.demotestprint;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MasterOpenHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String database_name = "master.db";
    private static final int database_version = 1;
    private static final String database_table = "create table testTABLE (" +
            "id integer primary key, " +
            "Name text, " +
            "Surname text, " +
            "Address text, " +
            "Tel text);";

//    private static final String database_table2 = "create table testTABLE2 (" +
//            "id integer primary key, " +
//            "Name text, " +
//            "Surname text, " +
//            "Address text, " +
//            "Tel text);";

    public MasterOpenHelper(Context context) {
        super(context, database_name, null, database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(database_table);
//        db.execSQL(database_table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
