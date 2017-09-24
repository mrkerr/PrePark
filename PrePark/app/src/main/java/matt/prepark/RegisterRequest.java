package matt.prepark;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mattlawlor on 9/21/17.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "proj-309-sb-b-2.cs.iastate.edu";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}