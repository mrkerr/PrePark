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
import java.util.Scanner;


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
    ArrayList<String> mapPoints = new ArrayList<>();
    String [] test = new String[2];
    boolean isReady = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final String address = "";
        String city = "";
        String state = "";


        while (true) {

            // Response received from the server
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonResponse = new JSONArray(response);
                        JSONObject successIndex = jsonResponse.getJSONObject(0);

                        boolean success = successIndex.getBoolean("success");
                        if (success) {

                            String addressBlock = jsonResponse.getString(1);
                            String stateBlock = jsonResponse.getString(2);
                            String cityBlock = jsonResponse.getString(3);

                            ArrayList<String> addressList = new ArrayList<>();
                            ArrayList<String> cityList = new ArrayList<>();
                            ArrayList<String> stateList = new ArrayList<>();

                            String[] addressHead = addressBlock.split(":");
                            String[] addressBody = addressHead[1].split(",");
                            String[] stateHead = stateBlock.split(":");
                            String[] stateBody = stateHead[1].split(",");
                            String[] cityHead = cityBlock.split(":");
                            String[] cityBody = cityHead[1].split(",");


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
                            addMarker(addressList, cityList, stateList);


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

            MapRequest mapRequest = new MapRequest(address, city, state, responseListener);
            RequestQueue queue = Volley.newRequestQueue(Map.this);
            queue.add(mapRequest);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void addMarker(ArrayList<String> address, ArrayList<String> city, ArrayList<String> state) {
        LatLng latLng2;
        MarkerOptions markerOptions2;
        String test = address.get(2) + " " + state.get(2) + " " + city.get(2);
        Toast.makeText(this, test, Toast.LENGTH_LONG).show();

                try {
                    lotMarker = geocoder.getFromLocationName(test, 1).get(0);
                    //Place marker for lot, change to for loop in future when >1 lot utilized
                    latLng2 = new LatLng(lotMarker.getLatitude(), lotMarker.getLongitude());
                    markerOptions2 = new MarkerOptions();
                    markerOptions2.position(latLng2);
                    markerOptions2.title(test);
                    markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    mCurrLocationMarker = mMap.addMarker(markerOptions2);
                    mCurrLocationMarker.showInfoWindow();
                } catch (IOException e) {
                    e.printStackTrace();
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
    public boolean checkLocationPermission(){
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

}
