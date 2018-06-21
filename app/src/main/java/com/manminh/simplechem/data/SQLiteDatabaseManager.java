package com.manminh.simplechem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class SQLiteDatabaseManager extends SQLiteOpenHelper {

    // Equation table
    private static final String EQUATION_TABLE = "equation_table";
    private static final String EQUATION_ID = "_id";
    private static final String EQUATION_CONTENT = "content";

    public SQLiteDatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "create table " +
                EQUATION_TABLE + " (" +
                EQUATION_ID + " integer primary key autoincrement, " +
                EQUATION_CONTENT + " text)";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EQUATION_TABLE);
        onCreate(db);
    }

    public void insert(String equation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EQUATION_CONTENT, equation);
        db.insert(EQUATION_TABLE, null, values);
    }
}
