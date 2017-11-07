package matt.prepark;

/**
 * @author JawadMRahman
 */

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class myLots extends AppCompatActivity {
    private void Notify(){

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, Map.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification n  = new Notification.Builder(this)
                .setContentText("Lot updated")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0,n);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lots);
        final EditText etAddress = (EditText) findViewById(R.id.address);
        final EditText spotsAvailable = (EditText) findViewById(R.id.num_available);
        final EditText nextAvail = (EditText) findViewById(R.id.nextavail);
        final EditText zipML = (EditText) findViewById(R.id.zip_num);
        final Button b_submitML = (Button) findViewById(R.id.button_submitmylots);
        final Button b_list = (Button) findViewById(R.id.button_list);

        b_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_list = new Intent(myLots.this, ListOfAddresses.class);
                //i_list.putExtra("username", username);
                startActivity(i_list);
            }
        });



        b_submitML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address = etAddress.getText().toString();
                final String spots = spotsAvailable.getText().toString();
                final String nextTime = nextAvail.getText().toString();
                final String zip = zipML.getText().toString();

                ProgressDialog dialog = new ProgressDialog(myLots.this);
                dialog.setMessage("Updating...");
                dialog.show();
                Notify();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(myLots.this, Map.class); //merge with mitch for this class
                                myLots.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(myLots.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                myLotsRequest request = new myLotsRequest(address, zip, spots, nextTime, responseListener);
                RequestQueue queue = Volley.newRequestQueue(myLots.this);
                queue.add(request);

            }
        });
    }
}
