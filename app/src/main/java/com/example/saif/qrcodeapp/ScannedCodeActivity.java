package com.example.saif.qrcodeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.text.DateFormat;
import java.util.Date;

public class ScannedCodeActivity extends AppCompatActivity {

    Button saveBtn,CancelBtn;
    ImageView scannedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_scanned_code);

            scannedImage = (ImageView) findViewById(R.id.scannedImageView);

            Bitmap bitmap = null;

            bitmap = Main2Activity.qrCodeEncoder.encodeAsBitmap();

            scannedImage.setDrawingCacheEnabled(true);
            scannedImage.setImageBitmap(bitmap);

            saveBtn = (Button) findViewById(R.id.Savebutton);
            CancelBtn = (Button) findViewById(R.id.Cancelbutton);


            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] splitVals = Main2Activity.ScannedCode.split("\\|");

                    int index = 0;
                    MetalPart part = new MetalPart();
                    for (String val : splitVals) {
                        if(splitVals.length != 6){
                            Toast.makeText(getApplicationContext(), "Invalid QR", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        else if (index == 0) {
                            part.setTagNo(val);
                        } else if (index == 1) {
                            part.setPartNo(val);
                        } else if (index == 2) {
                            part.setLocationCode(val);
                        } else if (index == 3) {
                            part.setPcs(val);
                        } else if (index == 4) {
                            part.setGrossWeight(val);
                        } else if (index == 5) {
                            part.setDescription(val);
                        }

                        index++;
                    }


                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    part.setDateTagged(currentDateTimeString);
                    Main2Activity.partsList.add(part);

                    Intent intent = new Intent(ScannedCodeActivity.this, Main3Activity.class);
                    startActivity(intent);
                    finish();

                }
            });

            CancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ScannedCodeActivity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }}