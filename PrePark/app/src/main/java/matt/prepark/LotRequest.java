package matt.prepark;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jawad44 on 10/6/17.
 */

public class LotRequest extends StringRequest {
    //url for server to find the correct php file
    private static final String LOT_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/lotsetup.php";
    private Map<String, String> params;

    public LotRequest(String username, String address, String city, String state, String zip, String spots, String time, String rate, Response.Listener<String> listener) {
        super(Request.Method.POST, LOT_REQUEST_URL, listener, null);
        //storing the values given to us made in myLots to be used in the database
        params = new HashMap<>();
        params.put("username", username);
        params.put("address", address);
        params.put("city", city);
        params.put("state", state);
        params.put("zip", zip);
        params.put("spots", spots);
        params.put("time", time);
        params.put("rate", rate);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
