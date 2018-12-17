package com.example.saif.qrcodeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static String filePath;
    public static boolean getHelp=false;
    Button scannedMaterialBtn,GetHelpBtn;
    public static String ScannedCode= "";
    public static  QRCodeEncoder qrCodeEncoder;
    public static ArrayList<MetalPart> partsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Config.EMAIL=EmailLoginActivity.readFromFile1(getApplicationContext());
        Config.PASSWORD=EmailLoginActivity.readFromFile2(getApplicationContext());

        scannedMaterialBtn = (Button) findViewById(R.id.scanBtn);
        GetHelpBtn = (Button)findViewById(R.id.HelpBtn);
        scannedMaterialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(Main2Activity.this);
                integrator.setBarcodeImageEnabled(true);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt(" ");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);

                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

        GetHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getHelp=true;
                Intent intent = new Intent(Main2Activity.this,SendEmailActivity.class);
                intent.putExtra("main",true);
                startActivity(intent);
//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:KMCDigitalization@kloecknermetals.com")); // only email apps should handle this
//                intent.putExtra(Intent.EXTRA_EMAIL, "KMCDigitalization@kloecknermetals.com");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "");
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                }
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {

            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
                } else {
                    filePath= result.getBarcodeImagePath();

                    //Toast.makeText(getApplicationContext(),filePath,Toast.LENGTH_LONG).show();
                    ScannedCode = result.getContents();
                    //Find screen size
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3/4;

                     qrCodeEncoder = new QRCodeEncoder(ScannedCode,
                            null,
                            Contents.Type.TEXT,
                            BarcodeFormat.QR_CODE.toString(),
                            smallerDimension);
                   // IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


                    // Toast.makeText(this, ScannedCode,Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Main2Activity.this,ScannedCodeActivity.class);
                    startActivity(intent);
                    finish();

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Wrong Code Scanned please Scan Again", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Intent intent = new Intent(Main2Activity.this,EmailLoginActivity.class);
            //startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scan) {
            // Handle the camera action
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }

        } else if (id == R.id.nav_revScans) {
            Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
            startActivity(intent);
            finish();


        }  else if (id == R.id.nav_LogOut) {
            LoginActivity.writeToFile("",getApplicationContext());
            LoginActivity.UserLoggedIn="";
            LoginActivity.PassLoggedIn="";
            Intent intent = new Intent(Main2Activity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else if(id == R.id.nav_add){

            Intent i = new Intent(getApplicationContext(), fields_activity.class);
            startActivity(i);
            finish();
        }

        else if(id == R.id.nav_setting){

            Intent i = new Intent(getApplicationContext(), user_pass_mail.class);
            startActivity(i);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
