package com.example.labbayk.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labbayk.Adapter.CartAdapter;
import com.example.labbayk.ApiService;
import com.example.labbayk.Domain.Driver;
import com.example.labbayk.Domain.Item;
import com.example.labbayk.Domain.Order;
import com.example.labbayk.Helper.ChangeNumberItemsListener;
import com.example.labbayk.Helper.ManagmentCart;
import com.example.labbayk.Helper.TinyDB;
import com.example.labbayk.R;
import com.example.labbayk.RetrofitClient;
import com.example.labbayk.databinding.ActivityCartBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagmentCart managmentCart;
    double delivery,total,itemTotal;
    ArrayList<Item> items;
    Retrofit retrofit = RetrofitClient.getClient("https://192.168.1.102:45455/");
    ApiService apiService = retrofit.create(ApiService.class);


    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        managmentCart = new ManagmentCart(this);
        initDriver();
        calculateCart();
        initList();
        setVariable();
    }

    private void initList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.cartView.setVisibility(View.GONE);
        }
        else{
            binding.emptyTxt.setVisibility(View.GONE);
            binding.cartView.setVisibility(View.VISIBLE);
        }
        items = managmentCart.getListCart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void change() {
                calculateCart();
            }
        });
        binding.cardView.setAdapter(adapter);
    }

    private void calculateCart() {
        delivery = 2;
        total = Math.ceil((managmentCart.getTotalFee()+delivery)*100)/100;
        itemTotal = Math.ceil((managmentCart.getTotalFee())*100)/100;
        binding.subtotalTxt.setText(itemTotal+"$");
        binding.deliveryTxt.setText(delivery+"$");
        binding.totalTxt.setText(total+"$");
    }

    private void initDriver() {


        Call<ArrayList<Driver>> call = apiService.getAllDrivers();
        call.enqueue(new Callback<ArrayList<Driver>>() {
            @Override
            public void onResponse(Call<ArrayList<Driver>> call, Response<ArrayList<Driver>> response) {
                ArrayList<Driver> drivers = response.body();
                ArrayAdapter<Driver> adapter = new ArrayAdapter<>(CartActivity.this, R.layout.sp_item, drivers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.driversp.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Driver>> call, Throwable throwable) {

            }
        });
    }

    private void setVariable() {


        binding.backBtn.setOnClickListener(view -> finish());


        binding.placeOrder.setOnClickListener(view -> {

            String cn = binding.urname.getText().toString();
            String dn = binding.driversp.getSelectedItem().toString();
            Order order = new Order(Double.valueOf(binding.totalTxt.getText().toString().substring(0,binding.totalTxt.getText().toString().length()-1)),
                binding.urname.getText().toString(),binding.urphone.getText().toString());

            Call<String> call = apiService.createOrder(order,items,binding.driversp.getSelectedItem().toString());


            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable throwable) {

                }
            });

            managmentCart.clearCart();
            TinyDB tinyDB = new TinyDB(CartActivity.this);
            tinyDB.putListObject("MyOrder",items);
            tinyDB.putString("customerName", cn);
            tinyDB.putString("driverName",dn);
            tinyDB.putDouble("delivery",delivery);
            tinyDB.putDouble("total",total);
            tinyDB.putDouble("itemTotal",itemTotal);
            Intent intent = new Intent(CartActivity.this,MainActivity.class);
            startActivity(intent);
        });

        binding.getLocation.setOnClickListener(view -> {
            startActivity(new Intent(CartActivity.this,MapActivity.class));
            binding.myLocation.setText(new TinyDB(CartActivity.this).getString("customerLocation"));
        });
    }


//    @SuppressLint("MissingPermission")
//    private void getLastLocation() {
//        // check if permissions are given
//        if (checkPermissions()) {
//
//            // check if location is enabled
//            if (isLocationEnabled()) {
//
//                // getting last
//                // location from
//                // FusedLocationClient
//                // object
//                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        Location location = task.getResult();
//                        if (location == null) {
//                            requestNewLocationData();
//                        } else {
//                            binding.myLocation.setText(location.getLatitude() + "," + location.getLongitude());
//                            new TinyDB(CartActivity.this).putString("customerLocation",location.getLatitude() + "," + location.getLongitude());
//                            new TinyDB(CartActivity.this).putDouble("customerLat",location.getLatitude());
//                            new TinyDB(CartActivity.this).putDouble("customerLong",location.getLongitude());
//                        }
//                    }
//                });
//            } else {
//                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
//        } else {
//            // if permissions aren't available,
//            // request for permissions
//            requestPermissions();
//        }
//    }

//    @SuppressLint("MissingPermission")
//    private void requestNewLocationData() {
//
//        // Initializing LocationRequest
//        // object with appropriate methods
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(5);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//
//        // setting LocationRequest
//        // on FusedLocationClient
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//    }
//
//    private LocationCallback mLocationCallback = new LocationCallback() {
//
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
//            binding.myLocation.setText(mLastLocation.getLatitude() + "," + mLastLocation.getLongitude());
//            new TinyDB(CartActivity.this).putString("customerLocation",mLastLocation.getLatitude() + "," + mLastLocation.getLongitude());
//            new TinyDB(CartActivity.this).putDouble("customerLat",mLastLocation.getLatitude());
//            new TinyDB(CartActivity.this).putDouble("customerLong",mLastLocation.getLongitude());
//        }
//    };
//
//    // method to check for permissions
//    private boolean checkPermissions() {
//        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//
//        // If we want background location
//        // on Android 10.0 and higher,
//        // use:
//        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
//    }
//
//    // method to request for permissions
//    private void requestPermissions() {
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
//    }
//
//    // method to check
//    // if location is enabled
//    private boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//
//    // If everything is alright then
//    @Override
//    public void
//    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == PERMISSION_ID) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLastLocation();
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (checkPermissions()) {
//            getLastLocation();
//        }
//    }
}