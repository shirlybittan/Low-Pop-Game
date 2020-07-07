package com.example.lowpop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {
    MediaPlayer menuSound;
    Button score_btn;
    Button play_btn;
    Button HowTo_btn;
    ImageButton sound_btn;
    static boolean soundPlaying=true;
    static boolean gamePlayed=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        menuSound = MediaPlayer.create(this, R.raw.homebacksound);
        if (soundPlaying) {
            menuSound.start();
        }



        score_btn = (Button)findViewById(R.id.score_btn_id);
        play_btn = (Button)findViewById(R.id.play_btn_id);
        HowTo_btn = (Button)findViewById(R.id.how_btn_id);
        sound_btn = (ImageButton)findViewById(R.id.sound_btn);
        sound_btn.setOnClickListener(imgButtonHandler);


//check if sound playing to change the sound logo
        if(!soundPlaying)
            sound_btn.setBackgroundResource(R.drawable.sound_mute);


        //how to play redirectoring button
        HowTo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, HowToPlay.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });


        //new game redirectoring button

        play_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                gamePlayed=true;

                Intent intent = new Intent(HomeActivity.this, Counter.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });

        //score redirectoring button

        score_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(HomeActivity.this, Score.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });
    }


/*
    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }*/


    @Override
    protected void onPause() {
        super.onPause();
        menuSound.pause();
        //your code for stopping the sound
    }

    //image button on lick listener to change the picture and stop/play the sound
    View.OnClickListener imgButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {

            if (soundPlaying) {
                sound_btn.setBackgroundResource(R.drawable.sound_mute);
                menuSound.pause();
                soundPlaying = false;

            } else {
                sound_btn.setBackgroundResource(R.drawable.sound_transparant);
                menuSound.start();
                soundPlaying = true;
            }

        }
    };

    public static void setGamePlayed(){
        gamePlayed=true;
    }

}