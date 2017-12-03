package matt.prepark;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.widget.TextView;

public class Countdown extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final TextView textView= findViewById(R.id.t1);
        setContentView(R.layout.activity_countdown);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int timeLeft = intent.getIntExtra("timeSent", 0);
                        if(timeLeft>0) {
                            textView.setText("You have " + timeLeft + " minutes left");
                        }
                        else{
                            textView.setText("Y'all outta time, see ya again soon!");
                        }
                    }
                }, new IntentFilter(CountdownService.ACTION_LOCATION_BROADCAST)
        );
    }
    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(this, CountdownService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(new Intent(this, CountdownService.class));
    }


    }






