package com.example.rkuworkshopactivitiy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, MyUtil.DB_NAME, null, MyUtil.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String sqlUserCreate = "create table if not exists " + MyUtil.TBL_USER + " (" +
                MyUtil.USER_COL_ID + " integer primary key autoincrement," +
                MyUtil.USER_COL_FNAME + " text," +
                MyUtil.USER_COL_LNAME + " text," +
                MyUtil.USER_COL_USERNAME + " text," +
                MyUtil.USER_COL_PASSWORD + " text)";
        db.execSQL(sqlUserCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + MyUtil.TBL_USER);
        onCreate(db);
    }

}
