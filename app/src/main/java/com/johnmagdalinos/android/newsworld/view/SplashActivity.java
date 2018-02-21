package com.johnmagdalinos.android.newsworld.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.johnmagdalinos.android.newsworld.utilities.Constants;
import com.johnmagdalinos.android.newsworld.utilities.SectionsService;

/**
 * Launcher Activity. Displays a splash screen while loading the News Sections.
 * It then launches the main activity to which it passes the loaded Sections.
 */

public class SplashActivity extends AppCompatActivity {
    /** Member variables */
    private SectionsBroadcastReceiver mBroadcastReceiver;
    private IntentFilter mSectionsIntentFilter;
    // TODO: SaveInstanceState???

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup the BroadcastReceiver and its IntentFilter
        mSectionsIntentFilter = new IntentFilter(Constants.SECTIONS_BROADCAST_ACTION);
        mBroadcastReceiver = new SectionsBroadcastReceiver();

        // Start the IntentService to get the Sections
        Intent serviceIntent = new Intent(SplashActivity.this, SectionsService.class);
        startService(serviceIntent);
    }


    /** Register the receiver */
    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver, mSectionsIntentFilter);
    }

    /** Unregister the receiver */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }

    /** Custom Broadcast listening to the results from the SectionsService */
    public class SectionsBroadcastReceiver extends BroadcastReceiver {
        /** Empty constructor */
        private SectionsBroadcastReceiver(){}

        @Override
        public void onReceive(Context context, Intent intent) {

            // Start the MainActivity and pass the Sections ArrayList
            Intent activityIntent = new Intent(SplashActivity.this, MainActivity.class);
            activityIntent.putParcelableArrayListExtra(Constants.KEY_SECTION, intent
                    .getParcelableArrayListExtra(Constants.KEY_SECTIONS_BROADCAST_EXTRAS));
            startActivity(activityIntent);

//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            finish();
        }
    }
}
