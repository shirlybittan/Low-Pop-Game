package com.example.lowpop;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;


public class Game extends AppCompatActivity implements View.OnClickListener {
    private static int scoreCount;
    TextView score;
    TextView timerGame;
    public int countdown = 30;
    int clickCount = 1;
    FrameLayout frame;
    int round = 0;
    private Context mContext;
    private CoordinatorLayout mCLayout;
    private Animation mBounceAnimation;
    MediaPlayer popSound;
    MediaPlayer winSound;
    MediaPlayer lostSound;
    ImageView winPic;
    ImageView lostPic;
    CountDownTimer countDownTimer;


    Button back_btn, num1_btn, num2_btn, num3_btn, num_max_btn;
    int num[] = new int[5];

    int Xcoordinate[] = {150, 550, 250 ,650, 150, 550, 250 ,650};
     int Ycoordinate[] = {100, 100, 450,  450, 780, 780, 1100, 1100};


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


            scoreCount = 0;
        back_btn = (Button) findViewById(R.id.back_btn_game);
        num1_btn = (Button) findViewById(R.id.num1_btn_id);
        num2_btn = (Button) findViewById(R.id.num2_btn_id);
        num3_btn = (Button) findViewById(R.id.num3_btn_id);
        num_max_btn = (Button) findViewById(R.id.num_max_btn_id);

        //colorization of the buttons randomly
        random_btn_color();


        winPic = (ImageView) findViewById(R.id.imagewin);
        lostPic = (ImageView) findViewById(R.id.imagelost);


        frame = findViewById(R.id.frame);
        score = (TextView) findViewById(R.id.score_id);
        timerGame = (TextView) findViewById(R.id.timerToEnd);

        //random placement of the buttons
        randomBtnPlace();

