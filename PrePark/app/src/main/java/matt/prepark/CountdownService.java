package matt.prepark;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;


public class CountdownService extends Service{
    public static int toSend = 0;
    public static final String
            ACTION_LOCATION_BROADCAST = CountdownService.class.getName() + "LocationBroadcast";

    @Override
    public void onCreate() {
    super.onCreate();
        int time = Countdown.globaltime;
        time = time*60000;
        new CountDownTimer(time, 60000) {
            public void onTick(long millisUntilFinished) {
                int timeLeftInt = (int) Math.ceil((double) millisUntilFinished / 60000);    //Whole number of minutes left, ceiling
                sendBroadcastMessage(timeLeftInt);
                toSend = timeLeftInt;
                if(timeLeftInt == 5){
                    Notify("Not Done");
                }

            }

            public void onFinish() {
                sendBroadcastMessage(0);
                Notify("done");

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


    private void Notify(String doneness){

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, Map.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
        if(doneness.equals("done")) {
            Notification n = new Notification.Builder(this)
                    .setContentTitle("Time to leave!")
                    .setContentText("Your PrePark spot has expired, time to go home!")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(0, n);
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(1000);
        }

        else{
            Notification n = new Notification.Builder(this)
                    .setContentTitle("Ya got 5 minutes left in your PrePark spot!")
                    .setContentText("Better get going soon here")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(0, n);
        }
    }


}