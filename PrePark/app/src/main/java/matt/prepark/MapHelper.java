package matt.prepark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MapHelper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_helper);
        final Button purchaseSpot = findViewById(R.id.purchaseButton2);
        setTitle("Lot Info");

        Intent intent = getIntent();
        String val = intent.getStringExtra("address") + "\n" + intent.getStringExtra("distance");
        final String aName = intent.getStringExtra("address");
        final String username = intent.getStringExtra("username");
        TextView tv = findViewById(R.id.textView4);
        tv.setText(val);

        //Sending an intent to ListOfLots
        purchaseSpot.setOnClickListener(view -> {
            Intent i_purchaseScreen = new Intent(MapHelper.this, Pay_activity.class);
            i_purchaseScreen.putExtra("address", aName);
            i_purchaseScreen.putExtra("username", username);
            startActivity(i_purchaseScreen);

        });
    }
}
