package com.example.saif.qrcodeapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {



    private ArrayList<String> data = new ArrayList<String>();
    public  static  Uri uri;
    public  static ListView lv;
    public static TextView tv;
    ArrayList<String>TagNos = new ArrayList<>();
    ArrayList<String> PartNos = new ArrayList<String>();
    ArrayList<String> LocationCodes = new ArrayList<String>();
    ArrayList<String> pcsNos = new ArrayList<String>();
    ArrayList<String> grossWeights = new ArrayList<String>();
    ArrayList<String> descriptions = new ArrayList<String>();
    public static  String emailBody;
    Button ConfrmBtn,CancelBtn;
    public static Uri u1;

    ArrayAdapter<String> adapter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv = (TextView)findViewById(R.id.pcsTotalTv);


        if(Main2Activity.partsList.isEmpty()){
            int TotalPcs=0,totalWeight=0;
            tv.setText(TotalPcs + "Pcs " + "-" + totalWeight +" lbs" + "\n         TOTAL");
            tv.setTextColor(Color.BLACK);
//            Main3Activity.lv.setAdapter(null);

        }

        for(MetalPart part : Main2Activity.partsList){

            TagNos.add(part.getTagNo());
            PartNos.add(part.getPartNo());
            LocationCodes.add(part.getLocationCode());
            pcsNos.add(part.getPcs());
            grossWeights.add(part.getGrossWeight());
            descriptions.add(part.getDescription());
        }

        generateListContent();
        // PartNos.add(Main2Activity.partsList.get(1).getPartNo());

        //Toast.makeText(getApplicationContext(),PartNos.get(0),Toast.LENGTH_LONG).show();

        lv = (ListView) findViewById(R.id.lView);

        ConfrmBtn = (Button)findViewById(R.id.ConfirmBtn);
        CancelBtn = (Button)findViewById(R.id.btn_New);

        ConfrmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //writeToFile("",getApplicationContext());
                String fileData="";

                for(MetalPart part : Main2Activity.partsList){
                    fileData = fileData +  part.getTagNo() + ",";
                    fileData=fileData + part.getPartNo() + ",";
                    fileData=fileData +part.getLocationCode() + ",";
                    fileData=fileData +part.getPcs()+ ",";
                    fileData=fileData +part.getGrossWeight() + ",";
                    fileData=fileData +part.getDescription() + ",";
                    fileData=fileData +part.getDateTagged()+ "\n";
                }

              //  Toast.makeText(getApplicationContext(),fileData,Toast.LENGTH_LONG).show();

                //writeToFile(fileData,getApplicationContext());


                    sendEmail(fileData);


                    //finish();


            }
        });

        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Main2Activity.partsList.clear();
                lv.setAdapter(null);
                int TotalPcs=0;
                double totalWeight=0;

                tv.setText(TotalPcs + "Pcs " + "-" + totalWeight +" lbs" + "\n         TOTAL");
                tv.setTextColor(Color.BLACK);


            }
        });
        lv.setAdapter(new MyListAdaper(this, R.layout.scannedlist, Main2Activity.partsList));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Main3Activity.this, "List item was clicked at " + position, Toast.LENGTH_SHORT).show();
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

    private void generateListContent() {
        for(int i = 0; i < 55; i++) {
            data.add("This is row number " + i);
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
        getMenuInflater().inflate(R.menu.main3, menu);
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
           // Intent intent = new Intent(Main3Activity.this,EmailLoginActivity.class);
           // startActivity(intent);
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

            Intent intent = new Intent(Main3Activity.this,Main2Activity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_revScans) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }



        }  else if (id == R.id.nav_LogOut) {
            LoginActivity.writeToFile("",getApplicationContext());
            LoginActivity.UserLoggedIn="";
            LoginActivity.PassLoggedIn="";
            Intent intent = new Intent(Main3Activity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        else if(id == R.id.nav_add){

            Intent i = new Intent(getApplicationContext(), fields_activity.class);
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class MyListAdaper extends ArrayAdapter<MetalPart> {
        private int layout;
        private List<MetalPart> mObjects;
        private MyListAdaper(Context context, int resource, List<MetalPart> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
try{
    int TotalPcs=0;
    double totalWeight=0;
    for(MetalPart pcs: Main2Activity.partsList){
        double weight;
        int pcs2;

        pcs2 = Integer.parseInt(pcs.getPcs());

        TotalPcs = TotalPcs+pcs2;

        weight=Double.parseDouble(pcs.getGrossWeight());
        totalWeight=totalWeight+weight;
           // mObjects.add(pcs);
    }
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.TagTv = (TextView) convertView.findViewById(R.id.TagNoTV);
                viewHolder.PartTv = (TextView) convertView.findViewById(R.id.PartNoTV);
                viewHolder.LocCodeTv = (TextView) convertView.findViewById(R.id.LocCodeTV);
                viewHolder.PcsTv = (TextView) convertView.findViewById(R.id.PCSTV);
                viewHolder.GrossWTV = (TextView) convertView.findViewById(R.id.GrossWeightTV);
                viewHolder.DescTV = (TextView) convertView.findViewById(R.id.DescTV);
                viewHolder.TaggedDate = (TextView) convertView.findViewById(R.id.DateTagged);


                viewHolder.DeleteButton = (Button) convertView.findViewById(R.id.DeleteBtn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();
            final ViewHolder finalMainViewholder = mainViewholder;
            mainViewholder.DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Main2Activity.partsList.remove(position);
                    notifyDataSetChanged();
                   // Toast.makeText(getContext(), "Button was clicked for list item " + position, Toast.LENGTH_SHORT).show();
                   // finalMainViewholder.PartTv.setText("");

                    int TotalPcs=0;
                    double totalWeight=0;
                    for(MetalPart pcs: Main2Activity.partsList){
                        double weight;
                        int pcs2;
                        pcs2 = Integer.parseInt(pcs.getPcs());

                        TotalPcs = TotalPcs+pcs2;

                        weight=Double.parseDouble(pcs.getGrossWeight());
                        totalWeight=totalWeight+weight;

                    }

                    tv.setText(TotalPcs + "Pcs " + "-" + totalWeight +" lbs" + "\n         TOTAL");
                    tv.setTextColor(Color.BLACK);



                }
            });

            mainViewholder.TagTv.setText("             "+Main2Activity.partsList.get(position).getTagNo());
            mainViewholder.PartTv.setText(" Part No:  "+ Main2Activity.partsList.get(position).getPartNo());
            mainViewholder.LocCodeTv.setText(" Location Code:  " + Main2Activity.partsList.get(position).getLocationCode());
            mainViewholder.PcsTv.setText(" Pcs:  " + Main2Activity.partsList.get(position).getPcs());
            mainViewholder.GrossWTV.setText(" Gross Weight:  "+Main2Activity.partsList.get(position).getGrossWeight());
            //mainViewholder.DescTV.setText("Description: "+Main2Activity.partsList.get(position).getDescription());
            mainViewholder.TaggedDate.setText(" Tag Date:  " +Main2Activity.partsList.get(position).getDateTagged());

            mainViewholder.TagTv.setTextColor(Color.BLACK);

            mainViewholder.PartTv.setTextColor(Color.BLACK);
            mainViewholder.LocCodeTv.setTextColor(Color.BLACK);
            mainViewholder.PcsTv.setTextColor(Color.BLACK);
            mainViewholder.GrossWTV.setTextColor(Color.BLACK);
            mainViewholder.DescTV.setTextColor(Color.BLACK);
            mainViewholder.TaggedDate.setTextColor(Color.BLACK);



            tv.setText(TotalPcs + "Pcs " + "-  " + totalWeight +" lbs" + "\n         TOTAL");
            tv.setTextColor(Color.BLACK);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Wrong Code Scanned please Scan Again", Toast.LENGTH_LONG).show();
        }

            return convertView;
        }

    }


    public static class ViewHolder {
        TextView TagTv;
        TextView PartTv;
        TextView LocCodeTv;
        TextView PcsTv;
        TextView GrossWTV;
        TextView DescTV;
        TextView TaggedDate;
        Button DeleteButton;
    }

    public  void sendEmail(String fileData){
        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
            File dir    =   new File (root.getAbsolutePath() + "/PersonData");
            dir.mkdirs();
            file   =   new File(dir, "Data.csv");
            FileOutputStream out   =   null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                String Columns = "Tag Number, PartNumber, Location Code, Pieces, Gross Weight, Description, Date Tagged, Time\n";
                out.write(Columns.getBytes());
                out.write(fileData.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

         u1  =   null;
        u1  =   Uri.fromFile(file);

         emailBody = "User: " +  LoginActivity.UserLoggedIn;

        for(MetalPart part : Main2Activity.partsList){

//            emailBody = emailBody + "\nTag No: "  + part.getTagNo() + "\nPart No: " +
  //                  part.getPartNo() + "\nPcs: " + part.getPcs() + "\nLocation Code: " + part.getLocationCode() + "\n";


            emailBody = "|"  + part.getTagNo() + "|" + part.getPartNo() + "|" + part.getPcs() + "|" + part.getLocationCode() + "";

        }


        Intent intent = new Intent(Main3Activity.this,SendEmailActivity.class);
        startActivity(intent);
        //finish();

//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:scannedtags@kloecknermetals.com")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, "scannedtags@kloecknermetals.com");
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Consignment Details");
//        intent.putExtra(Intent.EXTRA_TEXT,"Hello - There has been a new consignment scan” Below are the details:\n"+ emailBody);
//        intent.putExtra(Intent.EXTRA_STREAM, u1);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent,444);
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode== Activity.RESULT_CANCELED){

            int TotalPcs=0;
            int totalWeight=0;
            tv.setText(TotalPcs + "Pcs " + "-" + totalWeight +" lbs" + "\n         TOTAL");
            tv.setTextColor(Color.BLACK);
            Intent intent = new Intent(Main3Activity.this,SuccessfulActivity.class);
            startActivity(intent);
            finish();

        }
    }

    public static void writeToFile(String data, Context context) {
        try {
            File sdCard = Environment.getExternalStorageDirectory();
//            File dir = new File (sdCard.getAbsolutePath());
//            dir.mkdirs();
            File file = new File(sdCard.getAbsolutePath(), "MetalParts.csv");

            uri = Uri.fromFile(file);
            FileOutputStream f = new FileOutputStream(file);
            f.write(data.getBytes());
            f.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



}
