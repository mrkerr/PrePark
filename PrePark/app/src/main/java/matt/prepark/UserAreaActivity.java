package matt.prepark;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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
//        final Button bMap = (Button) findViewById(R.id.bMaps);
//        final Button b_userProfile = (Button) findViewById(R.id.bProfile);


        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String email = intent.getStringExtra("email");

        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);

        // Display user details
        etUsername.setText(username);
        etEmail.setText(email);

//        bMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent mapIntent = new Intent(UserAreaActivity.this, Map.class);
//                mapIntent.putExtra("username", username);
//                UserAreaActivity.this.startActivity(mapIntent);
//            }
//        });
//
//        b_userProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i_userprofile = new Intent(UserAreaActivity.this, userProfile.class);
//                i_userprofile.putExtra("username", username);
//                startActivity(i_userprofile);
//            }
//        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        Intent mapIntent = new Intent(UserAreaActivity.this, Map.class);
                        mapIntent.putExtra("username", username);
                        mapIntent.putExtra("email", email);
                        UserAreaActivity.this.startActivity(mapIntent);
                        break;
                    case R.id.profile:
                        Intent i_userprofile = new Intent(UserAreaActivity.this, userProfile.class);
                        i_userprofile.putExtra("username", username);
                        i_userprofile.putExtra("email", email);
                        startActivity(i_userprofile);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(UserAreaActivity.this, UserAreaActivity.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("email", email);
                        UserAreaActivity.this.startActivity(homeIntent);
                        break;
                }
                return true;
            }
        });

    }
}
