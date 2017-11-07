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
        String val = intent.getStringExtra("detailA") + "\n" + intent.getStringExtra("detailS") + "\n" + intent.getStringExtra("detailC");
        String spot = intent.getStringExtra("detailSpot");
        String time = intent.getStringExtra("detailTime");
        String rate = intent.getStringExtra("detailRate");
       final String aName = intent.getStringExtra("detailA");
        final String username = intent.getStringExtra("username");
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
            startActivity(i_purchaseScreen);

      });
    }
}
