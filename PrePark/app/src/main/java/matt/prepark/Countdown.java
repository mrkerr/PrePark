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
    public static String time;
    public static String address;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        Intent confIntent = getIntent();
        time = confIntent.getStringExtra("time");
        address = confIntent.getStringExtra("address");
        Intent toService = new Intent(this, CountdownService.class);
        startService(toService);
        TextView tv = findViewById(R.id.t1);
        tv.setText("You time of " + time + " minutes has begun");

    }
    @Override
    protected void onResume() {
        super.onResume();
        TextView tv = findViewById(R.id.t1);
        tv.setText("You time of " + time + " minutes has begun");
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, CountdownService.class));
    }


}






