package com.example.labbayk.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.labbayk.Helper.TinyDB;
import com.example.labbayk.R;
import com.example.labbayk.tracking.Customer;
import com.example.labbayk.tracking.Delivery;
import com.example.labbayk.tracking.DirectionsApiHelper;
import com.example.labbayk.tracking.DirectionsResponse;
import com.example.labbayk.tracking.LocationService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.PolyUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackActivity extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    private DirectionsApiHelper directionsApiHelper;
    Marker marker,marker1;
    LocationBroadcastReceiver receiver;
    FirebaseDatabase db;
    DatabaseReference reference;
    String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_map_activity);

        receiver = new LocationBroadcastReceiver();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                //Req Location Permission
                startLocService();
            }
        } else {
            //Start the Location Service
            startLocService();
        }
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        directionsApiHelper = new DirectionsApiHelper();
    }

    void startLocService() {
        IntentFilter filter = new IntentFilter("ACT_LOC");
        registerReceiver(receiver, filter);
        Intent intent = new Intent(TrackActivity.this, LocationService.class);
        startService(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocService();
                } else {
                    Toast.makeText(this, "Give me permissions", Toast.LENGTH_LONG).show();
                }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngZoom(new LatLng(new TinyDB(this).getDouble("customerLat")
                        , new TinyDB(this).getDouble("customerLong")), 15));
    }

    private void addPolyline(String encodedPolyline) {
        List<LatLng> points = PolyUtil.decode(encodedPolyline);
        PolylineOptions polylineOptions = new PolylineOptions().addAll(points).width(10);
        mMap.addPolyline(polylineOptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Register the BroadcastReceiver
        receiver = new LocationBroadcastReceiver();
        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        registerReceiver(receiver, filter);
    }

    public class LocationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ACT_LOC")) {
                //double lat = intent.getDoubleExtra("latitude", 0f);
                //double longitude = intent.getDoubleExtra("longitude", 0f);


                TinyDB tinyDB = new TinyDB(TrackActivity.this);
                username = tinyDB.getString("customerName");

                double lat = tinyDB.getDouble("customerLat");
                double longitude = tinyDB.getDouble("customerLong");

                Customer customer = new Customer(username,lat,longitude);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Order");
                reference.child(username).setValue(customer);


                reference  = db.getReference("Delivery");
                Query query = reference.orderByChild("name").equalTo(tinyDB.getString("driverName"));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){
                            Delivery delivery = null;
                            for (DataSnapshot issue: snapshot.getChildren()) {
                                delivery = issue.getValue(Delivery.class);
                                break;
                            }
                            double driverLat = delivery.getLatitude();
                            double driverLong = delivery.getLongitude();


                            if (mMap != null) {

                                LatLng customerLatLng = new LatLng(lat, longitude);
                                LatLng driverLatLng = new LatLng(driverLat,driverLong);

                                String origin = driverLat + "," + driverLong;
                                String destination = tinyDB.getString("customerLocation");



                                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(customerLatLng, 15));

                                directionsApiHelper.getDirections(origin, destination, new Callback<DirectionsResponse>() {
                                    @Override
                                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            DirectionsResponse directionsResponse = response.body();
                                            if (!directionsResponse.routes.isEmpty()) {
                                                String encodedPolyline = directionsResponse.routes.get(0).overview_polyline.points;
                                                addPolyline(encodedPolyline);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                                        // Handle failure
                                    }
                                });

                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(customerLatLng);
                                if (marker != null)
                                    marker.setPosition(customerLatLng);
                                else
                                    marker = mMap.addMarker(markerOptions);

                                MarkerOptions markerOptions1 = new MarkerOptions();
                                markerOptions1.position(driverLatLng);
                                if (marker1 != null)
                                    marker1.setPosition(driverLatLng);
                                else
                                    marker1 = mMap.addMarker(markerOptions1.icon(BitmapFromVector(getApplicationContext(),R.drawable.car)));
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                            }
                            //Toast.makeText(MainActivity.this, "Me", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        }
    }
    private BitmapDescriptor
    BitmapFromVector(Context context, int vectorResId)
    {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(
                context, vectorResId);

        // below line is use to set bounds to our vector
        // drawable.
        vectorDrawable.setBounds(
                0, 0, vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(
                vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our
        // bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
