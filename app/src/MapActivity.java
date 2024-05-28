package com.example.a71;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHelper dbHelper;
    private List<LatLng> itemLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DatabaseHelper(this);
        itemLocations = new ArrayList<>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            throw new RuntimeException("Error initializing map fragment");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadItemsOnMap();
    }

    private void loadItemsOnMap() {
        Cursor cursor = dbHelper.getAllItems();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        while (cursor.moveToNext()) {
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION));
            LatLng latLng = parseLocation(location);
            if (latLng != null) {
                itemLocations.add(latLng);
                mMap.addMarker(new MarkerOptions().position(latLng).title("Item Location"));
                builder.include(latLng);
            }
        }
        cursor.close();

        if (!itemLocations.isEmpty()) {
            LatLngBounds bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
    }

    private LatLng parseLocation(String location) {
        try {
            String[] parts = location.split(",");
            double lat = Double.parseDouble(parts[0].trim());
            double lng = Double.parseDouble(parts[1].trim());
            return new LatLng(lat, lng);
        } catch (Exception e) {
            return null;
        }
    }
}
