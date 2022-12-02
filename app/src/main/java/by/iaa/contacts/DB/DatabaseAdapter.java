package by.iaa.contacts.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.iaa.contacts.Model.Contact;

public class DatabaseAdapter implements IRepository<Contact> {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    @Override
    public List<Contact> getAllByType(int type) {
        ArrayList<Contact> events = new ArrayList<>();
        Cursor cursor = type == 0? getAllEntries() : getAllEntriesByType(type);
        while (cursor.moveToNext()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR))));
            events.add(new Contact(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)),
                    calendar,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATH_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTPHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SECONDPHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TELEGRAMLINK)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOCIALLINK))
            ));
        }
        return events;
    }

    @SuppressLint("Range")
    public List<Contact> getAllByName(String search) {
        ArrayList<Contact> events = new ArrayList<>();
        Cursor cursor = getAllEntriesByName(search);
        while (cursor.moveToNext()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR))));
            events.add(new Contact(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)),
                    calendar,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATH_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTPHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SECONDPHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TELEGRAMLINK)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOCIALLINK))
            ));
        }
        return events;
    }

    @Override
    public void close() {
        db.close();
    }

    private Cursor getAllEntries() {
        String[] columns = new String[]{
                dbHelper.COLUMN_ID,
                dbHelper.COLUMN_NAME,
                dbHelper.COLUMN_DESCRIPTION,
                dbHelper.COLUMN_CALENDAR,
                dbHelper.COLUMN_PATH_IMAGE,
                dbHelper.COLUMN_FIRSTPHONE,
                dbHelper.COLUMN_SECONDPHONE,
                dbHelper.COLUMN_TELEGRAMLINK,
                dbHelper.COLUMN_CATEGORY,
                dbHelper.COLUMN_LOCATION,
                dbHelper.COLUMN_SOCIALLINK
        };
        return db.query(dbHelper.TABLE, columns, null, null, null, null, null);
    }

    private Cursor getAllEntriesByType(int categoryType) {
        String[] columns = new String[]{
                dbHelper.COLUMN_ID,
                dbHelper.COLUMN_NAME,
                dbHelper.COLUMN_DESCRIPTION,
                dbHelper.COLUMN_CALENDAR,
                dbHelper.COLUMN_PATH_IMAGE,
                dbHelper.COLUMN_FIRSTPHONE,
                dbHelper.COLUMN_SECONDPHONE,
                dbHelper.COLUMN_TELEGRAMLINK,
                dbHelper.COLUMN_CATEGORY,
                dbHelper.COLUMN_LOCATION,
                dbHelper.COLUMN_SOCIALLINK
        };

        String selection =  dbHelper.COLUMN_CATEGORY + " =? ";
        Integer type = categoryType-1;
        String[] selectionArgs = {type.toString()};

        return db.query(dbHelper.TABLE, columns, selection, selectionArgs, null, null, null);
    }

    private Cursor getAllEntriesByName(String search) {
        String[] columns = new String[]{
                dbHelper.COLUMN_ID,
                dbHelper.COLUMN_NAME,
                dbHelper.COLUMN_DESCRIPTION,
                dbHelper.COLUMN_CALENDAR,
                dbHelper.COLUMN_PATH_IMAGE,
                dbHelper.COLUMN_FIRSTPHONE,
                dbHelper.COLUMN_SECONDPHONE,
                dbHelper.COLUMN_TELEGRAMLINK,
                dbHelper.COLUMN_CATEGORY,
                dbHelper.COLUMN_LOCATION,
                dbHelper.COLUMN_SOCIALLINK
        };

        String selection =  dbHelper.COLUMN_NAME + "  LIKE ? ";
        String[] selectionArgs = {"%" + search + "%" };

        return db.query(dbHelper.TABLE, columns, selection, selectionArgs, null, null, null);
    }

    @SuppressLint("Range")
    @Override
    public List<Contact> getAll() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Cursor cursor = getAllEntries();
        while (cursor.moveToNext()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR))));
            contacts.add(new Contact(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)),
                    calendar,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATH_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTPHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SECONDPHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TELEGRAMLINK)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOCIALLINK))
            ));
        }
        return contacts;
    }

    @Override
    public long getCount() {
        return DatabaseUtils.queryNumEntries(db, DatabaseHelper.TABLE);
    }

    @SuppressLint("Range")
    @Override
    public Contact get(long id) {
        Contact contact = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CALENDAR))));
            contact = new Contact(
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DESCRIPTION)),
                    calendar,
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PATH_IMAGE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FIRSTPHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SECONDPHONE)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TELEGRAMLINK)),
                    cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SOCIALLINK)),
                    cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_LOCATION))
            );
        }
        cursor.close();
        return contact;
    }

    @Override
    public long insert(Contact contact) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, contact.name);
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, contact.description);
        cv.put(DatabaseHelper.COLUMN_CALENDAR, contact.calendar.getTimeInMillis() + "");
        cv.put(DatabaseHelper.COLUMN_PATH_IMAGE, contact.pathImages);
        cv.put(DatabaseHelper.COLUMN_FIRSTPHONE, contact.firstPhone);
        cv.put(DatabaseHelper.COLUMN_SECONDPHONE, contact.secondPhone);
        cv.put(DatabaseHelper.COLUMN_TELEGRAMLINK, contact.telegramLink);
        cv.put(DatabaseHelper.COLUMN_CATEGORY, contact.category.getValue());
        cv.put(DatabaseHelper.COLUMN_LOCATION, contact.organization);
        cv.put(DatabaseHelper.COLUMN_SOCIALLINK, contact.socialLink);

        return db.insert(DatabaseHelper.TABLE,null, cv);
    }

    @Override
    public long update(Contact contact) {
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + contact.id;
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, contact.name);
        cv.put(DatabaseHelper.COLUMN_DESCRIPTION, contact.description);
        cv.put(DatabaseHelper.COLUMN_CALENDAR, contact.calendar.getTimeInMillis() + "");
        cv.put(DatabaseHelper.COLUMN_PATH_IMAGE, contact.pathImages);
        cv.put(DatabaseHelper.COLUMN_FIRSTPHONE, contact.firstPhone);
        cv.put(DatabaseHelper.COLUMN_SECONDPHONE, contact.secondPhone);
        cv.put(DatabaseHelper.COLUMN_TELEGRAMLINK, contact.telegramLink);
        cv.put(DatabaseHelper.COLUMN_CATEGORY, contact.category.getValue());
        cv.put(DatabaseHelper.COLUMN_LOCATION, contact.organization);
        cv.put(DatabaseHelper.COLUMN_SOCIALLINK, contact.socialLink);

        return db.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }

    @Override
    public long remove(long id) {
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return db.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }
}
