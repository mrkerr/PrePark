package matt.prepark;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class ListHelper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_helper);
        final Button purchaseSpot = findViewById(R.id.purchaseButton);
        setTitle("Lot Info");
        Intent intent = getIntent();

        final String aName = intent.getStringExtra("detailA");
        String rate = intent.getStringExtra("detailRate");

        String val = "Address: " + aName + "\n" + "City: " + intent.getStringExtra("detailS") + ", " + intent.getStringExtra("detailC")
                + "\n" + "Rate: $" + rate;

        String spot = intent.getStringExtra("detailSpot");
        String time = intent.getStringExtra("detailTime");
        final String username = intent.getStringExtra("username");
        final String email = intent.getStringExtra("email");

        TextView tv = findViewById(R.id.textView3);
        tv.setText(val);

        //Sending an intent to ListOfLots
        purchaseSpot.setOnClickListener(view -> {
            Intent i_purchaseScreen = new Intent(ListHelper.this, Pay_activity.class);
            i_purchaseScreen.putExtra( "address", aName);
            i_purchaseScreen.putExtra("username", username);
            i_purchaseScreen.putExtra("spot", spot);
            i_purchaseScreen.putExtra("time", time);
            i_purchaseScreen.putExtra("rate", rate);
            i_purchaseScreen.putExtra("email", email);
            startActivity(i_purchaseScreen);

      });
    }
}
