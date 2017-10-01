package com.example.jawad44.prepark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class setupLots extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_lots);
    }
    final Button b_submitSL = (Button) findViewById(R.id.button_setuplots);
    final Button b_saveSL = (Button) findViewById(R.id.button_save_setuplots);



}
