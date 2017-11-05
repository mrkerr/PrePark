package matt.prepark;

/**
 * @author JawadMRahman
 */

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

public class myLots extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lots);
        final EditText etAddress = (EditText) findViewById(R.id.address);
        final EditText spotsAvailable = (EditText) findViewById(R.id.num_available);
        final EditText nextAvail = (EditText) findViewById(R.id.nextavail);
        final EditText zipML = (EditText) findViewById(R.id.zip_num);

        final Button b_submitML = (Button) findViewById(R.id.button_submitmylots);

        b_submitML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address = etAddress.getText().toString();
                final String spots = spotsAvailable.getText().toString();
                final String nextTime = nextAvail.getText().toString();
                final String zip = zipML.getText().toString();


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

//                LotRequest myLotsRequest = new LotRequest(address, zip, spots, nextTime, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(myLots.this);
//                queue.add(myLotsRequest);


            }
        });
    }
}
