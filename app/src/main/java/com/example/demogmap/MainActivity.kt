package com.example.demogmap

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // as SupportMapFragment -> do the casting (Conversion) From fragment to mapFragment
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync({

            googleMap = it

            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }

            // Add marker (LatLng kuala lumpur search google)
            var klLocation = LatLng(3.1390, 101.6869)
            googleMap.addMarker(MarkerOptions().position(klLocation).title("Kuala Lumpur"))

            /*
            zoom level
            1. world
            2. Landmass
            3. city
            4. street
            ...
            20.building
             */
            // zoomlevel (Xf) -> Higher more details
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(klLocation, 8f))
        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        when(requestCode){
            requestCode ->{
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    googleMap.isMyLocationEnabled = false;
                }else{
                    googleMap.isMyLocationEnabled = true;
                }
                return
             }
        }
    }
}
