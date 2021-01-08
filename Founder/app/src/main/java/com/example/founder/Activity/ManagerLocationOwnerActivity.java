package com.example.founder.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.founder.Interfaces.RecyclerViewClick;
import com.example.founder.Public.Public_func;
import com.example.founder.R;
import com.example.founder.adapter.AdapterListStore;
import com.example.founder.adapter.MyInfoWindowAdapter;
import com.example.founder.animation.Animation;
import com.example.founder.model.InforStore;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ManagerLocationOwnerActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerClickListener,
        RecyclerViewClick {

    String TAG = "MapsActivity";

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean permissionDenied = false;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    RecyclerView rvListStore;
    ImageButton btnOpenListStore;
    Boolean flag = true;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;
    private ImageButton imgMnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_location_owner);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        drawerLayout = findViewById(R.id.activity_main_drawer);
        rvListStore = findViewById(R.id.rvListStore);
        imgMnu = findViewById(R.id.btnMnu);
        navigationView = findViewById(R.id.navDrawerMenu);
        openMenu();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.it1:
                        Public_func.clickItemMenu(ManagerLocationOwnerActivity.this, TongDoanhThuActivity.class);
                        return true;
                    case R.id.danh_sach_cua_hang:
                        Public_func.clickItemMenu(ManagerLocationOwnerActivity.this, ListCuaHangActivity.class);
                        return true;
                    case R.id.map:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.tao_tai_khoan_owner:
                        Public_func.clickItemMenu(ManagerLocationOwnerActivity.this, ThemTaiKhoanKhuVucActivity.class);
                        return true;
                    case R.id.thong_bao:
                        Public_func.clickItemMenu(ManagerLocationOwnerActivity.this, ChooseChatActivity.class);
                        return true;
                    case R.id.log_out:
                        SharedPreferences sharedPreferences = getSharedPreferences("datafile",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Public_func.clickLogout(ManagerLocationOwnerActivity.this, LoginActivity.class);
                        return true;
                }
                return true;
            }
        });
        getInfoStore();
        btnOpenListStore = findViewById(R.id.btnOpenListStore);
        btnOpenListStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                if (flag) {
                    Animation.turnOnConsoleSuggest(rvListStore, btnOpenListStore);
                } else {
                    Animation.turnOffConsoleSuggest(rvListStore, btnOpenListStore);
                }
            }
        });
    }

    private void openMenu() {
        imgMnu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    ArrayList<InforStore> listStore = new ArrayList<>();

    public void getInfoStore() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("FounderManager/ThongTinCuaHang");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listStore.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    InforStore store = data.getValue(InforStore.class);
                    listStore.add(store);
                }
                AdapterListStore adapter = new AdapterListStore(ManagerLocationOwnerActivity.this, listStore, ManagerLocationOwnerActivity.this);
                rvListStore.setLayoutManager(new LinearLayoutManager(ManagerLocationOwnerActivity.this, LinearLayoutManager.VERTICAL, false));
                rvListStore.setAdapter(adapter);
                showMarker(listStore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (map != null) {
            enableMyLocation();
            map.setInfoWindowAdapter(new MyInfoWindowAdapter(this,listStore));
            map.setOnMarkerClickListener(this);
            map.setInfoWindowAdapter(new MyInfoWindowAdapter(this, listStore));
        }
    }

    Task<Location> taskLPC;

    private void getMyLocation(final GoogleMap googleMap) {
        Context context;
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Toast.makeText(this, "You must accept app use your location", Toast.LENGTH_SHORT).show();
            return;
        } else {
            taskLPC = fusedLocationProviderClient.getLastLocation();
            taskLPC.addOnSuccessListener(new OnSuccessListener<Location>() {
                @SuppressLint("MissingPermission")
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 17));
                        googleMap.setMyLocationEnabled(true);
                    } else {
                        Log.d("TAG", "location was null");
                    }
                }
            });
            taskLPC.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("TAG", e.getMessage());
                }
            });
        }
    }

    public LatLng getLocation(String namePlace) {
        LatLng point = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(namePlace, 1);
            Address address = addresses.get(0);
            point = new LatLng(address.getLatitude(), address.getLongitude());
        } catch (Exception e) {
            e.getMessage();
        }
        return point;
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                getMyLocation(map);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }

    // [START maps_check_location_permission_result]
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // [START_EXCLUDE]
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
            // [END_EXCLUDE]
        }
    }
    // [END maps_check_location_permission_result]

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    ArrayList<Marker> listMarker = new ArrayList<>();
    ArrayList<Circle> listCircle = new ArrayList<>();

    public void showMarker(ArrayList<InforStore> listStore) {
        if (map != null && listStore.size() > 0) {
            for (int i = 0; i < listStore.size(); i++) {
                InforStore store = listStore.get(i);
                LatLng latLng = getLocation(store.diachi);
                if (latLng != null) {
                    Marker marker = map.addMarker(new MarkerOptions().position(latLng)
                            .title(store.tencuahang)
                            .snippet(store.diachi + "-" + store.giayphepkinhdoanh + "-" + store.sdt));
                    //  .icon(BitmapDescriptorFactory.fromResource(R.drawable.background_circle_active)).draggable(false));
                    Circle myCircle = map.addCircle(new CircleOptions()
                            .center(latLng)
                            .radius(50)
                            .strokeWidth(1)
                            .strokeColor(Color.parseColor("#21B8F3"))
                            .fillColor(Color.parseColor("#4847C4F4")));
                    listMarker.add(marker);
                    listCircle.add(myCircle);
                } else {
                    Toast.makeText(this, store.tencuahang + " địa chỉ sai yêu cầu cập nhật lại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        InforStore store = listStore.get(position);
        LatLng point = getLocation(listStore.get(position).diachi);
        map.clear();
        if (listMarker.size() > 0) {
            for (int i = 0; i < listMarker.size(); i++) {
                LatLng latLng = listMarker.get(i).getPosition();
                if (point != null) {
                    if (latLng.latitude == point.latitude && latLng.longitude == point.longitude) {
                        map.addMarker(new MarkerOptions()
                                .position(point)
                                .snippet(store.diachi)
                                .title(store.tencuahang)
                        );
                        Circle myCircle = map.addCircle(new CircleOptions()
                                .center(point)
                                .radius(50)
                                .strokeWidth(1)
                                .strokeColor(Color.parseColor("#21B8F3"))
                                .fillColor(Color.parseColor("#4847C4F4")));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 17));

                        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                Bundle bundle = new Bundle();
                                bundle.putString("tench",store.getTencuahang());
                                bundle.putString("diachi",store.getDiachi());
                                bundle.putString("giayphepkinhdoanh",store.getGiayphepkinhdoanh());
                                bundle.putString("sodienthoai",store.getSdt());
                                bundle.putString("id",store.getId());

                                Intent intent = new Intent(ManagerLocationOwnerActivity.this, InfoStoreActivity.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                        break;
                    }
                } else {
                    Toast.makeText(this, "Địa chỉ của " + listStore.get(position).tencuahang + " không đúng vui lòng cập nhật lại!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onLongClick(int position) {

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }
}