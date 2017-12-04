package matt.prepark;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;

/**
 * Created by rafaniyi on 10/7/2017.
 */
public class ConfirmationActivity extends AppCompatActivity {

    private String paymentAmount;
    private Button button;
    private String globalAddress;
    private String day, loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        //Getting Intent
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String email = intent.getStringExtra("email");
        final String address = intent.getStringExtra("address");
        final String time = intent.getStringExtra("time");
        globalAddress = address;

        final Button bCheckTime = (Button) findViewById(R.id.checkTime);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        Intent mapIntent = new Intent(ConfirmationActivity.this, Map.class);
                        mapIntent.putExtra("username", username);
                        mapIntent.putExtra("email", email);
                        ConfirmationActivity.this.startActivity(mapIntent);
                        break;
                    case R.id.profile:
                        Intent i_userprofile = new Intent(ConfirmationActivity.this, userProfile.class);
                        i_userprofile.putExtra("username", username);
                        i_userprofile.putExtra("email", email);
                        startActivity(i_userprofile);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(ConfirmationActivity.this, UserAreaActivity.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("email", email);
                        ConfirmationActivity.this.startActivity(homeIntent);
                        break;
                }
                return true;
            }
        });

        //System.out.println("I GOT: "+intent.getStringExtra("PaymentDetails"));

        JSONObject jsonDetails = null;
        try {

            jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"));
            Notify();
            share();
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        updateSpots("1"); //TODO

        bCheckTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_countdown = new Intent(ConfirmationActivity.this, Countdown.class);
                i_countdown.putExtra("time", time);
                i_countdown.putExtra("address", address);
                startActivity(i_countdown);
            }
        });

//        Intent i_countdown = new Intent(ConfirmationActivity.this, Countdown.class);
//        i_countdown.putExtra("time", time);
//        i_countdown.putExtra("address", address);
//        startActivity(i_countdown);

    }

    public void updateSpots(String lower) {
        Response.Listener<String> response = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //creating a jsonResponse that will receive the php json
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmationActivity.this);
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

        SpotAmountRequest spotAmountRequest = new SpotAmountRequest(globalAddress, lower, response);
        RequestQueue queue = Volley.newRequestQueue(ConfirmationActivity.this);
        queue.add(spotAmountRequest); //TODO
    }

    private void showDetails(JSONObject jsonDetails) throws JSONException {

        paymentAmount = Pay_activity.rate;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        //Views
        TextView textViewId = findViewById(R.id.t1);
        TextView textViewStatus=  findViewById(R.id.t2);
        TextView textViewAmount =  findViewById(R.id.t3);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount.concat(" USD"));

        String Date = dateFormat.format(date);
        day = Date;
        loc = jsonDetails.getString("state");


        new Transaction(Pay_activity.rate,jsonDetails.getString("state"),Date,getApplicationContext()).execute();

        response();


    }

    private void Notify(){
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, transactionHistory.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification n  = new Notification.Builder(this)
                .setContentTitle("Thank you!")
                .setContentText("Your payment of $"+paymentAmount+" has been received")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        notificationManager.notify(0,n);
    }


    private void share(){
        button = findViewById(R.id.share);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, " Yay!! Just rented out a lot on Prepark");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

            }
        });
    }



    public void response() {
            String transaction = "Transaction: " + "Price: " + Pay_activity.rate + " USD"  + " Location: " + loc;
            Transaction.TransactionRequest transactionRequest = new Transaction.TransactionRequest(transaction);
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(transactionRequest);
            System.out.println(transaction);
    }
}

class Transaction extends AsyncTask {

    private String amount;
    private String loc;
    public static String Date;
    private Context context;
    public static String lotOwner;


    Transaction(String amount, String city, String date, Context context) {
        this.amount = amount;
        this.loc =city;
        this.Date = date;
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] objects) {

        String seller = "";
        Response.Listener<String> ResponseListener = response -> {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");
                if (success) {
                    System.out.println("GET :hurray!");
                    lotOwner = jsonResponse.getString("username");
                } else {
                    System.out.println("GET: Sorry!");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        SellerRequest sellerRequest = new SellerRequest(seller, ResponseListener);
        RequestQueue q = Volley.newRequestQueue(this.context);
        q.add(sellerRequest);



        return null;

    }

    static class TransactionRequest extends StringRequest {
        private static final String TRANSACTION_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/transaction.php";
        private java.util.Map<String, String> params;


        TransactionRequest(String transaction) {
            super(Request.Method.POST, TRANSACTION_REQUEST_URL, null, null);

            params = new HashMap<>();
            params.put("buyer", Pay_activity.username);
            params.put("seller",lotOwner);
            params.put("transaction", transaction);
            params.put("date", Transaction.Date);
            System.out.println("Request is fine 1!");
        }


        @Override
        public java.util.Map<String, String> getParams() {
            System.out.println( ":Request is fine 2!");
            return params;
        }
    }


    class Seller extends StringRequest {
        private static final String TRANSACTION_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/SellerPost.php";
        private java.util.Map<String, String> params;
        private String address;


        Seller(String seller, Response.Listener<String> listener) {
            super(Request.Method.POST, TRANSACTION_REQUEST_URL, listener, null);

            params = new HashMap<>();
            params.put("seller", lotOwner);
        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }

}




