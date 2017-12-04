package matt.prepark;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by rafaniyi on 10/9/2017.
 */

public class transactionHistory extends AppCompatActivity {
    public static ArrayList<String> arr;
    public static String string;
    private TextView textView;
    private ListView listView;
    private String empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);

        empty = "NO TRANSACTION HAS BEEN RECORDED";

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        arr = new ArrayList<>();

        Button b = findViewById(R.id.bt1);
        Button c = findViewById(R.id.bt2);
        Button d = findViewById(R.id.bt3);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        Intent mapIntent = new Intent(transactionHistory.this, matt.prepark.Map.class);
                        mapIntent.putExtra("username", username);
                        mapIntent.putExtra("email", email);
                        transactionHistory.this.startActivity(mapIntent);
                        break;
                    case R.id.profile:
                        Intent i_userprofile = new Intent(transactionHistory.this, userProfile.class);
                        i_userprofile.putExtra("username", username);
                        i_userprofile.putExtra("email", email);
                        startActivity(i_userprofile);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(transactionHistory.this, UserAreaActivity.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("email", email);
                        transactionHistory.this.startActivity(homeIntent);
                        break;
                }
                return true;
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHistory(username, 2);
                arr.clear();
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHistory(username, 3);
            }
        });

        if (arr.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, arr);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                }
            });

        } else {
            //textView.setText(empty);
                getHistory(username, 3);
                arr.clear();
            }
    }


    public void getHistory(String username, int timeLine) {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //response is stored in an json array, usually it has been a json object
                    JSONArray jsonResponse = new JSONArray(response);
                    //because of json array, we need to cut it into json objects
                    //here we are checking to see if the query was successful
                    JSONObject successIndex = jsonResponse.getJSONObject(0);
                    Toast.makeText(transactionHistory.this, response, Toast.LENGTH_SHORT).show();
                    //storing the boolean in our own variable
                    boolean success = successIndex.getBoolean("success");
                    if (success) {
                        //getting string "blocks" from the json array
                        //the next process is formatting the strings to get the values I want
                        String usernameBlock = jsonResponse.getString(1);
                        String transactionBlock = jsonResponse.getString(2);
                        String dateBlock = jsonResponse.getString(3);

                        //creating arraylists to store these individual variables
                        ArrayList<String> usernameList = new ArrayList<>();
                        ArrayList<String> transactionList = new ArrayList<>();
                        ArrayList<String> dateList = new ArrayList<>();

                        //the head of these "blocks" are not needed so I split the strings
                        //then split the body so we now have an array with just the variables
                        //but they have some extra characters we don't want
                        String[] usernameHead = usernameBlock.split(":");
                        String[] usernameBody = usernameHead[1].split(",");
                        String[] transactionHead = transactionBlock.split(":");
                        String[] transactionBody = transactionHead[1].split(",");
                        String[] dateHead = dateBlock.split(":");
                        String[] dateBody = dateHead[1].split(",");

                        //we take these extra characters out and store them
                        //into our arraylists
                        for (int i = 0; i < usernameBody.length; i++) {
                            usernameBody[i] = usernameBody[i].replace("[", "");
                            usernameBody[i] = usernameBody[i].replace("]", "");
                            usernameBody[i] = usernameBody[i].replaceAll("^\"|\"$", "");
                            transactionBody[i] = transactionBody[i].replace("[", "");
                            transactionBody[i] = transactionBody[i].replace("]", "");
                            transactionBody[i] = transactionBody[i].replaceAll("^\"|\"$", "");
                            dateBody[i] = dateBody[i].replace("[", "");
                            dateBody[i] = dateBody[i].replace("]", "");
                            dateBody[i] = dateBody[i].replaceAll("^\"|\"$", "");
                            usernameList.add(usernameBody[i]);
                            transactionList.add(transactionBody[i]);
                            dateList.add(dateBody[i]);
                        }

                        //the last element in our arraylist doesn't have these
                        //extra characters removed. There are extra characters
                        //at the last two indices of the string
                        //this section removes the last two elements
                        String usernameEnd = usernameList.get(usernameList.size() - 1);
                        usernameList.remove(usernameList.size() - 1);
                        usernameEnd = usernameEnd.substring(0, usernameEnd.length() - 2);
                        usernameList.add(usernameEnd);

                        String transactionEnd = transactionList.get(transactionList.size() - 1);
                        transactionList.remove(transactionList.size() - 1);
                        transactionEnd = transactionEnd.substring(0, transactionEnd.length() - 2);
                        transactionList.add(transactionEnd);

                        String dateEnd = dateList.get(dateList.size() - 1);
                        dateList.remove(dateList.size() - 1);
                        dateEnd = dateEnd.substring(0, dateEnd.length() - 2);
                        dateList.add(dateEnd);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };

        if (timeLine == 2) {
            ReadRequest readRequest = new ReadRequest(username, "12", "2", responseListener);
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(readRequest);
        }

        if (timeLine == 3) {
            ReadRequest readRequest = new ReadRequest(username, "2017", "3", responseListener);
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(readRequest);
        }

    }
}

class ReadRequest extends StringRequest {
    private static final String TRANSACTION_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/read.php";
    private Map<String, String> params;

    ReadRequest(String username, String date, String when, Response.Listener<String> listener) {
        super(Request.Method.POST, TRANSACTION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("date", date);
        params.put("when", when);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


class getTransaction extends FragmentActivity {

    public getTransaction() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_transaction);
    }

}
