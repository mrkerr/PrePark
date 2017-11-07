package matt.prepark;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.*;

/**
 * Created by mattlawlor on 10/7/17.
 */

public class MapRequest extends StringRequest {
    private static final String MAP_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/mapRequest.php";
    private java.util.Map<String, String> params;

    public MapRequest(String address, String city, String state, String spots, String time, String rate, Response.Listener<String> listener) {
        super(Request.Method.POST, MAP_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("address", address);
        params.put("city", city);
        params.put("state", state);
        params.put("spots", spots);
        params.put("time", time);
        params.put("rate", rate);
    }

    @Override
    public java.util.Map<String, String> getParams() {
        return params;
    }
}
