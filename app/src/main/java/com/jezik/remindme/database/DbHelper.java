package com.jezik.remindme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jezik.remindme.R;
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
            TableEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TableEntry.HEADER + " TEXT," +
            TableEntry.CONTENT + " TEXT," +
            TableEntry.DATE + " TEXT," +
            TableEntry.FLAG + " TEXT," +
            TableEntry.DONE + " INTEGER NOT NULL" +
            ")";

    // Delete table statement
    private static final String DELETE_TABLE = "DROP TABLE IF EXIST " + TableEntry.REMIND_TABLE;

    // Get all reminders statement
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
            cv.put(TableEntry.DONE, reminderData.getIs_done());

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
                            cursor.getString(cursor.getColumnIndex(TableEntry.FLAG)),
                            cursor.getInt(cursor.getColumnIndex(TableEntry.DONE)));

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
            db.close();
        }

        return list;
    }

    //Get data for Ideas tab
    public List<ReminderData> getIdeasReminders(Context context) {
        List<ReminderData> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TableEntry.REMIND_TABLE,
                null,
                TableEntry.FLAG + " = ? and " + TableEntry.DONE + " = 0",
                new String[] {context.getString(R.string.db_ideas)},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                do {
                    ReminderData reminderData = new ReminderData(cursor.getString(cursor.getColumnIndex(TableEntry.HEADER)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.CONTENT)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.DATE)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.FLAG)),
                            cursor.getInt(cursor.getColumnIndex(TableEntry.DONE)));

                    list.add(reminderData);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while getting Ideas data");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }

        return list;
    }

    //Get data for To do tab
    public List<ReminderData> getTodoReminders(Context context) {
        List<ReminderData> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TableEntry.REMIND_TABLE,
                null,
                TableEntry.FLAG + " = ? and " + TableEntry.DONE + " = 0",
                new String[] {context.getString(R.string.db_todo)},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                do {
                    ReminderData reminderData = new ReminderData(cursor.getString(cursor.getColumnIndex(TableEntry.HEADER)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.CONTENT)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.DATE)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.FLAG)),
                            cursor.getInt(cursor.getColumnIndex(TableEntry.DONE)));

                    list.add(reminderData);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e){
            e.printStackTrace();
            Log.d(TAG, "Error while getting Todo data");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }

        return list;
    }

    //Get data for Birthdays tab
    public List<ReminderData> getBirthdaysReminders(Context context) {
        List<ReminderData> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TableEntry.REMIND_TABLE,
                null,
                TableEntry.FLAG + " = ? and " + TableEntry.DONE + " = 0",
                new String[]{context.getString(R.string.db_birthdays)},
                null,
                null,
                null
        );

        try {
            if (cursor.moveToFirst()) {
                do {
                    ReminderData reminderData = new ReminderData(cursor.getString(cursor.getColumnIndex(TableEntry.HEADER)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.CONTENT)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.DATE)),
                            cursor.getString(cursor.getColumnIndex(TableEntry.FLAG)),
                            cursor.getInt(cursor.getColumnIndex(TableEntry.DONE)));

                    list.add(reminderData);
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while getting birthdays data");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
        }

        return list;
    }

    //Delete single row
    public void deleteReminder(String header, String content, String flag) {

        SQLiteDatabase db = getWritableDatabase();

        try {
            db.delete(TableEntry.REMIND_TABLE,
                    TableEntry.HEADER + " = ? and " + TableEntry.CONTENT + " = ? and " + TableEntry.FLAG + " = ?",
                    new String[] {header, content, flag});

        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while deleting the row");
        } finally {
            db.close();
        }
    }

    //Check reminder as DONE
    public void doneReminder(String header, String content, String flag) {

        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues cv = new ContentValues();
            cv.put(TableEntry.DONE, 1);

            db.update(TableEntry.REMIND_TABLE,
                    cv,
                    TableEntry.HEADER + " = ? and " + TableEntry.CONTENT + " = ? and " + TableEntry.FLAG + " = ?",
                    new String[] {header, content, flag});
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while checking reminder as done");
        } finally {
            db.close();
        }
    }

    //Update reminder
    public void updateReminder(String header, String content, String flag, ReminderData data) {

        SQLiteDatabase db = getWritableDatabase();

        try {
            ContentValues newValues = new ContentValues();
            newValues.put(TableEntry.HEADER, data.getHeader());
            newValues.put(TableEntry.CONTENT, data.getContent());
            newValues.put(TableEntry.DATE, data.getDate());
            newValues.put(TableEntry.FLAG, data.getFlag());
            newValues.put(TableEntry.DONE, data.getIs_done());

            db.update(TableEntry.REMIND_TABLE,
                    newValues,
                    TableEntry.HEADER + " = ? and " + TableEntry.CONTENT + " = ? and " + TableEntry.FLAG + " = ?",
                    new String[] {header, content, flag});
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(TAG, "Error while updating the row.");
        } finally {
            db.close();
        }
    }
}
