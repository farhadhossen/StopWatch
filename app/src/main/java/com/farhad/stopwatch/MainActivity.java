package com.farhad.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    ImageView btnPayPause;
    CardView leftEarBg, rightEarBg, beatCard, bottomCard;
    Animation animation, animation2, animation3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        btnPayPause = findViewById(R.id.idPlayPause);
        leftEarBg = findViewById(R.id.idLeftEarBg);
        rightEarBg = findViewById(R.id.idRightEarBg);
        beatCard = findViewById(R.id.idBeatCard);
        bottomCard = findViewById(R.id.cardView);

        runTimer();
        playButtonImage();
        animations();


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }

    @Override
    protected void onResume() {
        super.onResume();
        animations();
    }


    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = !running;
        playButtonImage();
        rightEarBg.startAnimation(animation2);
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        btnPayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        leftEarBg.startAnimation(animation);
    }


    private void runTimer() {
        final TextView tvTime = (TextView)findViewById(R.id.idTvTime);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours, minutes, secs);
                tvTime.setText(covertBangla(time));
                if (running) {
                    seconds++;
                    beatCard.startAnimation(animation3);
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void playButtonImage(){
        if(running){
            btnPayPause.setImageResource(R.drawable.ic_baseline_pause_24);
        }else {
            btnPayPause.setImageResource(R.drawable.ic_baseline_play_arrow_24);
        }
    }


    private String covertBangla(String digitString)
    {
        digitString= digitString.replace("0", "০");
        digitString= digitString.replace("1", "১");
        digitString= digitString.replace("2", "২");
        digitString= digitString.replace("3", "৩");
        digitString= digitString.replace("4", "৪");
        digitString= digitString.replace("5", "৫");
        digitString= digitString.replace("6", "৬");
        digitString= digitString.replace("7", "৭");
        digitString= digitString.replace("8", "৮");
        digitString= digitString.replace("9", "৯");
        digitString= digitString.replace(":", "ঃ");
        return digitString;
    }


    private void animations(){

        animation = AnimationUtils.loadAnimation(this,R.anim.bounce);
        animation2 = AnimationUtils.loadAnimation(this,R.anim.bounce);
        animation3 = AnimationUtils.loadAnimation(this,R.anim.bounce);

        Animation animLeft = new TranslateAnimation(-500, 0,0, 0);
        animLeft.setDuration(1000);
        animLeft.setFillAfter(true);
        leftEarBg.startAnimation(animLeft);


        Animation animRight = new TranslateAnimation(500, 0,0, 0);
        animRight.setDuration(1000);
        animRight.setFillAfter(true);
        rightEarBg.startAnimation(animRight);


        Animation animBottom = new TranslateAnimation(0, 0,500, 0);
        animBottom.setDuration(1000);
        animBottom.setFillAfter(true);
        bottomCard.startAnimation(animBottom);

    }


}