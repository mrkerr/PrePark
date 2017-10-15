package matt.prepark;

import android.os.Bundle;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        list.add("Mitch");
        list.add("Matt");
        list.add("Jawad");
        list.add("Ridwan");
        list.add("Iowa State");
        list.add("ECpE");
        list.add("Mitch");
        list.add("Matt");
        list.add("Jawad");
        list.add("Ridwan");
        list.add("Iowa State");
        list.add("ECpE");
        list.add("Mitch");
        list.add("Matt");
        list.add("Jawad");
        list.add("Ridwan");
        list.add("Iowa State");
        list.add("ECpE");

        adapter = new ArrayAdapter(ListOfLots.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);



    }

}
