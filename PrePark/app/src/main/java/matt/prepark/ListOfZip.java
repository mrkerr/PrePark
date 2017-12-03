package matt.prepark;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListOfZip extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_addresses);

        ListView listView = findViewById(R.id.listview);
        List list = new ArrayList();
        EditText filter = (EditText) findViewById(R.id.searchFilter);
      // String[] abc = {"jawad", "matt", "mitch"};
        Intent intent = getIntent();
        ArrayList<String> address = intent.getStringArrayListExtra("addressList");
 //       Log.d("jawad", address.toString());
//        for(int i=0; i<address.size();i++)
//        {
//            list.add(address.get(i));
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, address);
        getListView().setAdapter(adapter);
       //filter stuff
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
