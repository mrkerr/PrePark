package matt.prepark;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final Button bSend = (Button) findViewById(R.id.bSend);

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //storing value given into a variable
                final String email = etEmail.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //creating a jsonResponse that will receive the php json
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                //php was successful with the query and we now will change to the LoginActivity
                                Intent intent = new Intent(EmailActivity.this, LoginActivity.class);
                                EmailActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EmailActivity.this);
                                builder.setMessage("Failed TO Send Email")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                //sending a request to the server with email variable and responseListener
                EmailRequest emailRequest = new EmailRequest(email, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EmailActivity.this);
                queue.add(emailRequest);
            }
        });
    }
}
