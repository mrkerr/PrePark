package matt.prepark;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;



public class CountdownService extends Service{
    public static final String
            ACTION_LOCATION_BROADCAST = CountdownService.class.getName() + "LocationBroadcast";

    @Override
    public void onCreate() {
    super.onCreate();

        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                sendBroadcastMessage((int) Math.ceil((double) millisUntilFinished / 1000));
            }

            public void onFinish() {
                sendBroadcastMessage(0);
            }
        }.start();

    }

    private void sendBroadcastMessage(int timeSent) {
            Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
            intent.putExtra("timeSent", timeSent);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}