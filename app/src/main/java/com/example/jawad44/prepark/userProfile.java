package com.example.jawad44.prepark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class userProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final Button setuplots = (Button) findViewById(R.id.button_setuplots);
        final Button mylots = (Button) findViewById(R.id.button_mylots);
        final Button transactions = (Button) findViewById(R.id.button_transactions);

    }
}
