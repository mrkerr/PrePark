package com.example.jawad44.prepark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class myLots extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lots);
        final EditText etAddress = (EditText) findViewById(R.id.address);
        final EditText spotsAvailable = (EditText) findViewById(R.id.num_available);
        final EditText nextAvail = (EditText) findViewById(R.id.nextavail);

        final Button b_submitML = (Button) findViewById(R.id.button_submitmylots);

        b_submitML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
