package matt.prepark;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListOfZip extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_addresses);

        ListView listView = findViewById(R.id.listview);
        List list = new ArrayList();

        Intent intent = getIntent();
        ArrayList<String> address = intent.getStringArrayListExtra("addressList");

        for(int i=0; i<address.size();i++)
        {
            list.add(address.get(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, list);
        getListView().setAdapter(adapter);

    }
}
