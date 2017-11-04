package matt.prepark;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rafaniyi on 10/9/2017.
 */

public class transactionHistory extends AppCompatActivity {
    public static ArrayList<String> arr;
    public static String string;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);

        String empty = "NO TRANSACTION HAS BEEN RECORDED";

        arr = new ArrayList<>();

        new req(getApplicationContext()).execute();


        TextView textView = findViewById(R.id.trans);
        ListView listView = findViewById(R.id.listview);

        if(arr.size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),R.layout.support_simple_spinner_dropdown_item,arr);
            listView.setAdapter(adapter);

          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

              }
          });

        }

        else
            textView.setText(empty);

    }
}

class request extends StringRequest {
    private static final String TRANSACTION_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/read.php";
    private Map<String, String> params;

    request( Response.Listener<String> listener) {
        super(Request.Method.POST, TRANSACTION_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("buyer",);
        params.put("seller",);
        params.put("transaction");
        params.put("date",);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

class req extends AsyncTask{

    private Context context;

    req(Context context){
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        Response.Listener<String>responseListener = response ->{
        try {
            JSONObject jsonResponse = new JSONObject(response);
            boolean success = jsonResponse.getBoolean("success");
            if (success) {
                System.out.println("hurray!");
                transactionHistory.arr.add(response);
            } else {
                System.out.println("Sorry!");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    };


        RequestQueue queue = Volley.newRequestQueue(this.context);
        queue.add(new request(responseListener));
        return null;
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