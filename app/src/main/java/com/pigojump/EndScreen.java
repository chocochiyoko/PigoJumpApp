package com.pigojump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class EndScreen extends AppCompatActivity {

    private List<Scores> scoreslist;
    private ScoresDB db;
    private int currScore;
    private String scoreDisplay = "\n\n", toAdd = "", name = "";
    private boolean submitted = false;
    private int currrank;
    private TextView currScoreView;
    private TextView ScoresList;
    private boolean pressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_end_screen);
        Intent mIntent = getIntent();
        int Score = mIntent.getIntExtra("Score", 0);
        currScore = Score;
        db = Room.databaseBuilder(this,
                ScoresDB.class, "database-name").allowMainThreadQueries().build();
        String ScoreString = "Your Score: " + Score;
        currScoreView = findViewById(R.id.CurrScore);
        currScoreView.setText(ScoreString);
        ScoresList = findViewById(R.id.AllScores);
        reprintScores();
        submitted = false;
    }

    public void sendMessage(View view) {
        // Do something in response to button
        if(!pressed){
            pressed = true;
            Intent intent = new Intent(this, MainGameActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public void sendMessage3(View view) {
        // Do something in response to button
        if(!pressed){
            pressed = true;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
    public void sendMessage2(View view) {

        if (!submitted){
            // Do something in response to button
            EditText editText = (EditText) findViewById(R.id.nameinput);
            name = editText.getText().toString();
            if (name.length() > 8 ) {
                name = name.substring(0,8);
            }
            if (name != ""){
                db.ScoresDao().insertAll(new Scores(name, currScore));
                System.out.println("trying to add");
                currrank = db.ScoresDao().getRank(currScore);


                scoreDisplay = "";
                toAdd= "Your Rank: \n" + currrank + ")" + name + ": " + currScore + "\n\n";
                //scoreDisplay += toAdd;
                currScoreView.setText(toAdd);
                //reprintScores();
                submitted = true;
                editText.setText("");
                System.out.println("done");
            }

        }


    }
    public void reprintScores(){
        if (!submitted){
            scoreslist = db.ScoresDao().getTopTen();
            scoreDisplay="\n\n";


            for (int i = 1; i <= scoreslist.size(); i++){
                toAdd = i + ")" + scoreslist.get(i-1).toString() + "\n";
                scoreDisplay += toAdd;
            }

            ScoresList.setText(scoreDisplay);

        }


    }
}
