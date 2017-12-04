package matt.prepark;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.util.Locale;
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
    ArrayList<String> globalAddress = new ArrayList<>();    //For storing addresses added to server after map open
    ArrayList<String> globalCity = new ArrayList<>();       //For storing cities added to server after map open
    ArrayList<String> globalState = new ArrayList<>();      //For storing states added to server after map open
    String username2 = "";
    ArrayList<String> globalSpots = new ArrayList<>();
    ArrayList<String> globalTime = new ArrayList<>();
    ArrayList<String> globalRate = new ArrayList<>();
    String globalEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        final Button listView = (Button) findViewById(R.id.button1);    //Represents List View button
        //final Button countdown = (Button) findViewById(R.id.button8);


        Intent intent = getIntent();   //Get intent from hub
        final String username = intent.getStringExtra("username");  //Get username from hub
        final String email = intent.getStringExtra("email");
        username2 = username;   //Store username in global
        final String address = intent.getStringExtra("address");
        globalEmail = email;


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
                i_ListView.putExtra("email", email);
                i_ListView.putStringArrayListExtra("addressList", globalAddress);
                i_ListView.putStringArrayListExtra("cityList", globalCity);
                i_ListView.putStringArrayListExtra("stateList", globalState);
                i_ListView.putStringArrayListExtra("spotsList", globalSpots);
                i_ListView.putStringArrayListExtra("timeList", globalTime);
                i_ListView.putStringArrayListExtra("rateList", globalRate);
                startActivity(i_ListView);
            }
        });


        //Set timer to run function every 10 seconds to check for new lots to be added to map
        new Timer().scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() { getAddress(true); }
            } , 0, 10000);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map:
                        Intent mapIntent = new Intent(Map.this, Map.class);
                        mapIntent.putExtra("username", username);
                        mapIntent.putExtra("email", email);
                        Map.this.startActivity(mapIntent);
                        break;
                    case R.id.profile:
                        Intent i_userprofile = new Intent(Map.this, userProfile.class);
                        i_userprofile.putExtra("username", username);
                        i_userprofile.putExtra("email", email);
                        startActivity(i_userprofile);
                        break;
                    case R.id.home:
                        Intent homeIntent = new Intent(Map.this, UserAreaActivity.class);
                        homeIntent.putExtra("username", username);
                        homeIntent.putExtra("email", email);
                        Map.this.startActivity(homeIntent);
                        break;
                }
                return true;
            }
        });

    }


