package com.example.saif.qrcodeapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class user_pass_mail extends AppCompatActivity {

    String editText;
    String editText2;
    FloatingActionButton button;
    FloatingActionButton button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pass_mail);

        button = (FloatingActionButton) findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText e1 = (EditText) findViewById(R.id.editText1);
                EditText e2 = (EditText) findViewById(R.id.editText2);

                String username = e1.getText().toString();
                String password = e2.getText().toString();

                Constants.username = username;
                Constants.password = password;

                SharedPref.write("username",username);
                SharedPref.write("password",password);

                Toast.makeText(getApplicationContext(), "Your Email and Password is Saved", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void onBackPressed(){

        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();

    }
}
