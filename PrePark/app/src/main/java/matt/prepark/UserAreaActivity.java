package matt.prepark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by mattlawlor on 9/21/17.
 */

public class UserAreaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        final Button bMap = (Button) findViewById(R.id.bMaps);
        final Button b_userProfile = (Button) findViewById(R.id.bProfile);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);

        // Display user details
        etUsername.setText(username);
        etEmail.setText(email);

        bMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(UserAreaActivity.this, Map.class);
                UserAreaActivity.this.startActivity(mapIntent);
            }
        });

        b_userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_userprofile = new Intent(UserAreaActivity.this, userProfile.class);
                startActivity(i_userprofile);
            }
        });
    }
}
