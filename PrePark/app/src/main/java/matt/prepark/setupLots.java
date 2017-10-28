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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

public class setupLots extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_lots);
        final Button b_submitSL = (Button) findViewById(R.id.button_submit_setuplots);
        final Button b_saveSL = (Button) findViewById(R.id.button_save_setuplots);
        final Switch s_contact = (Switch) findViewById(R.id.contactme_setuplots);
        final Switch s_overnight = (Switch) findViewById(R.id.overnight_setuplots);

        final EditText et_addressSL = (EditText) findViewById(R.id.address_setuplots);
        final EditText et_citySL = (EditText) findViewById(R.id.city_setuplots);
        final EditText et_stateSL = (EditText) findViewById(R.id.statesetuplots);
        final EditText et_zipSL = (EditText) findViewById(R.id.zip_setuplots);   //TODO digit
        final EditText spotsSL = (EditText) findViewById(R.id.num_spots_setuplots); //TODO time
        final EditText maxtimeSL = (EditText) findViewById(R.id.time_maxtime_setuplots); //TODO Time
        final EditText rateSL = (EditText) findViewById(R.id.rate_setuplots);   //TODO digit

        b_saveSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
        s_contact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //TODO
            }
        });
        s_overnight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //TODO
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


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(setupLots.this, Map.class); //merge with mitch for this class
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

                LotRequest lotRequest = new LotRequest(address, city, state, zip, spots, time, rate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(setupLots.this);
                queue.add(lotRequest);

            }
        });
    }
}