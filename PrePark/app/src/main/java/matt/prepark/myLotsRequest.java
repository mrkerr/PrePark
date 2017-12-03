package matt.prepark;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jawad44 on 10/9/17.
 */

public class myLotsRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/mylots.php";
    private Map<String, String> params;

    public myLotsRequest(String address, String spots, String time, String username, Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("address", address);
        params.put("spots", spots);
        params.put("time", time);
        params.put("username", username);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
