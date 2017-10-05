package com.example.rafaniyi.parkapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Pay_activity extends AppCompatActivity {

    public static final String PAYPAL_CLIENT_ID = "AUEI8B-07-XP-_Gjw5MtqWz_mdgIAZNLQfdjOXQL7WHx5oDrvJBwEwsr7X_MLMOjffWDTlOPefK0j3vV";
    public static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config;
    private Button button;
    private Context context;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        Log.i("paymentExample", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", 1));

                    } catch (JSONException e) {
                        Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_activity);
        config = new PayPalConfiguration().environment(
                PayPalConfiguration.ENVIRONMENT_SANDBOX
        ).clientId(PAYPAL_CLIENT_ID);


        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        button = findViewById(R.id.btn_pay);

        context = this;

        button.setOnClickListener(view -> getPayment());


    }

    private void getPayment() {

        String paymentAmount = "1";

        //Creating a paypalpayment
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(paymentAmount)), "USD", "Simplified Coding Fee",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
}

class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm);

        //Getting Intent
        Intent intent = getIntent();


        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = findViewById(R.id.t1);
        TextView textViewStatus=  findViewById(R.id.t2);
        TextView textViewAmount =  findViewById(R.id.t3);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount.concat(" USD"));

        new Transaction(paymentAmount,jsonDetails.getString("state"),"DATE").execute();

    }
}


class Transaction extends AsyncTask{

    private String amount;
    private String loc;
    private String Date;


    Transaction(String amount, String city, String date){
        this.amount = amount;
        this.Date = date;
        this.loc = city;
    }
    @Override
    protected Object doInBackground(Object[] objects) {
        //TODO: Send information to database
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Overrid
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        System.out.println("hurray!");
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Pay_activity.);
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

        //String transaction = Tostring();
        //TransactionRequest transactionRequest = new TransactionRequest(transaction, responseListener);
        //RequestQueue queue = Volley.newRequestQueue(Pay_activity.this);
        //queue.add(transactionRequest);

        return null;
    }

    private String Tostring(){
        return "Transaction: "+"Price: "+this.amount+" USD"+"\n"+"Location: "+this.loc+"\n"+"Date: "+this.Date;
    }
}

class TransactionRequest extends StringRequest {
    private static final String TRANSACTION_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/transaction.php";
    private Map<String, String> params;

    public TransactionRequest(String transaction, Response.Listener<String> listener) {
        super(Method.POST, TRANSACTION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("transaction", transaction);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
