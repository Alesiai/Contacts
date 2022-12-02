package by.iaa.contacts.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "contactsDB";
    public final static int SCHEMA = 1;
    final static String TABLE = "contacts";

    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_DESCRIPTION = "description";
    public final static String COLUMN_CALENDAR = "calendar";
    public final static String COLUMN_PATH_IMAGE = "path_image";

    public final static String COLUMN_FIRSTPHONE = "firstPhone";
    public final static String COLUMN_SECONDPHONE = "secondPhone";
    public final static String COLUMN_TELEGRAMLINK = "telegramLink";
    public final static String COLUMN_CATEGORY = "category";
    public final static String COLUMN_SOCIALLINK = "socialLink";
    public final static String COLUMN_LOCATION = "organization";

    public final static String createTable = "CREATE TABLE " + TABLE + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT, "
            + COLUMN_FIRSTPHONE + " TEXT, "
            + COLUMN_SECONDPHONE+ " TEXT, "
            + COLUMN_TELEGRAMLINK + " TEXT, "
            + COLUMN_CALENDAR + " TEXT,"
            + COLUMN_SOCIALLINK + " TEXT, "
            + COLUMN_LOCATION + " TEXT, "
            + COLUMN_PATH_IMAGE + " TEXT,"
            + COLUMN_CATEGORY + " INTEGER, "
            + COLUMN_DESCRIPTION + " TEXT);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
