package matt.prepark;

/**
 * Created by jawad44 on 12/3/17.
 */
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import static android.content.Context.CONNECTIVITY_SERVICE;
public class networkReceiver {

    Context context;

    public networkReceiver(Context context)
    {
        this.context = context;
    }

    public boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if (cm != null)
        {
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            if(nInfo != null)
            {
                if(nInfo.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
