package com.example.saif.qrcodeapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.EditText;
import android.widget.Toast;
import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
 

 
//Class is extending AsyncTask because this class is going to perform a networking operation
public class SendMail extends AsyncTask<Void,Void,Void> {

    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    //Declaring Variables
    private Context context;
    private Session session;
    boolean authentication=false;
    //Information to send email
    public static String email;
    public static String subject;
    public static String message1;

    String username = SharedPref.read("username","null");
    String password = SharedPref.read( "password", "null");


    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;
 
    //Class Constructor
    public SendMail(Context context, String email, String subject, String message){
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message1 = message;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        //Showing a success message
       if(authentication && !Main2Activity.getHelp){
           Toast.makeText(context,"Message Sent",Toast.LENGTH_LONG).show();
           int TotalPcs=0;
           int totalWeight=0;


           context.startActivity(new Intent(context, SuccessfulActivity.class));
           ((Activity)context).finish();
       }

        if(!authentication){
           Toast.makeText(context,"Wrong User/Password",Toast.LENGTH_SHORT).show();
       }
        Toast.makeText(context,"Email Sent",Toast.LENGTH_SHORT).show();

    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();

        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");


        if(username.equalsIgnoreCase("null") || password.equalsIgnoreCase("null")){
            Toast.makeText(context,"Username/Password is null",Toast.LENGTH_SHORT).show();

        }
        //Creating a new session
        session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username ,password);
                    }
                });

        try {
            if(Main2Activity.getHelp) {
                //Creating MimeMessage object
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("divclasss@gmail.com"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(subject);
                //3) create MimeBodyPart object and set your message content
                BodyPart messageBodyPart1 = new MimeBodyPart();

                messageBodyPart1.setText(message1);
                //4) create new MimeBodyPart object and set DataHandler object to this object
                //MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                //Location of file to be attached
                //String filename = Environment.getExternalStorageDirectory().getPath() + "/AnalysisData.csv"; //change accordingly
                //DataSource source = new FileDataSource(filename);
                //messageBodyPart2.setDataHandler(new DataHandler(source));
                //messageBodyPart2.setFileName(filename);
                //5) create Multipart object and add MimeBodyPart objects to this object
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart1);
                //multipart.addBodyPart(messageBodyPart2);
                //6) set the multiplart object to the message object
                message.setContent(multipart);
                //7) send message
                Transport.send(message);
                System.out.println("MESSAGE SENT....");

//            if(!Main2Activity.getHelp) {
//                source = new FileDataSource(Main3Activity.u1.getPath());
//                message.setDataHandler(new DataHandler(source));
//                message.setFileName(Main3Activity.u1.getPath());
//
//                //multipart.addBodyPart(messageBodyPart);
//            }
                //Sending email
                Transport.send(message);
                authentication = true;
            }else{
                //Creating MimeMessage object
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress("divclasss@gmail.com"));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                message.setSubject(subject);
                //3) create MimeBodyPart object and set your message content
                BodyPart messageBodyPart1 = new MimeBodyPart();

                messageBodyPart1.setText(message1);
                //4) create new MimeBodyPart object and set DataHandler object to this object
                MimeBodyPart messageBodyPart2 = new MimeBodyPart();
                //Location of file to be attached
                String filename = Environment.getExternalStorageDirectory().getPath() + "/AnalysisData.csv"; //change accordingly
                DataSource source = new FileDataSource(filename);
                messageBodyPart2.setDataHandler(new DataHandler(source));
                messageBodyPart2.setFileName(filename);
                //5) create Multipart object and add MimeBodyPart objects to this object
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart1);
                multipart.addBodyPart(messageBodyPart2);
                //6) set the multiplart object to the message object
                message.setContent(multipart);
                //7) send message
                Transport.send(message);
                System.out.println("MESSAGE SENT....");

//            if(!Main2Activity.getHelp) {
//                source = new FileDataSource(Main3Activity.u1.getPath());
//                message.setDataHandler(new DataHandler(source));
//                message.setFileName(Main3Activity.u1.getPath());
//
//                //multipart.addBodyPart(messageBodyPart);
//            }
                //Sending email
                Transport.send(message);
                authentication = true;
            }
        } catch (MessagingException e) {

            e.printStackTrace();
        }
        return null;
    }
}