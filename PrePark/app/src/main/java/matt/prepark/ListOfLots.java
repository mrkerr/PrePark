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

        String[] listofLots = {"Iowa State University, Ames IA", "1305 Georgia Avenue Ames, IA", "4800 Mortensen Rd Ames IA", "Jack Trice Stadium Ames, IA"
        + "123 Main Street Ames, IA", "Iowa State University, Ames IA", "1305 Georgia Avenue Ames, IA", "4800 Mortensen Rd Ames IA", "Jack Trice Stadium Ames, IA"
                + " 123 Main Street Ames, IA", "Iowa State University, Ames IA", "1305 Georgia Avenue Ames, IA", "4800 Mortensen Rd Ames IA", "Jack Trice Stadium Ames, IA"
                + " 123 Main Street Ames, IA", "Iowa State University, Ames IA", "1305 Georgia Avenue Ames, IA", "4800 Mortensen Rd Ames IA", "Jack Trice Stadium Ames, IA"
                + " 123 Main Street Ames, IA", "Iowa State University, Ames IA", "1305 Georgia Avenue Ames, IA", "4800 Mortensen Rd Ames IA", "Jack Trice Stadium Ames, IA"
                + " 123 Main Street Ames, IA"};

        for(int i = 0; i < listofLots.length; i++){
            list.add(listofLots[i]);
        }


        adapter = new ArrayAdapter(ListOfLots.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);



    }

}
