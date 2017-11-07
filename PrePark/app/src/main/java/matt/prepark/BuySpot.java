package matt.prepark;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.ListActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuySpot extends AppCompatActivity {
    ArrayList<String> gAddress = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_spot);

        final Button b_findParkingBS = (Button) findViewById(R.id.FindParkingBS);

        final EditText zipBS = (EditText) findViewById(R.id.address_BS);
        //final EditText lincenseplateBS = (EditText) findViewById(R.id.LicensePlateBS);
        //final EditText fromBS = (EditText) findViewById(R.id.FromTimeBS);
        //final EditText toBS = (EditText) findViewById(R.id.ToTimeBS);

        b_findParkingBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String zip = zipBS.getText().toString();
                //final String licensePlate = lincenseplateBS.getText().toString();
               // final String fromTime = fromBS.getText().toString();
               // final String toTime = toBS.getText().toString();
                //progress dialog
                ProgressDialog dialog = new ProgressDialog(BuySpot.this);
                dialog.setTitle("Please wait");
                dialog.setMessage("Finding the right options for you...");
                dialog.show();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonresponse = new JSONArray(response);
                            JSONObject successIndex = new JSONObject(response);
                            boolean success = successIndex.getBoolean("success");
                            if (success) {
                                //getting string "blocks" from the json array
                                //the next process is formatting the strings to get the values I want
                                String addressBlock = jsonresponse.getString(1);


                                //creating arraylists to store these individual variables
                                ArrayList<String> addressList = new ArrayList<>();

                                //the head of these "blocks" are not needed so I split the strings
                                //then split the body so we now have an array with just the variables
                                //but they have some extra characters we don't want
                                String[] addressHead = addressBlock.split(":");
                                String[] addressBody = addressHead[1].split(",");

                                //we take these extra characters out and store them
                                //into our arraylists
                                for (int i = 0; i < addressBody.length; i++) {
                                    addressBody[i] = addressBody[i].replace("[", "");
                                    addressBody[i] = addressBody[i].replace("]", "");
                                    addressBody[i] = addressBody[i].replaceAll("^\"|\"$", "");

                                    addressList.add(addressBody[i]);
                                }

                                //the last element in our arraylist doesn't have these
                                //extra characters removed. There are extra characters
                                //at the last two indices of the string
                                //this section removes the last two elements
                                String addressEnd = addressList.get(addressList.size() - 1);
                                addressList.remove(addressList.size() - 1);
                                addressEnd = addressEnd.substring(0, addressEnd.length() - 2);
                                addressList.add(addressEnd);


//                                Intent intent = new Intent(BuySpot.this, Map.class); //merge with mitch for this class
//                                BuySpot.this.startActivity(intent);
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

                BuySpotRequest bsRequest = new BuySpotRequest(zip, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuySpot.this);
                queue.add(bsRequest);

                Intent i_listZ = new Intent(BuySpot.this, ListOfZip.class);
                //i_listZ.putExtra("username", username);
                i_listZ.putStringArrayListExtra("addressList", )    //TODO
                startActivity(i_listZ);

            }
        });

    }
}
