package com.example.visioneh.englishhelper.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.visioneh.englishhelper.bean.Word;

/**
 * Created by visionEH on 2017/9/23.
 */

public class WordUtil  {
    private static SQLiteDatabase db;
    public static void Instance(Context context){
         WordDbHelper wordDbHelper=new WordDbHelper(context,"word.db",null,1);
    }
    public static Cursor SelectAll(){
        Cursor cursor=db.rawQuery("select *" +
                " from Word",null);
        Close();
        return cursor;
    }
    public static void Insert(Word word){
        db.execSQL("insert into Word values(NULL,?,?,?)",new Object[]{word.getName(),word.getTranslation(),word.getDate()});
        Close();
    }
    public static void Delete(String name){
        db.execSQL("delete from Word where name=?",new String[]{name});
        Close();
    }
    private static void Close(){
        db.close();
    }

}
