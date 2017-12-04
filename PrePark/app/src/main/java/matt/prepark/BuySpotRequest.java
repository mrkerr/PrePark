package matt.prepark;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jawad44 on 10/30/17.
 */

public class BuySpotRequest extends StringRequest{


    private static final String REGISTER_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/buyspotlist.php";   //change
    private Map<String, String> params;

        public BuySpotRequest(String zip, Response.Listener<String> listener) {
            super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            params.put("zip", zip);

    }


    @Override
    public Map<String, String> getParams() {
        return params;

    }
}

