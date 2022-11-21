package com.example.firma;



import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText desc;
    Button btnLista;

    private float floatStartX = -1, floatStartY = -1,
            floatEndX = -1, floatEndY = -1;

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this
                ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.firmad);
        desc = findViewById(R.id.txtdescripcion);
        btnLista = findViewById(R.id.btnLista);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityList.class);
                startActivity(intent);
            }
        });


    }

    private void drawPaintSketchImage(){

        if (bitmap == null){
            bitmap = Bitmap.createBitmap(imageView.getWidth(),
                    imageView.getHeight(),
                    Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bitmap);
            paint.setColor(Color.BLUE);
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8);
        }
        canvas.drawLine(floatStartX,
                floatStartY-220,
                floatEndX,
                floatEndY-220,
                paint);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            floatStartX = event.getX();
            floatStartY = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE){
            floatEndX = event.getX();
            floatEndY = event.getY();
            drawPaintSketchImage();
            floatStartX = event.getX();
            floatStartY = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP){
            floatEndX = event.getX();
            floatEndY = event.getY();
            drawPaintSketchImage();
        }
        return super.onTouchEvent(event);
    }

    public void guardarFirmas(View view){

        if (desc.getText().length()>0 || bitmap != null){
            File fileSaveImage = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                    Calendar.getInstance().getTime().toString() + ".jpg");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(fileSaveImage);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                String encoded = convertiraBase64firmas();
                fileOutputStream.flush();
                fileOutputStream.close();

                guardarRegistrosf(encoded);
                Toast.makeText(this, "Datos Gurdados correctamente", Toast.LENGTH_LONG).show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "DIngrese  Firma y  descripción", Toast.LENGTH_LONG).show();
        }

    }
    public String convertiraBase64firmas(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return encoded;
    }

    private void guardarRegistrosf(String encoded) {

        conexionsql conexion = new conexionsql(this, base.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(base.descripcion,desc.getText().toString());
        values.put(base.firma,encoded);

        try {
            Long resultados = db.insert(base.TablaFirmas, base.id, values);
            Toast.makeText(getApplicationContext(), "Firma Ingresada Correctamente. "+resultados.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error al insertar. "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        bitmap = null;
        desc.setText("");
        desc.requestFocus();
        imageView.setImageResource(R.drawable.firmad);

        db.close();
    }
}