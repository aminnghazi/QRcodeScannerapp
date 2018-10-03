package com.example.aming.qrcodescanner;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayer extends AppCompatActivity implements View.OnClickListener {
    //swipe
    //customize layout
    //not stopped after locking
    //Background playing
    VideoView videoView;
    ProgressDialog progDial;
    ImageButton rotate_btn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer_layout);
        //getSupportActionBar().hide();
        if(savedInstanceState!=null){
            int position = savedInstanceState.getString("message");
        }


        videoView = findViewById(R.id.videoView);
        Intent i = getIntent();
        String videoUrl = i.getStringExtra("adress");

        Toast.makeText(this, "" + videoUrl, Toast.LENGTH_SHORT).show();

        rotate_btn = findViewById(R.id.rotate);
        rotate_btn.setOnClickListener(this);

        progDial = new ProgressDialog(this);
        progDial.setTitle("Playing video");
        progDial.setMessage("loading...");
        progDial.setCancelable(false);
        progDial.show();

        try {
            //Creating MediaController
            MediaController controller = new MediaController(this);
            controller.setAnchorView(videoView);
            Uri uri = Uri.parse(videoUrl);
            videoView.setMediaController(controller);
            videoView.setVideoURI(uri);
        } catch (Exception ex) {
            Toast.makeText(this, "Some Problem Has Occured", Toast.LENGTH_SHORT).show();
        }


        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
//                 progDial.dismiss();
                Toast.makeText(VideoPlayer.this, "Error", Toast.LENGTH_SHORT).show();
                finish();
                return false;
            }
        });

        videoView.requestFocus();
//        videoView.canSeekForward();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progDial.dismiss();
                videoView.start();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("pos",videoView.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        if ( progDial!=null && progDial.isShowing() ) {
            progDial.cancel();
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        videoView.pause();
        if ( progDial!=null && progDial.isShowing() ){
            progDial.cancel();}
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if ( progDial!=null && progDial.isShowing() ){
            progDial.cancel();
        }
    }


    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.rotate:
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
           // else if(getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
             //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            break;

    }
    }
}
