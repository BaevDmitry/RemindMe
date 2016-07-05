package com.jezik.remindme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jezik.remindme.ReminderData;
import com.jezik.remindme.database.Contract.TableEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Дмитрий on 04.07.2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbHelper";
    private static DbHelper mDbHelper;

    // Database info
    private static final String DATABASE_NAME = "remind.db";
    private static final int DATABASE_VERSION = 1;

    // Create table statement
    private static final String CREATE_TABLE = "CREATE TABLE " + TableEntry.REMIND_TABLE +
            "(" +
            TableEntry._ID + " INTEGER PRIMARY KEY," +
            TableEntry.HEADER + " TEXT," +
            TableEntry.CONTENT + " TEXT," +
            TableEntry.DATE + " TEXT," +
            TableEntry.FLAG + " TEXT" +
            ")";

    // Delete table statement
    private static final String DELETE_TABLE = "DROP TABLE IF EXIST " + TableEntry.REMIND_TABLE;

    // Get all reminder statement
    private static final String SELECT_ALL_QUERY = "SELECT * FROM " + TableEntry.REMIND_TABLE;

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method for return new Instance of DbHelper
    public static synchronized DbHelper newInstance(Context context) {
        if (mDbHelper == null) {
            mDbHelper = new DbHelper(context.getApplicationContext());
        }
        return mDbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL(DELETE_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

    // Insert new reminderData into database
    public void insertReminderItem(ReminderData reminderData){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put(TableEntry.HEADER, reminderData.getHeader());
            cv.put(TableEntry.CONTENT, reminderData.getContent());
            cv.put(TableEntry.DATE, reminderData.getDate());
            cv.put(TableEntry.FLAG, reminderData.getFlag());

            db.insertOrThrow(TableEntry.REMIND_TABLE, null, cv);
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(TAG, "Error inserting row");
        } finally {
            db.endTransaction();
        }
    }

    // Get all rows from database
    public List<ReminderData> getAllReminders() {
        List<ReminderData> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    ReminderData reminderData = new ReminderData(cursor.getString(cursor.getColumnIndex(TableEntry.HEADER)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.CONTENT)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.DATE)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.FLAG)));

                    list.add(reminderData);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while getting all data");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return list;
    }

    // Delete single row
    public void deleteReminderItem(String header) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL("DELETE FROM " + TableEntry.REMIND_TABLE + " WHERE header ='" + header + "'");
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while deleting row");
        } finally {
            db.endTransaction();
        }

    }
}
