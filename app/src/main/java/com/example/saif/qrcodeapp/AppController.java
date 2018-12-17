package com.example.saif.qrcodeapp;

import android.app.Application;

public class AppController extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPref.init(getApplicationContext());
    }

}
