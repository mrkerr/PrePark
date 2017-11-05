package matt.prepark;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ListHelper extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_helper);
        setTitle("Lot Info");
        Intent intent = getIntent();
        String val = intent.getStringExtra("detail");
        TextView tv = (TextView) findViewById(R.id.textView3);
        tv.setText(val);
    }
}
