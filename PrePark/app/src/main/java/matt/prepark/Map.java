package matt.prepark;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;



public class Map extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    Geocoder geocoder;  //for decoding addresses into LatLng
    Address lotMarker;    //For storing addresses retrieved from geocoder
    ArrayList<String> globalAddress = new ArrayList<>();
    ArrayList<String> globalCity = new ArrayList<>();
    ArrayList<String> globalState = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final Button listView = (Button) findViewById(R.id.button1);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getAddress(false);


        //Sending an intent to ListOfLots
        listView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i_ListView = new Intent(Map.this, ListOfLots.class);
                i_ListView.putExtra("username", username);
                i_ListView.putStringArrayListExtra("addressList", globalAddress);
                i_ListView.putStringArrayListExtra("cityList", globalCity);
                i_ListView.putStringArrayListExtra("stateList", globalState);
                startActivity(i_ListView);
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() { getAddress(true); }
            } , 0, 10000);

    }



    public void addMarker(ArrayList<String> address, ArrayList<String> city, ArrayList<String> state) {
        LatLng latLng2;
        MarkerOptions markerOptions2;
        String[] plotPoint = new String[address.size() + 1];

        for (int i = 0; i < address.size(); i++) {
            plotPoint[i] = address.get(i) + " " + state.get(i) + " " + city.get(i);
        }

        plotPoint[plotPoint.length - 1] = "Iowa State University Ames Iowa";

        for (int j = 0; j < plotPoint.length; j++) {
            try {
                lotMarker = geocoder.getFromLocationName(plotPoint[j], 1).get(0);
                //Place marker for lot, change to for loop in future when >1 lot utilized
                latLng2 = new LatLng(lotMarker.getLatitude(), lotMarker.getLongitude());
                markerOptions2 = new MarkerOptions();
                markerOptions2.position(latLng2);
                markerOptions2.title(plotPoint[j]);
                markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mCurrLocationMarker = mMap.addMarker(markerOptions2);
                mCurrLocationMarker.showInfoWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }





    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        geocoder = new Geocoder(this);


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        mCurrLocationMarker = mMap.addMarker(markerOptions);


        //move map camera
        float zoomLevel = 16.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }


        }
    }

    public void getAddress(boolean needsUpdate) {
        final String address = "";
        String city = "";
        String state = "";


        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //response is stored in an json array, usually it has been a json object
                    JSONArray jsonResponse = new JSONArray(response);
                    //because of json array, we need to cut it into json objects
                    //here we are checking to see if the query was successful
                    JSONObject successIndex = jsonResponse.getJSONObject(0);

                    //storing the boolean in our own variable
                    boolean success = successIndex.getBoolean("success");
                    if (success) {

                        //getting string "blocks" from the json array
                        //the next process is formatting the strings to get the values I want
                        String addressBlock = jsonResponse.getString(1);
                        String stateBlock = jsonResponse.getString(2);
                        String cityBlock = jsonResponse.getString(3);

                        //creating arraylists to store these individual variables
                        ArrayList<String> addressList = new ArrayList<>();
                        ArrayList<String> cityList = new ArrayList<>();
                        ArrayList<String> stateList = new ArrayList<>();

                        //the head of these "blocks" are not needed so I split the strings
                        //then split the body so we now have an array with just the variables
                        //but they have some extra characters we don't want
                        String[] addressHead = addressBlock.split(":");
                        String[] addressBody = addressHead[1].split(",");
                        String[] stateHead = stateBlock.split(":");
                        String[] stateBody = stateHead[1].split(",");
                        String[] cityHead = cityBlock.split(":");
                        String[] cityBody = cityHead[1].split(",");

                        //we take these extra characters out and store them
                        //into our arraylists
                        for (int i = 0; i < addressBody.length; i++) {
                            addressBody[i] = addressBody[i].replace("[", "");
                            addressBody[i] = addressBody[i].replace("]", "");
                            addressBody[i] = addressBody[i].replaceAll("^\"|\"$", "");
                            cityBody[i] = cityBody[i].replace("[", "");
                            cityBody[i] = cityBody[i].replace("]", "");
                            cityBody[i] = cityBody[i].replaceAll("^\"|\"$", "");
                            stateBody[i] = stateBody[i].replace("[", "");
                            stateBody[i] = stateBody[i].replace("]", "");
                            stateBody[i] = stateBody[i].replaceAll("^\"|\"$", "");
                            addressList.add(addressBody[i]);
                            cityList.add(cityBody[i]);
                            stateList.add(stateBody[i]);
                        }

                        //the last element in our arraylist doesn't have these
                        //extra characters removed. There are extra characters
                        //at the last two indices of the string
                        //this section removes the last two elements
                        String addressEnd = addressList.get(addressList.size() - 1);
                        addressList.remove(addressList.size() - 1);
                        addressEnd = addressEnd.substring(0, addressEnd.length() - 2);
                        addressList.add(addressEnd);

                        String cityEnd = cityList.get(cityList.size() - 1);
                        cityList.remove(cityList.size() - 1);
                        cityEnd = cityEnd.substring(0, cityEnd.length() - 2);
                        cityList.add(cityEnd);

                        String stateEnd = stateList.get(stateList.size() - 1);
                        stateList.remove(stateList.size() - 1);
                        stateEnd = stateEnd.substring(0, stateEnd.length() - 2);
                        stateList.add(stateEnd);

                        if (needsUpdate == false) {
                            //the arraylists are now all "clean" and are able to be plotted
                            addMarker(addressList, cityList, stateList);
                            globalAddress = addressList;
                            globalCity = cityList;
                            globalState = stateList;
                        } else {
                            if (addressList.size() > globalAddress.size()) {
                                ArrayList<String> newAddressList = new ArrayList<>();
                                ArrayList<String> newCityList = new ArrayList<>();
                                ArrayList<String> newStateList = new ArrayList<>();
                                for (int i = globalAddress.size(); i < addressList.size(); i++) {
                                    newAddressList.add(addressList.get(i));
                                    newCityList.add(cityList.get(i));
                                    newStateList.add(stateList.get(i));
                                }
                                addMarker(newAddressList, newCityList, newStateList);
                                globalAddress = newAddressList;
                                globalCity = newCityList;
                                globalState = newStateList;
                            }
                        }

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Map.this);
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //sending a request to the server with address, city, state and responseListener
        MapRequest mapRequest = new MapRequest(address, city, state, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Map.this);
        queue.add(mapRequest);
    }

}
