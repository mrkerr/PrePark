package matt.prepark;

import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mattlawlor on 9/21/17.
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://proj-309-sb-b-2.cs.iastate.edu/home/mlawlor/register.php";
    //https://10.25.68.190/home/mlawlor/register.php
    //https://129.186.252.159/home/mlawlor
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String password, String email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}