package matt.prepark;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mattlawlor on 10/2/17.
 */

public class EmailRequest extends StringRequest {
    //url for server to find the correct php file
    private static final String EMAIL_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/email.php";
    private Map<String, String> params;

    public EmailRequest(String email, Response.Listener<String> listener) {
        super(Method.POST, EMAIL_REQUEST_URL, listener, null);
        //storing the values given to us made in EmailActivity to be used in the database
        params = new HashMap<>();
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
