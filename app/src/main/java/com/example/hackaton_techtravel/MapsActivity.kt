package com.example.hackaton_techtravel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.hackaton_techtravel.PermissionCodes.Companion.CAMERA_PERMISSION_CODE
import com.example.hackaton_techtravel.PermissionCodes.Companion.LOCATION_PERMISSION_CODE

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.hackaton_techtravel.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private var geocoder: Geocoder? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpButtonsWithoutPermissions()
        setUpEventListenersWithoutPermissions()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap!!.uiSettings.isZoomGesturesEnabled = true

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun setUpButtonsWithoutPermissions() {
        binding.mapXButton.setOnClickListener {
            binding.selectedLocation.text = ""
        }

        binding.mapSearchButton.setOnClickListener {
            searchLocationWithGeoCoder(binding.searchLocationMap.text.toString())
        }

        binding.mapLocationButton.setOnClickListener {
            checkPermissionForLocation(this)
        }

        binding.mapAddImageButton.setOnClickListener {
            checkPermissionForCamera(this)
        }
    }

    private fun setUpEventListenersWithoutPermissions() {

    }

    private fun setUpButtonsWithLocationPermission() {
        binding.mapLocationButton.setOnClickListener {
            val latIng = getCurrentLocation()
            setUpMarker(latIng)
        }
    }

    private fun disableButtonsWithLocationPermission() {
        // Deactivate button
        binding.mapLocationButton.isEnabled = false
    }

    private fun setUpButtonsWithCameraPermission() {
        binding.mapAddImageButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }
    }

    private fun disableButtonsWithCameraPermission() {
        // Deactivate button
        binding.mapAddImageButton.isEnabled = false
    }

    private fun searchLocationWithGeoCoder(location: String) {
        if(location.isEmpty() || geocoder == null) {
            Toast.makeText(this, "Location is empty", Toast.LENGTH_SHORT).show()
            return
        }

        val addressList = geocoder!!.getFromLocationName(location, 1)

        if (!addressList.isNullOrEmpty()) {
            val address = addressList[0]
            val latLng = LatLng(address.latitude, address.longitude)

            if (mMap == null) {
                return
            }

            setUpMarker(latLng)
        } else {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(): LatLng {
        var latLng = LatLng(0.0, 0.0)
        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                latLng = LatLng(location.latitude, location.longitude)
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show()
            }
        }

        return latLng
    }

    private fun setUpMarker(latLng: LatLng) {
        if (mMap == null) {
            return
        }
        mMap!!.clear()
        mMap!!.addMarker(MarkerOptions().position(latLng).title("Avistamiento"))
        mMap!!.moveCamera(CameraUpdateFactory.zoomTo(30f))
        mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    private fun checkPermissionForLocation(activity: AppCompatActivity) {
        when{
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                setUpButtonsWithLocationPermission()
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // If user previously denied the permission
                Toast.makeText(this, "Permission previously denied", Toast.LENGTH_SHORT).show()
                 requestPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE, "Needed for locating person")
            }

            else -> {
                // Always call the own function to request permission, not the system one (requestPermissions)
                requestPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE, "Needed for locating person")
            }

        }
    }

    private fun checkPermissionForCamera(activity: AppCompatActivity) {
        when{
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                setUpButtonsWithCameraPermission()
            }

            shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                // If user previously denied the permission
                Toast.makeText(this, "Permission previously denied", Toast.LENGTH_SHORT).show()
                requestPermission(this, android.Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE, "Needed for taking pictures")
            }

            else -> {
                // Always call the own function to request permission, not the system one (requestPermissions)
                requestPermission(this, android.Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE, "Needed for taking pictures")
            }

        }
    }

    private fun requestPermission(context: Activity, permission: String, requestCode: Int, justify: String) {
        if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if(shouldShowRequestPermissionRationale(permission)) {
                Toast.makeText(this, justify, Toast.LENGTH_SHORT).show()
            }
            requestPermissions(arrayOf(permission), requestCode)
        } else {
            when (requestCode) {
                LOCATION_PERMISSION_CODE -> {
                    setUpButtonsWithLocationPermission()
                }
                CAMERA_PERMISSION_CODE -> {
                    setUpButtonsWithCameraPermission()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted
                    setUpButtonsWithLocationPermission()
                } else {
                    // Permission denied
                    disableButtonsWithLocationPermission()
                }
                return
            }

            CAMERA_PERMISSION_CODE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted
                    setUpButtonsWithCameraPermission()
                } else {
                    // Permission denied
                    disableButtonsWithCameraPermission()
                }
                return
            }

            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }
}