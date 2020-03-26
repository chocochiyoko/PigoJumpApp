package com.example.pigojump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        Intent mIntent = getIntent();
        int Score = mIntent.getIntExtra("Score", 0);
        String ScoreString = "Your Score: " + Score;
        TextView textView = findViewById(R.id.CurrScore);
        textView.setText(ScoreString);
    }

    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MainGameActivity.class);
        startActivity(intent);
    }
}
