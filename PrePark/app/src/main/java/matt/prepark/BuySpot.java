package matt.prepark;

import android.app.AlertDialog;
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

public class BuySpot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_spot);

        final Button b_findParkingBS = (Button) findViewById(R.id.FindParkingBS);

        final EditText zipBS = (EditText) findViewById(R.id.address_BS);
        final EditText lincenseplateBS = (EditText) findViewById(R.id.LicensePlateBS);
        final EditText fromBS = (EditText) findViewById(R.id.FromTimeBS);
        final EditText toBS = (EditText) findViewById(R.id.ToTimeBS);

        b_findParkingBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String zip = zipBS.getText().toString();
                final String licensePlate = lincenseplateBS.getText().toString();
                final String fromTime = fromBS.getText().toString();
                final String toTime = toBS.getText().toString();



                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(BuySpot.this, Map.class); //merge with mitch for this class
                                BuySpot.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(BuySpot.this);
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

                BuySpotRequest bsRequest = new BuySpotRequest(zip, licensePlate, fromTime, toTime, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuySpot.this);
                queue.add(bsRequest;


            }
        });

    }
}
