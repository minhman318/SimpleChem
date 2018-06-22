package com.manminh.simplechem.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Pair;

import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.model.SimpleEquation;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sqlite_database";

    // Equation table
    private static final String EQUATION_TABLE = "equation_table";
    private static final String EQUATION_ID = "_id";
    private static final String EQUATION_CONTENT = "content";

    public SQLiteDatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
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

    public List<String> getAllEquation() {
        List<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + EQUATION_TABLE, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            items.add(res.getString(res.getColumnIndex(EQUATION_CONTENT)));
            res.moveToNext();
        }
        res.close();
        db.close();
        return items;
    }

    public boolean balance(SimpleEquation key) {
        try {
            List<String> all = getAllEquation();
            for (String s : all) {
                SimpleEquation eq = new SimpleEquation(s);
                if (key.compareAndCopyFactor(eq)) {
                    return true;
                }
            }
            return false;
        } catch (ParseEquationException e) {
            return false;
        }
    }
}
