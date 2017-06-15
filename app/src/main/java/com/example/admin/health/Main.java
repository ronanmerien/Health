package com.example.admin.health;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Main extends AppCompatActivity {

    ProgressBar bar;
    private final String PROGRESS = "PROGRESS";

    //Handler qui récupère la modification de la barre de progression
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = msg.getData().getInt(PROGRESS);
            bar.incrementProgressBy(progress);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setMax(100);
    }

    @Override
    public void onStart() {
        super.onStart();
        bar.setProgress(0);

        Thread thread = new Thread(new Runnable() {

            Bundle messageBundle = new Bundle();
            Message message;

            //Boucle de progression de la barre
            public void run() {
                try {
                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(300);
                        message = handler.obtainMessage();
                        messageBundle.putInt(PROGRESS, i);
                        message.setData(messageBundle);
                        handler.sendMessage(message);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });

        //Lancement du Thread
        thread.start();
    }
}