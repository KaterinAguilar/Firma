package com.example.firma;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;



public class ActivityList extends AppCompatActivity
{
    private static CustomAdapter adaptercustomer;
    conexionsql conexion;
    ArrayList<signaturess> listaver;
    ListView listafirmas;
    List<signaturess> Listfirma;
    ArrayList<String> firmaLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new conexionsql(this, base.NameDatabase, null, 1);
        listafirmas = (ListView) findViewById(R.id.ListFirmas);
        Listfirma = new ArrayList<>();

        firmaLista = new ArrayList<String>();
        ObListaFirmas();

        listafirmas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String id = listaver.get(i).getFirma();
                Intent intent = new Intent(ActivityList.this, firma.class);
                intent.putExtra("firma",listaver.get(i).getFirma());
                startActivity(intent);

            }
        });

    }

    private void ObListaFirmas() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        signaturess listFirmatotal;

        listaver = new ArrayList<>();
        Cursor cursor = db.rawQuery( base.GetFirmas ,null);

        while (cursor.moveToNext()){
            listFirmatotal = new signaturess();
            listFirmatotal.setId(cursor.getInt(0));
            listFirmatotal.setDescripcion(cursor.getString(1));
            listFirmatotal.setFirma(cursor.getString(2));

            listaver.add(listFirmatotal);
        }
        cursor.close();

        adaptercustomer = new CustomAdapter(listaver, getApplicationContext());
        listafirmas.setAdapter(adaptercustomer);
    }

}