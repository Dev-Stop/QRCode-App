package com.example.saif.qrcodeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class SendEmailActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    String username = SharedPref.read("username","Not Found");
    String password = SharedPref.read( "password", "Not Found");
    Button csv_button;
    private Multipart _multipart;
    boolean isFromMain = false;

    //Send button
    private FloatingActionButton buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        checkWritingPermission();
        if(getIntent().hasExtra("main")) {
            isFromMain = getIntent().getBooleanExtra("main", false);
        }

        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        csv_button = (Button)findViewById(R.id.csvv);
        if(isFromMain){
            csv_button.setEnabled(false);
            csv_button.setVisibility(View.INVISIBLE);
        }

        csv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileName = "AnalysisData.csv";
                String filePath = baseDir + File.separator + fileName;
                File f = new File(filePath);
                CSVWriter writer = null;

                if (f.exists() && !f.isDirectory()) {
                    FileWriter mFileWriter = null;
                    try {
                        mFileWriter = new FileWriter(filePath, true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    writer = new CSVWriter(mFileWriter);
                    Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        writer = new CSVWriter(new FileWriter(filePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String[] entries = Main3Activity.emailBody.split("\\|");
                writer.writeNext(entries);

                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                File file = new File(filePath, "AnalysisData.csv");
                file = new File(file.getAbsolutePath());
                Log.i("namre", file.getName());

            }



            });

        if(isFromMain)
            editTextEmail.setText(username);
        else {
            editTextEmail.setText("");
            editTextSubject.setText("Consignment Details");
            editTextMessage.setText(Main3Activity.emailBody);
        }

        buttonSend = (FloatingActionButton) findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(this);
    }

    private void checkWritingPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SendEmailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
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

    private void sendEmail() {
        //Getting content for email

        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();


        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);
      //  SendMail sm1 = new SendMail(this, SendEmailActivity.);
      //  SendMail sm1 = new SendMail(this );

        //Executing sendmail to send email
if(isFromMain)
    SendMail.email=email;
else{
    SendMail.email=email;
    SendMail.message1=message;
    SendMail.subject=subject;
}
        sm.execute();

    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
}