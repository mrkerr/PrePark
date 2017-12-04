package matt.prepark;

/**
 * @author JawadMRahman
 */

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class userProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//comment for git
        setContentView(R.layout.activity_user_profile);

        final Button b_setuplots = (Button) findViewById(R.id.button_setuplots);
        final Button b_mylots = (Button) findViewById(R.id.button_mylots);
        final Button b_transactions = (Button) findViewById(R.id.button_transactions);
        final Button b_searchParking = (Button) findViewById(R.id.button_searchparking);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String email = intent.getStringExtra("email");

        b_setuplots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_setuplots = new Intent(userProfile.this, setupLots.class);
                i_setuplots.putExtra("username", username);
                i_setuplots.putExtra("email", email);
                startActivity(i_setuplots);
            }
        });
        b_mylots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_mylots = new Intent(userProfile.this, myLots.class);
                i_mylots.putExtra("username", username);
                i_mylots.putExtra("email", email);
                startActivity(i_mylots);
            }
        });
        b_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transactionIntent = new Intent(userProfile.this, transactionHistory.class);
                transactionIntent.putExtra("username", username);
                transactionIntent.putExtra("email", email);
                userProfile.this.startActivity(transactionIntent);
		}
	});
        b_searchParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_buyspot = new Intent(userProfile.this, BuySpot.class);
                i_buyspot.putExtra("username", username);
                i_buyspot.putExtra("email", email);
                startActivity(i_buyspot);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        Intent mapIntent = new Intent(userProfile.this, Map.class);
                        mapIntent.putExtra("username", username);
                        mapIntent.putExtra("email", email);
                        userProfile.this.startActivity(mapIntent);
                        break;
                    case R.id.profile:
                        Intent i_userprofile = new Intent(userProfile.this, userProfile.class);
                        i_userprofile.putExtra("username", username);
                        i_userprofile.putExtra("email", email);
                        startActivity(i_userprofile);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(userProfile.this, UserAreaActivity.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("email", email);
                        userProfile.this.startActivity(homeIntent);
                        break;
                }
                return true;
            }
        });
    }
}
