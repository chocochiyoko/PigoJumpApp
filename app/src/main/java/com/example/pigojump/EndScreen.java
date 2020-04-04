package com.example.pigojump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        Intent mIntent = getIntent();
        int Score = mIntent.getIntExtra("Score", 0);
        currScore = Score;
        db = Room.databaseBuilder(this,
                ScoresDB.class, "database-name").allowMainThreadQueries().build();
        String ScoreString = "Your Score: " + Score;
        TextView currScoreView = findViewById(R.id.CurrScore);
        currScoreView.setText(ScoreString);
        reprintScores();
        submitted = false;
    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }
    public void sendMessage2(View view) {
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.nameinput);
        name = editText.getText().toString();
        db.ScoresDao().insertAll(new Scores(name, currScore));
        System.out.println("trying to add");
        currrank = db.ScoresDao().getRank(currScore);


        scoreDisplay = "";
        toAdd= "Your Rank:   " + currrank + ")" + name + "........." + currScore + "\n\n";
        scoreDisplay += toAdd;
        reprintScores();
        submitted = true;
        System.out.println("done");

    }
    public void reprintScores(){
        if (!submitted){
            scoreslist = db.ScoresDao().getTopTen();


            for (int i = 1; i <= scoreslist.size(); i++){
                toAdd = i + ")" + scoreslist.get(i-1).toString() + "\n";
                scoreDisplay += toAdd;
            }
            TextView ScoresList = findViewById(R.id.AllScores);
            ScoresList.setText(scoreDisplay);

        }


    }
}