        //countdown text on screen
        countDownTimer = new CountDownTimer(countdown*1000, 1000) {
            public void onTick(long millisUntilFinished) {

                timerGame.setText(String.valueOf(countdown));
                countdown--;
            }

//actions when countdown is over
            public void onFinish() {
                timerGame.setText(getString(R.string.timesup_txt));
                num1_btn.setEnabled(false);
                num2_btn.setEnabled(false);
                num3_btn.setEnabled(false);
                num_max_btn.setEnabled(false);

//change screen
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Game.this, Score.class);

                        // start the activity connect to the specified class
                        startActivity(intent);
                    }
                }, 1000);
            }
        }.start();

        num1_btn.setOnClickListener(this);
        num2_btn.setOnClickListener(this);
        num3_btn.setOnClickListener(this);
        num_max_btn.setOnClickListener(this);

        popSound = MediaPlayer.create(this, R.raw.popsound);
        winSound = MediaPlayer.create(this, R.raw.winsound);
        lostSound = MediaPlayer.create(this, R.raw.lostsound);


        mContext = getApplicationContext();

        //animation of jumping buttons when clicked
        mBounceAnimation = AnimationUtils.loadAnimation(mContext, R.anim.bounce);

        score.setText(Integer.toString(scoreCount));


        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                countDownTimer.cancel();
                countDownTimer = null;

                Intent intent = new Intent(Game.this, HomeActivity.class);

                // start the activity connect to the specified class
                startActivity(intent);
            }
        });



        fillBtnLevel1();

    }


    public static int getLastScore() {
        return scoreCount;
    }

    @Override
    public void onBackPressed() {

        countDownTimer.cancel();
        countDownTimer = null;
        Intent intent = new Intent(Game.this, HomeActivity.class);

        // start the activity connect to the specified class
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        //check if sound activated
        if (HomeActivity.soundPlaying)
            popSound.start();

        //check if the choosen button is the right one
        if (clickCount == 1 && v.getId() == R.id.num1_btn_id) {
            scoreCount += 10;
            v.startAnimation(mBounceAnimation);
            v.setVisibility(View.INVISIBLE);
            clickCount++;

        } else if (clickCount == 2 && v.getId() == R.id.num2_btn_id) {
            scoreCount += 10;
            v.startAnimation(mBounceAnimation);
            v.setVisibility(View.INVISIBLE);
            clickCount++;

        } else if (clickCount == 3 && v.getId() == R.id.num3_btn_id) {
            scoreCount += 10;
            v.startAnimation(mBounceAnimation);
            v.setVisibility(View.INVISIBLE);
            clickCount++;

        } else if (clickCount == 4 && v.getId() == R.id.num_max_btn_id) {
            scoreCount += 100;
            v.startAnimation(mBounceAnimation);
            v.setVisibility(View.INVISIBLE);
            //win sound
            if (HomeActivity.soundPlaying)
                winSound.start();

            //picture of winning (V)
            new CountDownTimer(700, 1000) {

                public void onTick(long millisUntilFinished) {
                    winPic.setVisibility(View.VISIBLE);
                }

                public void onFinish() {
                    winPic.setVisibility(View.INVISIBLE);

                }
            }.start();

            //wait for the pic to disappear to show new buttons

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    newRound();
                }
            }, 700);


            //         v.clearAnimation();

        } else {

            num1_btn.setVisibility(View.INVISIBLE);
            num2_btn.setVisibility(View.INVISIBLE);
            num3_btn.setVisibility(View.INVISIBLE);
            num_max_btn.setVisibility(View.INVISIBLE);

            //lost sound
            if (HomeActivity.soundPlaying)
                lostSound.start();

            //picture of lost(X)
            new CountDownTimer(700, 1000) {

                public void onTick(long millisUntilFinished) {
                    lostPic.setVisibility(View.VISIBLE);
                }

                public void onFinish() {
                    lostPic.setVisibility(View.INVISIBLE);

                }
            }.start();


            //wait for the pic to disappear to show new buttons
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    newRound();
                }
            }, 700);


            v.clearAnimation();
            scoreCount -= 10;
        }


        score.setText(Integer.toString(scoreCount));

    }

    public void newTableLevel1() { //create a table with the values to guess in the right order, in an easy range
        for (int i = 0; i < 5; i++) {
            num[i] = getRandomIntegerBetweenRange(0, 50);
        }

        if (!distinctValues(num))
            newTableLevel1();

        Arrays.sort(num);
    }

    public void newTableLevel2() { //create a table with the values to guess in the right order, in an harder range
        for (int i = 0; i < 5; i++) {
            num[i] = getRandomIntegerBetweenRange(-50, 50);
        }
        if (!distinctValues(num))
            newTableLevel2();

        Arrays.sort(num);
    }

    public static int getRandomIntegerBetweenRange(int min, int max) {
        int x = (int) (Math.random() * ((max - min) + 1)) + min;
        return x;
    }

    public void fillBtnLevel1() {

        newTableLevel1();
        num1_btn.setText(Integer.toString(num[0]));
        num2_btn.setText(Integer.toString(num[1]));
        num3_btn.setText(Integer.toString(num[2]));
        num_max_btn.setText(Integer.toString(num[3]));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void newRound() {

        clickCount = 1;
        if (scoreCount > 500)
            fillBtnLevel2();
        else
            fillBtnLevel1();

        random_btn_color();

        randomBtnPlace();

        num1_btn.setVisibility(View.VISIBLE);
        num2_btn.setVisibility(View.VISIBLE);
        num3_btn.setVisibility(View.VISIBLE);
        num_max_btn.setVisibility(View.VISIBLE);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void random_btn_color() {
        //orange, vert , bleu , rouge
        int[] colors = {1, 2, 3, 4};

        shuffleArray(colors);

        coloringButton(num1_btn, colors[0]);
        coloringButton(num2_btn, colors[1]);
        coloringButton(num3_btn, colors[2]);
        coloringButton(num_max_btn, colors[3]);


    }

    public void coloringButton(Button button, int randomNum) {
        switch (randomNum) {
            case 1:
                button.setBackgroundResource(R.drawable.round_blue_button);
                break; // break is optional

            case 2:
                button.setBackgroundResource(R.drawable.round_green_btn);
                break;
            case 3:
                button.setBackgroundResource(R.drawable.round_red_button);
                break;
            case 4:
                button.setBackgroundResource(R.drawable.round_orange_btn);
                break; // break is optional


        }
    }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        protected static void shuffleArray(int[] ar)
        {
            // If running on Java 6 or older, use `new Random()` on RHS here
            Random rnd = ThreadLocalRandom.current();
            for (int i = ar.length - 1; i > 0; i--) {
                int index = rnd.nextInt(i + 1);
                // Simple swap
                int a = ar[index];
                ar[index] = ar[i];
                ar[i] = a;
            }
        }

        void randomBtnPlace () {
            // button 1

            int temp1 = getRandomIntegerBetweenRange(0, Ycoordinate.length -1);
            num1_btn.setX((float) Xcoordinate[temp1]);
            num1_btn.setY((float) Ycoordinate[temp1]);


            //button 2

            int temp2 = getRandomIntegerBetweenRange(0, Ycoordinate.length -1);

            while (temp2 == temp1)
                temp2 = getRandomIntegerBetweenRange(0, Ycoordinate.length -1);

            num2_btn.setX((float) Xcoordinate[temp2]);
            num2_btn.setY((float) Ycoordinate[temp2]);


            //button 3

            int temp3 = getRandomIntegerBetweenRange(0, Ycoordinate.length -1);

            while (temp3 == temp1 || temp3 == temp2)
                temp3 = getRandomIntegerBetweenRange(0, Ycoordinate.length -1);

            num3_btn.setX((float) Xcoordinate[temp3]);
            num3_btn.setY((float) Ycoordinate[temp3]);


            //button 4


            int temp4 = getRandomIntegerBetweenRange(0, Ycoordinate.length -1);
            while (temp4 == temp1 || temp4 == temp2 || temp4 == temp3)
                temp4 = getRandomIntegerBetweenRange(0, Ycoordinate.length -1);

            num_max_btn.setX((float) Xcoordinate[temp4]);
            num_max_btn.setY((float) Ycoordinate[temp4]);

            round++;
        }


        public void fillBtnLevel2 () {
            newTableLevel2();

            num1_btn.setText(Integer.toString(num[0]));
            num2_btn.setText(Integer.toString(num[1]));
            num3_btn.setText(Integer.toString(num[2]));
            num_max_btn.setText(Integer.toString(num[3]));

        }


        public static boolean distinctValues ( int[] arr){
            Set<Integer> foundNumbers = new HashSet<Integer>();
            for (int num : arr) {
                if (foundNumbers.contains(num)) {
                    return false;
                }
                foundNumbers.add(num);
            }
            return true;
        }

        public void tapToAnimate (View view){
            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
            animation.setInterpolator(interpolator);
            view.startAnimation(animation);

        }
    }



