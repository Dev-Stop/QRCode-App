package com.example.saif.qrcodeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static android.media.tv.TvContract.AUTHORITY;



import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;


public class fields_activity extends AppCompatActivity {


    String editText;
    String editText2;
    String editText3;
    String editText4;
    FloatingActionButton button;
    FloatingActionButton button2;
    FloatingActionButton button3;
    String result;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fields_activity);

        button = (FloatingActionButton) findViewById(R.id.button1);
        button2 = (FloatingActionButton) findViewById(R.id.button3);
        button3 = (FloatingActionButton) findViewById(R.id.button2);

        button3.setVisibility(View.INVISIBLE);

        imageView = (ImageView) findViewById(R.id.image);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button3.setVisibility(View.VISIBLE);


                EditText e1 = (EditText) findViewById(R.id.editText);
                EditText e2 = (EditText) findViewById(R.id.editText2);
                EditText e3 = (EditText) findViewById(R.id.editText3);
                EditText e4 = (EditText) findViewById(R.id.editText4);

                String editText = e1.getText().toString();
                String editText2 = e2.getText().toString();
                String editText3 = e3.getText().toString();
                String editText4 = e4.getText().toString();

                result = " " + editText + "|" + editText2 + "|" + editText3 + "|" + editText4;
                // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {

                    BitMatrix byteMatrix = multiFormatWriter.encode(result, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(byteMatrix);
                    imageView.setImageBitmap(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText e1 = (EditText) findViewById(R.id.editText);
                EditText e2 = (EditText) findViewById(R.id.editText2);
                EditText e3 = (EditText) findViewById(R.id.editText3);
                EditText e4 = (EditText) findViewById(R.id.editText4);

                e1.setText("");
                e2.setText("");
                e3.setText("");
                e4.setText("");

            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/BarCode");
                path.mkdirs();
                File imageFile = new File(path, "barcode_saved.png"); //
                Uri imageURI = Uri.parse(imageFile.getAbsolutePath());
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, imageURI);
                intent.setType("image/*");
                startActivity(Intent.createChooser(intent, "Share image via"));


            }
        });

    }




    private void checkWritingPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(fields_activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }

    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, String permissions[], int[] grantResults){
        switch (requestCode) {
            case 200: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission Not Granted", Toast.LENGTH_SHORT).show();
                    // saveBut.setEnabled(false);
                    return;
                }
                return;
            }

        }


    }

    public void onBackPressed(){

        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();


    }
}