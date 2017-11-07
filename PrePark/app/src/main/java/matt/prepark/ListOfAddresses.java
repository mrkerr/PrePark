package matt.prepark;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ListOfAddresses extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_addresses);

        String[] lotsIown = {"42 Walby way, sydney", "10880 Malibu Point, CA", "167H University Village, Ames", "Wayne Manor, Gotham City", };
        //get the list of address, city, state
        //somehow create an intent from the data base
        //// addressList.get
        ///cityList.get
        //stateList.get
        //zipList.get

        //keep adding
    //    for(int i=0; i<list.size();i++)
        {
            //list.add()
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), android.R.layout.simple_list_item_1, lotsIown);
        getListView().setAdapter(adapter);

    }
}