/*
*
* This method adds a marker at every address obtained from the database
 */
    public void addMarker(ArrayList<String> address, ArrayList<String> city, ArrayList<String> state, ArrayList<String> spots, ArrayList<String> time, ArrayList<String> rate) {
        LatLng latLng2; //For tracking LatLng data from address
        MarkerOptions markerOptions2;   //For displaying marker
        String[] plotPoint = new String[address.size() + 1];    //Add 1 index for storing dummy address
        String[] pointInfo = new String[address.size() + 1];
        //Add every address, city, and state to array
        for (int i = 0; i < address.size(); i++) {
            plotPoint[i] = address.get(i) + " " + state.get(i) + " " + city.get(i);
            pointInfo[i] = "Spots: " + spots.get(i) + " | Time: " + time.get(i) + " minutes | Rate: $"  + rate.get(i);
        }

        plotPoint[plotPoint.length - 1] = "Iowa State University Ames Iowa";    //dummy address
        pointInfo[plotPoint.length -1] = "Spots: 20 | Time: 5 | Rate: $3";
        //Adding marker for every address in array
        for (int j = 0; j < plotPoint.length; j++) {
            try {
                lotMarker = geocoder.getFromLocationName(plotPoint[j], 1).get(0);   //Return one Address object for every address
                latLng2 = new LatLng(lotMarker.getLatitude(), lotMarker.getLongitude());    //Get LatLng from address
                markerOptions2 = new MarkerOptions();
                markerOptions2.position(latLng2);
                markerOptions2.title(plotPoint[j]);
                markerOptions2.snippet(pointInfo[j]);
                markerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                mCurrLocationMarker = mMap.addMarker(markerOptions2);   //add marker
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

        //Overriding method to change size of info window
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;    //On return null, calls getInfoContents
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context context = getApplicationContext();

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                String distanceTo = getDistance(marker.getPosition(), mCurrLocationMarker.getPosition());
                title.setText(marker.getTitle() + "\n" + distanceTo + "\n" + "Click to purchase spot ");

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }

        });

        //Sending intent to Pay_Activity on window click
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String spotTimeRate = marker.getSnippet();  //Get formatted string
                String[] parts = spotTimeRate.split("\\|");   //Split at "|" marker

                String spotTemp = parts[0];                 //Get first part
                String spot = spotTemp.substring(7, spotTemp.length());

                String timeTemp = parts[1];                 //Get second part
                String time = timeTemp.substring(7, timeTemp.length()-9);

                String rateTemp = parts[2];                 //Get third part
                String rate = rateTemp.substring(8, rateTemp.length());

                final String username3 = username2;
                Intent intent2 = new Intent(Map.this, Pay_activity.class);
                String fixedAddres = marker.getTitle().replace("Ames Iowa", "");
                intent2.putExtra("address", fixedAddres);
                intent2.putExtra("username", username3);
                intent2.putExtra("spot", spot);
                intent2.putExtra("time", time);
                intent2.putExtra("rate", rate);
                intent2.putExtra("email", globalEmail);
                startActivity(intent2);
            }
        });

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
        String spots = "";
        String time = "";
        String rate = "";


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
                        String spotsBlock = jsonResponse.getString(4);
                        String timeBlock = jsonResponse.getString(5);
                        String rateBlock = jsonResponse.getString(6);

                        //creating arraylists to store these individual variables
                        ArrayList<String> addressList = new ArrayList<>();
                        ArrayList<String> cityList = new ArrayList<>();
                        ArrayList<String> stateList = new ArrayList<>();
                        ArrayList<String> spotsList = new ArrayList<>();
                        ArrayList<String> timeList = new ArrayList<>();
                        ArrayList<String> rateList = new ArrayList<>();

                        //the head of these "blocks" are not needed so I split the strings
                        //then split the body so we now have an array with just the variables
                        //but they have some extra characters we don't want
                        String[] addressHead = addressBlock.split(":");
                        String[] addressBody = addressHead[1].split(",");
                        String[] stateHead = stateBlock.split(":");
                        String[] stateBody = stateHead[1].split(",");
                        String[] cityHead = cityBlock.split(":");
                        String[] cityBody = cityHead[1].split(",");

                        String[] spotsHead = spotsBlock.split(":");
                        String[] spotsBody = spotsHead[1].split(",");
                        String[] timeHead = timeBlock.split(":");
                        String[] timeBody = timeHead[1].split(",");
                        String[] rateHead = rateBlock.split(":");
                        String[] rateBody = rateHead[1].split(",");

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

                            spotsBody[i] = spotsBody[i].replace("[", "");
                            spotsBody[i] = spotsBody[i].replace("]", "");
                            spotsBody[i] = spotsBody[i].replaceAll("^\"|\"$", "");

                            timeBody[i] = timeBody[i].replace("[", "");
                            timeBody[i] = timeBody[i].replace("]", "");
                            timeBody[i] = timeBody[i].replaceAll("^\"|\"$", "");

                            rateBody[i] = rateBody[i].replace("[", "");
                            rateBody[i] = rateBody[i].replace("]", "");
                            rateBody[i] = rateBody[i].replaceAll("^\"|\"$", "");

                            addressList.add(addressBody[i]);
                            cityList.add(cityBody[i]);
                            stateList.add(stateBody[i]);

                            spotsList.add(spotsBody[i]);
                            timeList.add(timeBody[i]);
                            rateList.add(rateBody[i]);
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

                        String spotsEnd = spotsList.get(spotsList.size() - 1);
                        spotsList.remove(spotsList.size() - 1);
                        spotsEnd = spotsEnd.substring(0, spotsEnd.length() - 2);
                        spotsList.add(spotsEnd);

                        String timeEnd = timeList.get(timeList.size() - 1);
                        timeList.remove(timeList.size() - 1);
                        timeEnd = timeEnd.substring(0, timeEnd.length() - 2);
                        timeList.add(timeEnd);

                        String rateEnd = rateList.get(rateList.size() - 1);
                        rateList.remove(rateList.size() - 1);
                        rateEnd = rateEnd.substring(0, rateEnd.length() - 2);
                        rateList.add(rateEnd);

                        if (needsUpdate == false) {
                            //the arraylists are now all "clean" and are able to be plotted
                            addMarker(addressList, cityList, stateList, spotsList, timeList, rateList);
                            globalAddress = addressList;
                            globalCity = cityList;
                            globalState = stateList;

                            globalSpots = spotsList;
                            globalTime = timeList;
                            globalRate = rateList;

                        } else {
                            if (addressList.size() > globalAddress.size()) {
                                ArrayList<String> newAddressList = new ArrayList<>();
                                ArrayList<String> newCityList = new ArrayList<>();
                                ArrayList<String> newStateList = new ArrayList<>();

                                ArrayList<String> newSpotsList = new ArrayList<>();
                                ArrayList<String> newTimeList = new ArrayList<>();
                                ArrayList<String> newRateList = new ArrayList<>();
                                for (int i = globalAddress.size(); i < addressList.size(); i++) {
                                    newAddressList.add(addressList.get(i));
                                    newCityList.add(cityList.get(i));
                                    newStateList.add(stateList.get(i));

                                    newSpotsList.add(spotsList.get(i));
                                    newTimeList.add(timeList.get(i));
                                    newRateList.add(rateList.get(i));
                                }
                                addMarker(newAddressList, newCityList, newStateList, newSpotsList, newTimeList, newRateList);
                                globalAddress = newAddressList;
                                globalCity = newCityList;
                                globalState = newStateList;

                                globalSpots = newSpotsList;
                                globalTime = newTimeList;
                                globalRate = newRateList;
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
        MapRequest mapRequest = new MapRequest(address, city, state, spots, time, rate, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Map.this);
        queue.add(mapRequest);
    }
    public String getDistance(LatLng from, LatLng to){
        try{
            Location malLoc = new Location("");
            malLoc.setLatitude(from.latitude);
            malLoc.setLongitude(from.longitude);

            Location userLoc = new Location("");
            userLoc.setLatitude(to.latitude);
            userLoc.setLongitude(to.longitude);

            double distance = malLoc.distanceTo(userLoc) / 1000;
            distance = distance * 0.62137;
            return "You are " + String.format(Locale.getDefault(), "%.2f", distance) + " miles away from this lot";

        } catch(Exception e){
            return "Unknown distance";
        }
    }

}
