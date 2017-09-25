package com.example.jawad44.prepark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class myLots extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lots);
        final EditText etAddress = (EditText) findViewById(R.id.etAddress);
        final EditText spotsAvailable = (EditText) findViewById(R.id.etspotAvailable);
        final EditText nextAvail = (EditText) findViewById(R.id.availabletime);

        final Button submitML = (Button) findViewById(R.id.button_submit);

    }
}
