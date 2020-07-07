package com.example.lowpop;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.TextView;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;




public class Counter extends AppCompatActivity implements CountDownAnimation.CountDownListener{
    public int countdown = 3;
    private TextView timerBeforeGame;
    MediaPlayer countdownSound;


    private CountDownAnimation countDownAnimation ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

//countdown sound
        countdownSound = MediaPlayer.create(this, R.raw.countdown);
        if (HomeActivity.soundPlaying) {
            countdownSound.start();
        }

        timerBeforeGame = (TextView) findViewById(R.id.timerToBegin);


        initCountDownAnimation();

        startCountDownAnimation();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Counter.this, Game.class);

                // start the activity connect to the specified class
                startActivity(intent);            }
        }, 2700);


    }

    private void initCountDownAnimation() {
        countDownAnimation = new CountDownAnimation(timerBeforeGame, getStartCount());
        countDownAnimation.setCountDownListener(this);
    }

    private void startCountDownAnimation() {


            Animation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f,
                    0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f); //from x= beginning -> center. to X =end left 0-1 = little to big
            Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);//from Y =beginning = big1 to little toY=0
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(scaleAnimation);
            animationSet.addAnimation(alphaAnimation);
            countDownAnimation.setAnimation(animationSet);

        // Customizable start count
        countDownAnimation.setStartCount(3);
        countDownAnimation.start();
    }

    private void cancelCountDownAnimation() {
        countDownAnimation.cancel();
    }

    private int getStartCount() {
        return countdown;
    }

    @Override
    public void onCountDownEnd(CountDownAnimation animation) {
        timerBeforeGame.setText("GO!");
    }

}

