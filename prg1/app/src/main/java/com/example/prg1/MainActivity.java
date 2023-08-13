package com.example.prg1;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView stopwatchText;
    private Button startButton, stopButton, resetButton, holdButton;
    private long startTime = 0L, elapsedTime = 0L, holdTime = 0L;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatchText = findViewById(R.id.stopwatchText);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);
        holdButton = findViewById(R.id.holdButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis() - elapsedTime;
                handler.postDelayed(updateTimer, 0);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimer);
                elapsedTime = System.currentTimeMillis() - startTime;
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(updateTimer);
                elapsedTime = 0L;
                updateStopwatchText();
            }
        });

        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holdTime == 0L) {
                    holdTime = System.currentTimeMillis();
                    handler.removeCallbacks(updateTimer);
                } else {
                    long holdDuration = System.currentTimeMillis() - holdTime;
                    startTime += holdDuration;
                    holdTime = 0L;
                    handler.postDelayed(updateTimer, 0);
                }
            }
        });
    }

    private Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            elapsedTime = System.currentTimeMillis() - startTime;
            updateStopwatchText();
            handler.postDelayed(this, 1000); // Update every 1 second
        }
    };

    private void updateStopwatchText() {
        int seconds = (int) (elapsedTime / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        int hours = minutes / 60;
        minutes %= 60;

        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        stopwatchText.setText(time);
    }
}
