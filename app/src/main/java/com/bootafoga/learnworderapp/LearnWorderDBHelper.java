package com.bootafoga.learnworderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LearnWorderDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "learnworder";
    private static final int DB_VERSION = 1;

    LearnWorderDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DICTIONARY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "WORD TEXT, "
                + "TRANSLATE TEXT,"
                + "FLAG INTEGER); "
        );

        ContentValues newWord = new ContentValues();
        newWord.put("WORD", "Accidentally");
        newWord.put("TRANSLATE", "Случайно");
        newWord.put("FLAG", 0);
        db.insert("DICTIONARY", null, newWord);

        ContentValues newWord2 = new ContentValues();
        newWord2.put("WORD", "Yummy");
        newWord2.put("TRANSLATE", "Вкусный");
        newWord2.put("FLAG", 0);
        db.insert("DICTIONARY", null, newWord2);

        ContentValues newWord3 = new ContentValues();
        newWord3.put("WORD", "Wise");
        newWord3.put("TRANSLATE", "Мудрый");
        newWord3.put("FLAG", 0);
        db.insert("DICTIONARY", null, newWord3);

        ContentValues newWord4 = new ContentValues();
        newWord4.put("WORD", "Doubt");
        newWord4.put("TRANSLATE", "Сомневаться");
        newWord4.put("FLAG", 0);
        db.insert("DICTIONARY", null, newWord4);

        ContentValues newWord5 = new ContentValues();
        newWord5.put("WORD", "Lamentable");
        newWord5.put("TRANSLATE", "Плачевный");
        newWord5.put("FLAG", 0);
        db.insert("DICTIONARY", null, newWord5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}