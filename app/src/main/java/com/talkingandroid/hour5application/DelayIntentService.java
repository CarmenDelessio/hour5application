package com.talkingandroid.hour5application;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;


public class DelayIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    public static final String ACTION_DELAY = "com.talkingandroid.hour5application.action.DELAY";
    public static final String EXTRA_MESSAGE = "com.talkingandroid.hour5application.extra.MESSAGE";

        public DelayIntentService() {
            super("DelayIntentService");
        }
        @Override
        protected void onHandleIntent(Intent intent) {
            SystemClock.sleep(5000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(ACTION_DELAY);
            broadcastIntent.putExtra(EXTRA_MESSAGE, "UPDATE:  USING INTENT SERVICE");
            sendBroadcast(broadcastIntent);
        }

}
