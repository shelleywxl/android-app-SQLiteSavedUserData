package com.example.android.sqlitesaveduserdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "User";
    public static final String TABLE_NAME = "user_table";
    public static final int DATABSE_VERSION = 1;
    public static final String COL_1 = "_id";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "SHOW";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " TEXT, "
                + COL_3 + " TEXT, "
                + COL_4 + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    // for adding data in the table
    //public boolean dbAddData(String name, String email, String show) {
    //    SQLiteDatabase db = this.getWritableDatabase();
    public boolean dbAddData(String name, String email, String show) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(COL_2, name);
        userValues.put(COL_3, email);
        userValues.put(COL_4, show);
        long result = db.insert(TABLE_NAME, null, userValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    // for viewing data
    public Cursor dbViewData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return cursor;
    }


    // for updating data
    public boolean dbUpdateData(String id, String name, String email, String show) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();
        userValues.put(COL_2, name);
        userValues.put(COL_3, email);
        userValues.put(COL_4, show);
        db.update(TABLE_NAME, userValues, "_id = ?", new String[] {id});
        return true;
    }


    // for deleting data
    public Integer dbDeleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "_id = ?", new String[] {id});
    }
}
