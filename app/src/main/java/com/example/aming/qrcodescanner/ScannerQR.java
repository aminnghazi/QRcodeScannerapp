package com.example.aming.qrcodescanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScannerQR extends AppCompatActivity implements  ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new  ZXingScannerView(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(scannerView);

    }
    //------------------------------------------------------------------------------------------------//
//-------------------------------//

    @Override
    protected void onResume() {
        super.onResume();
                scannerView = new ZXingScannerView(this);
                setContentView(scannerView);
                scannerView.setResultHandler(this);
                scannerView.startCamera();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {
        final String scanResult = result.getText();
//        Intent i = new Intent();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Open Webpage");
        builder.setPositiveButton(" No,Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(ScannerQR.this);
//                setResult(RESULT_CANCELED);
                finish();
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
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
