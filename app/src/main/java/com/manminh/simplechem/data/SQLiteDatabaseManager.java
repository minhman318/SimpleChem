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
    private static final String EQUATION_BEFORE = "before_chem"; // Ex: 2 H2 1 O2
    private static final String EQUATION_AFTER = "after_chem"; // Ex: 2 H2O

    public SQLiteDatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "create table " +
                EQUATION_TABLE + " (" +
                EQUATION_ID + " integer primary key autoincrement, " +
                EQUATION_BEFORE + " text," +
                EQUATION_AFTER + " text)";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EQUATION_TABLE);
        onCreate(db);
    }

    public void insert(SimpleEquation equation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EQUATION_BEFORE, equation.getBeforeDataString());
        values.put(EQUATION_AFTER, equation.getAfterDataString());
        db.insert(EQUATION_TABLE, null, values);
    }

    public boolean balance(SimpleEquation key) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + EQUATION_TABLE, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            String bStr = res.getString(res.getColumnIndex(EQUATION_BEFORE));
            String aStr = res.getString(res.getColumnIndex(EQUATION_AFTER));
            SimpleEquation eq = new SimpleEquation(bStr, aStr);
            if (key.compareAndCopyFactor(eq)) {
                res.close();
                db.close();
                return true;
            }
            res.moveToNext();
        }
        res.close();
        db.close();
        return false;
    }
}
