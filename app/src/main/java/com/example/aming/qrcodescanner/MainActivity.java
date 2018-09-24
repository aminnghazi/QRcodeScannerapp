package com.example.aming.qrcodescanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener {
    FloatingActionButton fb;
    private ZXingScannerView scannerView;
    //    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
//    static final short REQESTCODE_SCAN = 1;
    public static final int REQUEST_CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fb = findViewById(R.id.fb);
        fb.setOnClickListener(this);

//request for permission////////
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            if (checkpermission()){
                Toast.makeText(this, "Permision Is Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                requestpermission();
            }
        }
//-----------------------******

    }
    //------------------------------------------------------------------------------------------------//
//-------------------------------//
    private boolean checkpermission(){
        return (ContextCompat.checkSelfPermission(MainActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestpermission(){
        ActivityCompat.requestPermissions(this , new String[]{CAMERA},REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode ,String[] permissions ,int[] grantresults ){
        switch (requestCode){
            case REQUEST_CAMERA:
                if (grantresults.length>0){
                    boolean ispermitted = grantresults[0] == PackageManager.PERMISSION_GRANTED;
                    if (ispermitted){
                        Toast.makeText(this, "Permision Granted Now", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Permision Denied", Toast.LENGTH_SHORT).show();
                        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(CAMERA)){
                                displayDialog("App needs permission To Scan QR Codes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{CAMERA} , REQUEST_CAMERA);
                                        }
                                    }
                                });
                                return;
                            }
                        }

                    }
                }
                break;
        }
    }
    //----------------------------------------------------------------------------------------------//
    public void displayDialog(String message , DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(this).setMessage(message).setPositiveButton("OK", listener)
                .setNegativeButton("Cancel" , null).create().show();
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
//            if(checkpermission()){
//                if(scannerView == null ){
//                    scannerView = new ZXingScannerView(this);
//                    setContentView(scannerView);
//                }
//                scannerView.setResultHandler(this);
//                scannerView.startCamera();
//            }
//            else{
//                requestpermission();
//            }
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        scannerView.stopCamera();
//    }

    @Override
    public void handleResult(final Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Open Webpage");
        builder.setPositiveButton(" No,Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(MainActivity.this);
            }
        });
        builder.setNeutralButton("Open WebPage", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i  = new Intent(Intent.ACTION_VIEW , Uri.parse(scanResult));
                startActivity(i);
            }
        });
        builder.setMessage("Do You Want To Open  " + scanResult + " ?");
        AlertDialog alertDialog  =  builder.create();
        alertDialog.show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fb:
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                if(checkpermission()){
//                    if(scannerView == null ){
//                        scannerView = new ZXingScannerView(this);
//                        setContentView(scannerView);
                    Intent i = new Intent(this, ScannerQR.class);
                    startActivity(i);
                    break;
                    }
//                    scannerView.setResultHandler(this);
//                    scannerView.startCamera();
                else{
                    requestpermission();
                    break;
                }
            }
        }
}}

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode)
//    }
//}

