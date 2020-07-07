package com.example.lowpop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Score extends AppCompatActivity {
    TextView bestScoreTxt;
    Button back_button, new_game_btn_score;
    static int bestScore = 0;
    int best1, best2, best3;
    MediaPlayer menuSound;
    ListView listScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        final int lastScore = Game.getLastScore();

        //shared preference to save the scores
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editorpref = preferences.edit();
        editorpref.putInt("lastscore", lastScore );
        editorpref.apply();

        //load old scores
        best1 = preferences.getInt("best1", 0);
        best2 = preferences.getInt("best2", 0);
        best3 = preferences.getInt("best3", 0);


        // replace if there is a high score
        if (lastScore > best3) {
            best3 = lastScore;
            editorpref.putInt("best3", best3 );
            editorpref.apply();
        }
        if (lastScore > best2) {
            int temp = best2;
            best2 = lastScore;
            best3 = temp;
            editorpref.putInt("best3", best3 );
            editorpref.putInt("best2", best2 );
            editorpref.apply();
        }
        if (lastScore > best1) {
            int temp = best1;
            best1 = lastScore;
            best2 = temp;
            editorpref.putInt("best1", best1 );
            editorpref.putInt("best2", best2 );
            editorpref.apply();

        }



        //placing the load scores in a list
        listScore = (ListView) findViewById(R.id.listScore) ;
        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(getString(R.string.best_score_txt) +"\n" + best1);
        arrayList.add(getString(R.string.secondbest_txt) +"\n" + best2);
        arrayList.add(getString(R.string.thirdbest_txt) +"\n"+ best3);
        arrayList.add(getString(R.string.lastscore_txt) +"\n"+ lastScore);


        ArrayAdapter arrayAdapter= new ArrayAdapter(this, R.layout.score_text_view, arrayList);
        listScore.setAdapter(arrayAdapter);

        //toast with best score and actual score
        listScore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((position)==0 || position==3)
                Toast.makeText(Score.this, getString(R.string.best_score_txt)+": " + best1 + ". "+ getString(R.string.lastscore_txt) + ": " + lastScore, Toast.LENGTH_SHORT).show();
                else if ((position)==1)
                    Toast.makeText(Score.this, getString(R.string.secondbest_txt)+": " + best2 + ". "+ getString(R.string.lastscore_txt) + ": " + lastScore, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Score.this, getString(R.string.thirdbest_txt)+": " + best3 + ". "+ getString(R.string.lastscore_txt) + ": " + lastScore, Toast.LENGTH_SHORT).show();

            }
        });




        back_button = (Button) findViewById(R.id.back_button_score);
        new_game_btn_score = (Button) findViewById(R.id.new_Game_btn_score);
        bestScoreTxt = (TextView) findViewById(R.id.congrats_txt);



/*
//clean the scoreboard
        editorpref.remove("best1");
        editorpref.remove("best2");
        editorpref.remove("best3");
        editorpref.commit();
*/

                bestScoreTxt.setText(getString(R.string.tryagain_txt) +" \n");





//back redirectoring button

            back_button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Intent intent = new Intent(Score.this, HomeActivity.class);

                    startActivity(intent);
                }
            });

        //new game redirectoring button

        new_game_btn_score.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    HomeActivity.setGamePlayed();

                    Intent intent = new Intent(Score.this, Counter.class);

                    startActivity(intent);
                }
            });
        }


    }

