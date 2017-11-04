package matt.prepark;

/**
 * @author JawadMRahman
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        b_setuplots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_setuplots = new Intent(userProfile.this, setupLots.class);
                startActivity(i_setuplots);
            }
        });
        b_mylots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_mylots = new Intent(userProfile.this, myLots.class);
                startActivity(i_mylots);
            }
        });
        b_transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transactionIntent = new Intent(userProfile.this, TransactionActivity.class);
                userProfile.this.startActivity(transactionIntent);
            }
        });
    }
}