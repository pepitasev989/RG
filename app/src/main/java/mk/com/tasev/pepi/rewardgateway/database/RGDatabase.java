package mk.com.tasev.pepi.rewardgateway.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ptasev on 3/12/18.
 */

public class RGDatabase extends SQLiteOpenHelper {
    private static final String rGDatabaseName = "RGDatabase";
    protected static int DATABASE_VERSION = 1;

    protected static final String TABLE_EMPLOYEES = "tableEmployees";

    protected static final String COLUMN_UUID = "uuid";
    protected static final String COLUMN_COMPANY = "company";
    protected static final String COLUMN_BIO = "bio";
    protected static final String COLUMN_NAME = "name";
    protected static final String COLUMN_TITLE = "title";
    protected static final String COLUMN_AVATAR = "avatar";

    protected static final int INDEX_COLUMN_UUID = 0;
    protected static final int INDEX_COLUMN_COMPANY = INDEX_COLUMN_UUID + 1;
    protected static final int INDEX_COLUMN_BIO = INDEX_COLUMN_COMPANY + 1;
    protected static final int INDEX_COLUMN_NAME = INDEX_COLUMN_BIO + 1;
    protected static final int INDEX_COLUMN_TITLE = INDEX_COLUMN_NAME + 1;
    protected static final int INDEX_COLUMN_AVATAR = INDEX_COLUMN_TITLE + 1;

    protected static final String CREATE_TABLE_EMPLOYEES = "CREATE TABLE " + TABLE_EMPLOYEES + " (" + COLUMN_UUID + " TEXT PRIMARY KEY, " + COLUMN_COMPANY + " TEXT, " + COLUMN_BIO + " TEXT, " + COLUMN_NAME + " TEXT, " + COLUMN_TITLE + " TEXT, " + COLUMN_AVATAR + " TEXT)";

    protected static final String DROP_TABLE_EMPLOYEES = "DROP TABLE IF EXISTS " + TABLE_EMPLOYEES;

    public RGDatabase(Context context, int version) {
        super(context, rGDatabaseName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EMPLOYEES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_EMPLOYEES);
        onCreate(db);
    }
}
