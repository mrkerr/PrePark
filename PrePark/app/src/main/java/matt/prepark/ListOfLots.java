package matt.prepark;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListOfLots extends AppCompatActivity {

    ListView listView;
    List list = new ArrayList();
    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_lots);
        listView = findViewById(R.id.list_view);


        Intent MapIntent = getIntent();
        ArrayList<String> aList = MapIntent.getStringArrayListExtra("addressList");
        ArrayList<String> cList = MapIntent.getStringArrayListExtra("cityList");
        ArrayList<String> sList = MapIntent.getStringArrayListExtra("stateList");
        ArrayList<String> spotList = MapIntent.getStringArrayListExtra("spotsList");
        ArrayList<String> timeList = MapIntent.getStringArrayListExtra("timeList");
        ArrayList<String> rateList = MapIntent.getStringArrayListExtra("rateList");
        final String username = MapIntent.getStringExtra("username");
        final String email = MapIntent.getStringExtra("email");

     ;



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ListHelper.class);
                intent.putExtra("detailA", aList.get(i));
                intent.putExtra("detailC", cList.get(i));
                intent.putExtra("detailS", sList.get(i));
                intent.putExtra("detailSpot", spotList.get(i));
                intent.putExtra("detailTime", timeList.get(i));
                intent.putExtra("detailRate", rateList.get(i));
                intent.putExtra("username", username);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });


        for(int i = 0; i < aList.size(); i++){
            list.add("Address: " + aList.get(i) + "\n" + "City: " + sList.get(i) + ", " + cList.get(i) + "\n" + "Spots: " + spotList.get(i) + "\n"
                    + "Time: " + timeList.get(i) + "\n" + "Rate: $" + rateList.get(i));
        }

        adapter = new ArrayAdapter(ListOfLots.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);





    }

}
