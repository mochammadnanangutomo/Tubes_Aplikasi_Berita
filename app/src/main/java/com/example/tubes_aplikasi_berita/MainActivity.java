package com.example.tubes_aplikasi_berita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tubes_aplikasi_berita.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private CustomListAdapter adapter_off;
    private MyDatabase db;
    private List<Berita> ListKamar = new ArrayList<Berita>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MyDatabase(this);

        adapter_off = new CustomListAdapter(this, ListKamar);
        mListView = (ListView) findViewById(R.id.list_news);
        mListView.setAdapter(adapter_off);
        mListView.setOnItemClickListener(this);
        mListView.setClickable(true);
        ListKamar.clear();

        List<Berita> contacts = db.ReadKamar();
        for (Berita cn : contacts) {
            Berita judulModel = new Berita();
            judulModel.set_id(cn.get_id());
            judulModel.set_image(cn.get_image());
            judulModel.set_judul(cn.get_judul());
            judulModel.set_isi(cn.get_isi());
            judulModel.set_waktu(cn.get_waktu());
            judulModel.set_kronologi(cn.get_kronologi());
            ListKamar.add(judulModel);

            if ((ListKamar.isEmpty()))
                Toast.makeText(MainActivity.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
            else {
            }
        }
        final int orientation = getResources().getConfiguration().orientation;
        int kolom;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            kolom = 2;
        } else {
            kolom = 4;
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Object o = mListView.getItemAtPosition(i);
        Berita obj_itemDetails = (Berita) o;

        String Sid = obj_itemDetails.get_id();
        byte[] Simage = obj_itemDetails.get_image();
        String Sjudul = obj_itemDetails.get_judul();
        String Sisi = obj_itemDetails.get_isi();
        String Swaktu = obj_itemDetails.get_waktu();
        String Skronologi = obj_itemDetails.get_kronologi();

        Intent Detail = new Intent(MainActivity.this, MainRead.class);
        Detail.putExtra("Iid", Sid);
        Detail.putExtra("Igambar", Simage);
        Detail.putExtra("Ijudul", Sjudul);
        Detail.putExtra("Iisi", Sisi);
        Detail.putExtra("Iwaktu", Swaktu);
        Detail.putExtra("Ikronologi", Skronologi);
        startActivity(Detail);
    }
    @Override
    protected void onResume() {
        super.onResume();
        ListKamar.clear();
        mListView.setAdapter(adapter_off);

        List<Berita> contacts = db.ReadKamar();
        for (Berita cn : contacts) {
            Berita judulModel = new Berita();
            judulModel.set_id(cn.get_id());
            judulModel.set_image(cn.get_image());
            judulModel.set_judul(cn.get_judul());
            judulModel.set_isi(cn.get_isi());
            judulModel.set_waktu(cn.get_waktu());
            judulModel.set_kronologi(cn.get_kronologi());
            ListKamar.add(judulModel);

            if ((ListKamar.isEmpty()))
                Toast.makeText(MainActivity.this, "Tidak ada data", Toast.LENGTH_SHORT).show();
            else {
            }
        }
    }
}
