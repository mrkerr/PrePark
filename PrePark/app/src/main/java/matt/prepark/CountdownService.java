package matt.prepark;

import android.app.AlertDialog;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class CountdownService extends Service{
    public static int toSend=0;
   public int time;
    public static final String
            ACTION_LOCATION_BROADCAST = CountdownService.class.getName() + "LocationBroadcast";
    public final String timeFromCD = Countdown.time;
    public final String address = Countdown.address;

    @Override
    public void onCreate() {
    super.onCreate();
        time = Integer.parseInt(timeFromCD);
        time = time*60000;
        new CountDownTimer(time, 5000) {
            public void onTick(long millisUntilFinished) {
                int timeLeftInt = (int) Math.ceil((double) millisUntilFinished / 60000);    //Whole number of minutes left, ceiling
                if(timeLeftInt == 5){
                    Notify("Not Done");
                }

            }

            public void onFinish() {
                Notify("done");

                Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //creating a jsonResponse that will receive the php json
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CountdownService.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                SpotAmountRequest spotAmountRequest = new SpotAmountRequest(address, "0", response);
                RequestQueue queue = Volley.newRequestQueue(CountdownService.this);
                queue.add(spotAmountRequest);

            }
        }.start();

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