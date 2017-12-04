package matt.prepark;

import android.os.Vibrator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Countdown extends Activity {
    public static int globaltime;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        Intent confIntent = getIntent();
        globaltime = Integer.parseInt(confIntent.getStringExtra("time"));
        Toast.makeText(this, globaltime, Toast.LENGTH_SHORT).show();
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        TextView textView= findViewById(R.id.t1);
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
        Intent toService = new Intent(this, CountdownService.class);
        startService(toService);

    }
    @Override
    protected void onResume() {
        super.onResume();
        TextView textView= findViewById(R.id.t1);
        textView.setText("You have " + CountdownService.toSend + " minutes left");
    }

    @Override
    protected void onPause() {
        super.onPause();

    }



    }






