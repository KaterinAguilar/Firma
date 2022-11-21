package com.example.firma;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class firma extends AppCompatActivity {

    ImageView firmas;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);

        firmas = findViewById(R.id.imagen_firma);


        Bundle datos = getIntent().getExtras();
        String image = datos.getString("firma");

        firmas.setImageBitmap(ConvertirBase64firma(image));

    }

    private Bitmap ConvertirBase64firma(String Base64String)
    {

        String base64Image  = Base64String;
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}