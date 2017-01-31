package com.vvv.cinemavvv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by valera on 31.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "cinemavvv.db";
    public static final int VERSION = 1;
    public static final String TABLE_CINEMA = "cinema";
    public static final String C_ID = "id";
    public static final String C_IMAGE = "image";
    public static final String C_NAME = "name";
    public static final String C_NAME_ENG = "name_eng";
    public static final String C_PREMIERE = "premiere";
    public static final String C_DESCRIPTION = "description";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /*создаем таблицу куда будем сгружать наши данные для кэша
    для того что бы у нас небыло в будущем застоев с данными делаем индекс по ИД и по НАИМЕНОВАНИЯ АНГЛ
    для англ по поиску при добавлении
    для ид для всего остального(апдейт, инстер, селект, и мб удаление)
    */

    @Override
    public void onCreate(SQLiteDatabase db) {
        String USER = "CREATE TABLE " + TABLE_CINEMA + "(" +
                C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                C_NAME + " TEXT," +
                C_IMAGE + " TEXT," +
                C_NAME_ENG + " TEXT ," +
                C_PREMIERE + " TEXT," +
                C_DESCRIPTION + " TEXT" + ")";

        db.execSQL(USER);
        db.execSQL("CREATE INDEX " + TABLE_CINEMA + "_IDX" + " ON " + TABLE_CINEMA + " (" + C_ID + ")");
        db.execSQL("CREATE INDEX " + TABLE_CINEMA + "_IDX2" + " ON " + TABLE_CINEMA + " (" + C_NAME_ENG + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
    тут суть в том что на сервере нету уникально ИД и мы не можем не на что опереться. я опирался на наименование АНГЛ
    смотрим если есть такая запись с именем то делаем апдейт(вдруг описание либо другая инфа поменяласб) а если нету то делаем инсерт
     */
    public long addCinema(String image, String name, String nameEng, String premiere, String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C_IMAGE, image);
        contentValues.put(C_NAME, name);
        contentValues.put(C_NAME_ENG, nameEng);
        contentValues.put(C_PREMIERE, premiere);
        contentValues.put(C_DESCRIPTION, description);

        Cursor res = db.rawQuery("select * from " + TABLE_CINEMA + " where " + C_NAME_ENG + " = '" + nameEng + "'", null);
        if (res.getCount() > 0) {
            res.moveToNext();
            db.update(TABLE_CINEMA, contentValues, C_ID + " = " + res.getLong(0), null);
            return res.getLong(0);
        } else {
            return db.insert(TABLE_CINEMA, null, contentValues);
        }
    }

    //тут все выбираем
    public Cursor getAllCinema() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_CINEMA, null);
    }

    //тут  только нужную нам запись
    public Cursor getCinema(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_CINEMA + " where " + C_ID + " = " + id, null);
    }

}