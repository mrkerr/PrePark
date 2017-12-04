package matt.prepark;

/**
 * @author JawadMRahman
 */

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
//import java.util.Map;

public class setupLots extends AppCompatActivity {
   //private String address = null;
    private void Notify(){

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, Map.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification n  = new Notification.Builder(this)
                .setContentTitle("Congratulations!")
                .setContentText("Your parking lot has been created")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0,n);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_lots);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String email = intent.getStringExtra("email");

        final Button b_submitSL = (Button) findViewById(R.id.button_submit_setuplots);
        final EditText et_addressSL = (EditText) findViewById(R.id.address_setuplots);
        final EditText et_citySL = (EditText) findViewById(R.id.city_setuplots);
        final EditText et_stateSL = (EditText) findViewById(R.id.statesetuplots);
        final EditText et_zipSL = (EditText) findViewById(R.id.zip_setuplots);   //TODO digit
        final EditText spotsSL = (EditText) findViewById(R.id.num_spots_setuplots); //TODO time
        final EditText maxtimeSL = (EditText) findViewById(R.id.time_maxtime_setuplots); //TODO Time
        final EditText rateSL = (EditText) findViewById(R.id.rate_setuplots);   //TODO digit

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        Intent mapIntent = new Intent(setupLots.this, Map.class);
                        mapIntent.putExtra("username", username);
                        mapIntent.putExtra("email", email);
                        setupLots.this.startActivity(mapIntent);
                        break;
                    case R.id.profile:
                        Intent i_userprofile = new Intent(setupLots.this, userProfile.class);
                        i_userprofile.putExtra("username", username);
                        i_userprofile.putExtra("email", email);
                        startActivity(i_userprofile);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(setupLots.this, UserAreaActivity.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("email", email);
                        setupLots.this.startActivity(homeIntent);
                        break;
                }
                return true;
            }
        });



        b_submitSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address = et_addressSL.getText().toString();
                final String city = et_citySL.getText().toString();
                final String state = et_stateSL.getText().toString();
                final String zip = et_zipSL.getText().toString();
                final String spots = spotsSL.getText().toString();
                final String time = maxtimeSL.getText().toString();
                final String rate = rateSL.getText().toString();
                Notify();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(setupLots.this, Map.class); //merge with mitch for this class
                                intent.putExtra("username", username);
                                intent.putExtra("email", email);
                                setupLots.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(setupLots.this);
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

                LotRequest lotRequest = new LotRequest(username, address, city, state, zip, spots, time, rate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(setupLots.this);
                queue.add(lotRequest);
            }
        });
    }
}


