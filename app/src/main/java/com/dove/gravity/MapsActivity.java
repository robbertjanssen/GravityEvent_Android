package com.dove.gravity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity
        implements LocationListener,
        OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    LatLng myLocation = new LatLng(-34, 151);
    Marker myLocMarker = null;

    LinearLayout weekLayout;
    Button btn_calender ;
    Button btn_publicEarth;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        weekLayout = (LinearLayout) findViewById(R.id.lm1);
        weekLayout.setVisibility(View.GONE);

        btn_calender = (Button)findViewById(R.id.btn_calender);
        btn_publicEarth = (Button)findViewById(R.id.btn_public_earth);
        weekLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekLayout.setVisibility(LinearLayout.INVISIBLE);
                btn_calender.setVisibility(View.VISIBLE);
            }
        });
        btn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekLayout.setVisibility(LinearLayout.VISIBLE);
                btn_calender.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);


        ///////////////////  Get Current Location   ////////////////////
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (myLocMarker != null) myLocMarker.remove();
                myLocMarker = mMap.addMarker(new MarkerOptions().position(myLocation).title("James Strive"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 8.0f));
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        LatLng newlocation = latLng;

        String addressString = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            StringBuilder sb = new StringBuilder();
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                if (address.getSubLocality() != null) {
                    sb.append(address.getSubLocality()).append(", ");
                }
                sb.append(address.getLocality()).append(", ");
                sb.append(address.getCountryName());
            }
            addressString = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }


        mMap.addMarker(new MarkerOptions().position(newlocation).title(addressString.toString()));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        }

        Log.e("TAG", "" + location.getLatitude() + " : " + location.getLongitude());
        myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if (myLocMarker != null) myLocMarker.remove();
        myLocMarker = mMap.addMarker(new MarkerOptions().position(myLocation).title("James Strive"));

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(2000);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
