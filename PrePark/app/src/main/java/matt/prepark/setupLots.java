package matt.prepark;

/**
 * @author JawadMRahman
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.sql.Time;

public class setupLots extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_lots);
        final Button b_submitSL = (Button) findViewById(R.id.button_submit_setuplots);
        final Button b_saveSL = (Button) findViewById(R.id.button_save_setuplots);
        final Switch s_contact = (Switch) findViewById(R.id.contactme_setuplots);
        final Switch s_overnight = (Switch) findViewById(R.id.overnight_setuplots);

        final EditText et_addressSL = (EditText) findViewById(R.id.address_setuplots);
        final EditText et_citySL = (EditText) findViewById(R.id.city_setuplots);
        final EditText et_stateSL = (EditText) findViewById(R.id.statesetuplots);
        final EditText et_zipSL = (EditText) findViewById(R.id.zip_setuplots);   //TODO digit
        final EditText spotsSL = (EditText) findViewById(R.id.num_spots_setuplots); //TODO time
        final EditText maxtimeSL = (EditText) findViewById(R.id.time_maxtime_setuplots); //TODO Time
        final EditText rateSL = (EditText) findViewById(R.id.rate_setuplots);   //TODO digit

        b_submitSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
        b_saveSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });
        s_contact.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //TODO
            }
        });
        s_overnight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //TODO
            }
        });
    }





}