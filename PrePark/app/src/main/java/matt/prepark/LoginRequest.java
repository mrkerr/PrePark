package matt.prepark;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mattlawlor on 9/21/17.
 */

public class LoginRequest extends StringRequest {
    //url for server to find the correct php file
    private static final String LOGIN_REQUEST_URL = "http://proj-309-sb-b-2.cs.iastate.edu/login.php";
    private Map<String, String> params;

    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        //storing the values given to us made in LoginActivity to be used in the database
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
