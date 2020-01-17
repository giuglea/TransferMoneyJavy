package com.example.transferrapidjavy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSQLite extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DataModel.db";
    public static final String TABLE_NAME = "DataModel_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "SENDER";
    public static final String COL_3 = "RECEIVER";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "AMOUNT";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table DataModel (ID INTEGER PRIMARY KEY AUTOINCREMENT,SENDER TEXT,RECEIVER TEXT,DATE TEXT,AMOUNT INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DataModel");
        onCreate(db);
    }

    public DatabaseSQLite(@Nullable Context context) {
        super(context, DATABASE_NAME,null,1);
        SQLiteDatabase db = this.getReadableDatabase();
    }
}
