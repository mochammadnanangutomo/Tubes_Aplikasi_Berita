package com.example.tubes_aplikasi_berita;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_museum";

    private static final String tb_museum= "tb_museum";

    private static final String tb_km_id = "id";
    private static final String tb_km_image = "image";
    private static final String tb_km_nama = "nama_hotel";
    private static final String tb_km_alamat = "alamat";
    private static final String tb_km_telpon = "no_telpon";
    private static final String tb_km_deskripsi = "deskripsi";
    private static final String tb_km_koleksi = "koleksi";

    private static final String CREATE_TABLE_HOTEL = "CREATE TABLE " + tb_museum + "("
            + tb_km_id + " INTEGER PRIMARY KEY ,"
            + tb_km_image + " BLOB,"
            + tb_km_nama + " TEXT,"
            + tb_km_alamat + " TEXT,"
            + tb_km_telpon + " TEXT,"
            + tb_km_deskripsi + " TEXT, "
            + tb_km_koleksi + " TEXT"+ ")";

    public MyDatabase (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HOTEL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void CreateMuseum (Museum mdNotif) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tb_km_id, mdNotif.get_id());
        values.put(tb_km_image, mdNotif.get_image());
        values.put(tb_km_nama, mdNotif.get_nama());
        values.put(tb_km_alamat, mdNotif.get_alamat());
        values.put(tb_km_telpon, mdNotif.get_telpon());
        values.put(tb_km_deskripsi, mdNotif.get_deskripsi());
        values.put(tb_km_koleksi, mdNotif.get_koleksi());
        db.insert(tb_museum, null, values);
        db.close();
    }

    public List<Berita> ReadKamar() {
        List<Museum> judulModelList = new ArrayList<Museum>();
        String selectQuery = "SELECT  * FROM " + tb_museum;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Museum mdKontak = new Museum();
                mdKontak.set_id(cursor.getString(0));
                mdKontak.set_image(cursor.getBlob(1));
                mdKontak.set_nama(cursor.getString(2));
                mdKontak.set_alamat(cursor.getString(3));
                mdKontak.set_telpon(cursor.getString(4));
                mdKontak.set_deskripsi(cursor.getString(5));
                mdKontak.set_koleksi(cursor.getString(6));
                judulModelList.add(mdKontak);
            } while (cursor.moveToNext());
        }
        db.close();
        return judulModelList;
    }

    public int UpdateMuseum (Museum mdNotif) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(tb_km_image, mdNotif.get_image());
        values.put(tb_km_nama, mdNotif.get_nama());
        values.put(tb_km_alamat, mdNotif.get_alamat());
        values.put(tb_km_telpon, mdNotif.get_telpon());
        values.put(tb_km_deskripsi, mdNotif.get_deskripsi());
        values.put(tb_km_koleksi, mdNotif.get_koleksi());

        return db.update(tb_museum, values, tb_km_id + " = ?",
                new String[] { String.valueOf(mdNotif.get_id())});
    }

    public void DeleteMuseum (Museum mdNotif) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_museum, tb_km_id+ " = ?",
                new String[]{String.valueOf(mdNotif.get_id())});
        db.close();
    }


    public List<Museum> search(String keyword) {
        List<Museum> contacts = null;
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + tb_museum + " where " + tb_km_nama + " like ?", new String[] { "%" + keyword + "%" });
            if (cursor.moveToFirst()) {
                contacts = new ArrayList<Museum>();
                do {
                    Museum mdKontak = new Museum();
                    mdKontak.set_id(cursor.getString(0));
                    mdKontak.set_image(cursor.getBlob(1));
                    mdKontak.set_nama(cursor.getString(2));
                    mdKontak.set_alamat(cursor.getString(3));
                    mdKontak.set_telpon(cursor.getString(4));
                    mdKontak.set_deskripsi(cursor.getString(5));
                    mdKontak.set_koleksi(cursor.getString(6));
                    contacts.add(mdKontak);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            contacts = null;
        }
        return contacts;
    }

}