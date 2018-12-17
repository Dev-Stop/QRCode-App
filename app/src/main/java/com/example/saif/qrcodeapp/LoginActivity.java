package com.example.saif.qrcodeapp;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Saif on 12/7/2016.
 */

public class LoginActivity extends Activity {

    public static String UserLoggedIn= "";
    public static String PassLoggedIn="";
    DataBaseHelper myDb;
    EditText UserName,Password;
    FloatingActionButton LoginBtn;
    boolean loggedIn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String[] perms = {  Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        int permsRequestCode = 200;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
            System.out.println("requesting permissions ");
        }

        String logged = readFromFile(getApplicationContext());
        UserLoggedIn = logged;
        if(!logged.equals("")){
            Intent intent = new Intent(LoginActivity.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }

        myDb = new DataBaseHelper(this);

        //boolean isInserted =

        myDb.insertData("Zubair","Baqai");
//        myDb.insertData("kloeckner1", "KMC500!");
//        myDb.insertData("amy.ostean", "Jordan23");
//        myDb.insertData("eatonfay", "Bragg2017!");
//        myDb.insertData("eatonlinc", "Logan2017!");
//        myDb.insertData("julia.schmidt", "Handball2017!!");
//        myDb.insertData("matt.meyer", "Vol$2017!");
//        myDb.insertData("jonathan.toler", "Owl$2017!");


//        if(isInserted == true)
//            Toast.makeText(LoginActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
//        else
//            Toast.makeText(LoginActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

        UserName = (EditText) findViewById(R.id.editText1);
        Password = (EditText) findViewById(R.id.editText2);

        LoginBtn = (FloatingActionButton)findViewById(R.id.button4);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(getApplicationContext(), UserName.getText() + ".." + Password.getText() ,Toast.LENGTH_LONG).show();

                   Cursor res = myDb.getAllData();
                    if(res.getCount() == 0) {
                        // show message
                       // showMessage("Error","Nothing found in DataBase");
                        Toast.makeText(getApplicationContext(),"No Data Found",Toast.LENGTH_LONG).show();

                        return;
                    }
                    else{
                        while (res.moveToNext()) {
                          //  Toast.makeText(getApplicationContext(), "ID:"+ res.getString(0) ,Toast.LENGTH_SHORT).show();
                        if( UserName.getText().toString().equals(res.getString(0)) &&  Password.getText().toString().equals(res.getString(1))) {
                          //  Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                            writeToFile(UserName.getText().toString(),getApplicationContext());
                            UserLoggedIn = UserName.getText().toString();
                            PassLoggedIn = Password.getText().toString();
                            loggedIn = true;
                            Intent intent = new Intent(LoginActivity.this,Main2Activity.class);
                            startActivity(intent);
                            finish();
                        }

                        }
                        if(!loggedIn)
                            Toast.makeText(getApplicationContext(), "    Login Unsuccessful\n Wrong Username/Password", Toast.LENGTH_SHORT).show();

                    }

                    //finish();
                    //Intent intent = new Intent(ManagerLogin.this,ManagerActivity.class);
                   // startActivity(intent);

                  //  Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();



            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){

        switch(permsRequestCode){

            case 200:

                boolean writeAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;

                break;

        }

    }

    public static void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}