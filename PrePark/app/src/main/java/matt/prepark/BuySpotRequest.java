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


        private static final String REGISTER_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/lotsetup.php";
        private Map<String, String> params;

        public BuySpotRequest(String zip, String fromTime, String toTime, Response.Listener<String> listener) {
            super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null);
            params = new HashMap<>();
            //params.put("address", licensePlate);
            params.put("zip", zip);
//            params.put("username", username);
//            params.put("password", password);
//            params.put("email", email);

        }

        @Override
        public Map<String, String> getParams() {
            return params;
        }
    }
