package com.example.saif.qrcodeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessfulActivity extends AppCompatActivity {

    Button OkBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful);

        Main2Activity.partsList.clear();



        OkBtn = (Button)findViewById(R.id.OkButton);

        OkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessfulActivity.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
