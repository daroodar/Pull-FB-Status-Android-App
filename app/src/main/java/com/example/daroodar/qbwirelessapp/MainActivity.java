package com.example.daroodar.qbwirelessapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

TextView StatusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusText = (TextView) findViewById(R.id.Text1);


        //Checking Internet Connection
        if(!isNetworkConnected()) {
            //Display Error Dialog
            noInternetDialog(this);
        }
        else {
            acquireFBStatus();
        }


    }

    //Method to check internet connection
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    //Method to Acquire Facebook Status from Dummy Test Company
    private void acquireFBStatus(){
        //Initializing Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());

        //Initializing token using various IDs extracted from my facebook developer account
        AccessToken token= new AccessToken(getString(R.string.accessToken),
                getString(R.string.facebook_app_id),
                getString(R.string.userID),null,null,null,null,null );
        GraphRequest req= GraphRequest.newGraphPathRequest(token, "453355835083862",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        //Error Checking
                        FacebookRequestError error = response.getError();
                        String Error= error.getErrorMessage();
                        if(Error!=null) {
                            Log.e("Error", Error);
                            StatusText.setText("Oops! Error: " + Error);
                        }
                        else{
                            String setStatusText=response.getJSONObject().toString();
                            //Putting limit of 50 characters
                            if(setStatusText.length()>50){
                                setStatusText=setStatusText.substring(0,49);
                            }
                            StatusText.setText(setStatusText);
                        };
                    }
                });
        GraphRequest.executeBatchAsync(req);
    }

    //No Internet Error Dialog
    private void noInternetDialog(Context context){
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Oops! not connected to internet! Connect and try again");
        builder1.setCancelable(false);

        builder1.setNeutralButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(isNetworkConnected()) {
                            builder1.setCancelable(true);
                            dialog.cancel();
                        }
                    }
                });



        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    //Handling Refresh Button Response
    public void onClickRefresh(View v){
        if(!isNetworkConnected()) {
            noInternetDialog(this);
            acquireFBStatus();
        }
    }
}

