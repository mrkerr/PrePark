package com.example.rafaniyi.parkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import static spark.Spark.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;

import java.util.HashMap;
import java.util.Map;

import spark.Route;

public class Pay_activity extends AppCompatActivity {

    private final int REQUEST_CODE = 1;
    private final String send_payment_details = "YOUR-API-FOR-PAYMENTS";
    private String  amount;
    public static String token;
    private HashMap<String, String> paramHash;
    private Button btnPay;
    private EditText etAmount;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String stringNonce = nonce.getNonce();
                Log.d("mylog", "Result: " + stringNonce);

                // Send payment price with the nonce
                // use the result to update your UI and send the payment method nonce to your server
                if (!etAmount.getText().toString().isEmpty()) {
                    amount = etAmount.getText().toString();
                    paramHash = new HashMap<>();
                    paramHash.put("amount", amount);
                    paramHash.put("nonce", stringNonce);
                    sendPaymentDetails();
                } else
                    Toast.makeText(Pay_activity.this, "Please enter a valid amount.", Toast.LENGTH_SHORT).show();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Log.d("mylog", "user canceled");
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("mylog", "Error : " + error.toString());
            }
        }
    }

    public void onBraintreeSubmit() {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_activity);
        init_server();

        onBraintreeSubmit();
        new HttpRequest().execute();
    }

    public void init_server() {

        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                "5sqpp7pmzhdhptw9",
                "fdc68cnngqs24b9c",
                "9c483d0f3b361e92014cbf4f81cbe309"
        );

       token = gateway.clientToken().generate();
        String path = "/checkout";

       post(path, new Route() {
           @Override
           public Object handle(spark.Request request, spark.Response response) throws Exception {
               return request.queryParams("payment_method_nonce");
           }
       });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pay_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void sendPaymentDetails() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, send_payment_details,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("Successful"))
                        {
                            Toast.makeText(Pay_activity.this, "Transaction successful", Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(Pay_activity.this, "Transaction failed", Toast.LENGTH_LONG).show();
                        Log.d("mylog", "Final Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }


}

class HttpRequest extends AsyncTask {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Object doInBackground(Object[] objects) {
        HttpClient client = new HttpClient();
        client.get(Pay_activity.token, new HttpResponseCallback() {
            @Override
            public void success(String responseBody) {
                Log.d("mylog", responseBody);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(Pay_activity., "Successfully got token", Toast.LENGTH_SHORT).show();
                       // llHolder.setVisibility(View.VISIBLE);
                    }
                });
                Pay_activity.token = responseBody;
            }

            @Override
            public void failure(Exception exception) {
                final Exception ex = exception;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(MainActivity.this, "Failed to get token: " + ex.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }
}
