package com.example.lowpop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class HowToPlay extends AppCompatActivity {

    VideoView videov;
    TextView explanation1;
    Button back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);


        videov = (VideoView)findViewById(R.id.explanVideo);
        explanation1 = (TextView) findViewById(R.id.explanation1);
        back_btn = (Button) findViewById(R.id.back_btn_how);


        //back redirectoring button

        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(HowToPlay.this, HomeActivity.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });



     //reading the explicative video in loop
        final Uri uri = Uri.parse("android.resource://com.example.lowpop/"+R.raw.videoview);

        videov.setVideoURI(uri);
        videov.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        videov.requestFocus();
        videov.start();


    }
}
