package com.talkingandroid.hour5application;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button uiThreadButton;
    Button postButton;
    Button asyncTaskButton;
    Button intentServiceButton;
    TextView resultsTextView;
    DelayReceiver delayReceiver = new DelayReceiver();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultsTextView = (TextView) findViewById(R.id.textView);
        uiThreadButton = (Button) findViewById(R.id.uiThreadButton);
        uiThreadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemClock.sleep(5000);
                resultsTextView.setText("Updated on UI Thread");
            }
        });

        postButton = (Button) findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsTextView.setText("Starting View.post() ");

                new Thread(new Runnable() {
                    public void run() {
                        SystemClock.sleep(5000);
                        resultsTextView.post(new Runnable() {
                            public void run() {
                                resultsTextView.setText("Updated using View.post() ");
                            }
                        });
                    }
                }).start();

            }
        });

        asyncTaskButton = (Button) findViewById(R.id.asyncTaskButton);
        asyncTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DelayTask().execute();
            }
        });


        intentServiceButton = (Button) findViewById(R.id.intentServiceButton);
        intentServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsTextView.setText("Starting Intent Service ");

                Intent delayIntent = new Intent(getApplicationContext(), DelayIntentService.class);
                startService(delayIntent);
            }
        });


    }
    @Override
    protected void onResume (){
        super.onResume();
        registerReceiver(delayReceiver, new IntentFilter(DelayIntentService.ACTION_DELAY));
    }

    protected void onPause (){
        super.onPause();
        unregisterReceiver(delayReceiver);
    }

    class DelayTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            resultsTextView.setText("Starting AsyncTask");
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result==0){
                resultsTextView.setText("Updated via AsyncTask");
            }
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            SystemClock.sleep(5000);
            return 0;

        }
    }

    public class DelayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DelayIntentService.ACTION_DELAY)){
                String message = intent.getExtras().getString(DelayIntentService.EXTRA_MESSAGE);
                resultsTextView.setText( message);
            }
        }
    }

}
