package com.example.visioneh.englishhelper.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by visionEH on 2017/9/17.
 */

public class WordDbHelper extends SQLiteOpenHelper {

    public WordDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists Word(" +
                "id integer primary key autoincrement," +
                "name varchar(20)," +
                "tran varchar(50)," +
                "date varchar(15))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Word");
        onCreate(db);
    }
}
