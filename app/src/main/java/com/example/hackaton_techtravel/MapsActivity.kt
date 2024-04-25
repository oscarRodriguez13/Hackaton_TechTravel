package com.example.hackaton_techtravel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.hackaton_techtravel.PermissionCodes.Companion.CAMERA_PERMISSION_CODE
import com.example.hackaton_techtravel.PermissionCodes.Companion.LOCATION_PERMISSION_CODE
import com.example.hackaton_techtravel.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import org.osmdroid.api.IMapController
import org.osmdroid.bonuspack.routing.OSRMRoadManager
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.util.ArrayList


class MapsActivity : AppCompatActivity() {
    companion object {
        private val aguadas = GeoPoint(5.613140, -75.457597)
        private const val lowerLeftLatitude = 5.447439
        private const val lowerLeftLongitude = -75.586738
        private const val upperRightLatitude = 5.744714
        private const val upperRightLongitude = -75.317356
    }

    private lateinit var binding: ActivityMapsBinding
    private val startPoint = GeoPoint(4.6287662, -74.0636298647595)
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private var mrk: Marker? = null
    private var roadOverlay: Polyline? = null
    private lateinit var roadManager: RoadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startOSM()
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setUpButtonsDoNotRequirePermissions()
        setUpButtonsRequirePermissions()

        // TODO for development; change to false for production
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        setUpMarkers()
    }

    private fun setUpMarkers() {
        // 5.607519, -75.450010
        val marker1 = createMarker(GeoPoint(5.607519, -75.450010), "Aguadas", "Avistamiento Climatico", 0)
        // 5.614931, -75.463819
        val marker2 = createMarker(GeoPoint(5.614931, -75.463819), "Aguadas", "Avistamiento Animal", 0)
        // 5.615662, -75.463461
        val marker3 = createMarker(GeoPoint(5.615662, -75.463461), "Monserrate", "Avistamiento Camara", 0)

        marker1?.let { binding.osmMap.overlays.add(it) }
        marker2?.let { binding.osmMap.overlays.add(it) }
        marker3?.let { binding.osmMap.overlays.add(it) }
    }

    private fun setUpButtonsDoNotRequirePermissions() {
        binding.searchLocationMap.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val location = binding.searchLocationMap.text.toString()
                if (location.isNotEmpty()) {
                    val loc = getLocationFromAddress(location)
                    val mapController: IMapController = binding.osmMap.controller
                    mapController.setZoom(9.5)
                    mapController.setCenter(loc)
                    if (mrk != null) {
                        binding.osmMap.overlays.remove(mrk)
                    }
                    mrk = createMarker(loc, location, binding.mapSpinner.selectedItem.toString(), 0)
                    mrk?.let { binding.osmMap.overlays.add(it) }
                } else {
                    Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }

        binding.mapXButton.setOnClickListener {
            binding.searchLocationMap.text.clear()
        }

        binding.mapSearchButton.setOnClickListener {
            val location = binding.searchLocationMap.text.toString()
            if (location.isNotEmpty()) {
                val loc = getLocationFromAddress(location)
                val mapController: IMapController = binding.osmMap.controller
                mapController.setZoom(9.5)
                mapController.setCenter(loc)
                if (mrk != null) {
                    binding.osmMap.overlays.remove(mrk)
                }
                mrk = createMarker(loc, location, binding.mapSpinner.selectedItem.toString(), 0)
                mrk?.let { binding.osmMap.overlays.add(it) }
            } else {
                Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLocationFromAddress(location: String): GeoPoint {
        val geocoder = android.location.Geocoder(this)
        val addresses = geocoder.getFromLocationName(location, 1, lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRightLongitude)
        return if (!addresses.isNullOrEmpty()) {
            GeoPoint(addresses[0].latitude, addresses[0].longitude)
        } else {
            aguadas
        }
    }

    private fun setUpButtonsRequirePermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setUpButtonsWithLocationPermissions()
        } else {
            binding.mapLocationButton.setOnClickListener {
                checkPermissionForLocation(this)
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            setUpButtonsWithCameraPermissions()
        } else {
            binding.mapAddImageButton.setOnClickListener {
                checkPermissionForCamera(this)
            }
        }
    }

    private fun setUpButtonsWithLocationPermissions() {
        binding.mapLocationButton.setOnClickListener {
            obtainLocation()
        }
    }

    private fun setUpButtonsWithoutLocationPermissions() {
        binding.mapLocationButton.setOnClickListener {
            Toast.makeText(this, "Please allow location permission", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setUpButtonsWithCameraPermissions() {
        binding.mapAddImageButton.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)
        }
    }

    private fun setUpButtonsWithoutCameraPermissions() {
        binding.mapAddImageButton.setOnClickListener {
            Toast.makeText(this, "Please allow camera permission", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startOSM() {
        // Set userAgent to identify the app to the OSM service
        Configuration.getInstance().userAgentValue = "com.example.hackaton_techtravel"
        binding.osmMap.setTileSource(TileSourceFactory.MAPNIK)
        binding.osmMap.setMultiTouchControls(true)
        roadManager = OSRMRoadManager(this, "ANDROID")
        val mapController: IMapController = binding.osmMap.controller
        mapController.setZoom(9.5)
        mapController.setCenter(startPoint)
    }

    @SuppressLint("MissingPermission")
    private fun obtainLocation(){
        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val currentLocation = GeoPoint(location.latitude, location.longitude)
                val mapController: IMapController = binding.osmMap.controller
                mapController.setZoom(9.5)
                mapController.setCenter(currentLocation)
                val name = Geocoder(this).getFromLocation(location.latitude, location.longitude, 1)
                createMarker(currentLocation, name!![0].featureName, binding.mapSpinner.selectedItem.toString(), 0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.osmMap.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.osmMap.onPause()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createMarker(p: GeoPoint, title: String?, desc: String?, iconID: Int): Marker? {
        var marker: Marker? = null
        marker = Marker(binding.osmMap)
        title?.let { marker.title = it }
        desc?.let { marker.subDescription = it }

        if (iconID != 0) {
            val myIcon = resources.getDrawable(iconID, theme)
            marker.icon = myIcon
        }
        marker.position = p
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        return marker
    }

    private fun checkPermissionForLocation(activity: AppCompatActivity) {
        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                setUpButtonsWithLocationPermissions()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // If user previously denied the permission
                Toast.makeText(this, "Permission previously denied", Toast.LENGTH_SHORT).show()
                requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE, "Needed for locating person")
            }

            else -> {
                // Always call the own function to request permission, not the system one (requestPermissions)
                requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE, "Needed for locating person")
            }

        }
    }

    private fun checkPermissionForCamera(activity: AppCompatActivity) {
        when{
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                // Permission already granted
                setUpButtonsWithCameraPermissions()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                // If user previously denied the permission
                Toast.makeText(this, "Permission previously denied", Toast.LENGTH_SHORT).show()
                requestPermission(this, Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE, "Needed for capturing images")
            }

            else -> {
                // Always call the own function to request permission, not the system one (requestPermissions)
                requestPermission(this, Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE, "Needed for capturing images")
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
            // Permission granted
            when(requestCode) {
                LOCATION_PERMISSION_CODE -> setUpButtonsWithLocationPermissions()
                CAMERA_PERMISSION_CODE -> setUpButtonsWithCameraPermissions()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_CODE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted
                    setUpButtonsWithLocationPermissions()
                } else {
                    // Permission denied
                    setUpButtonsWithoutLocationPermissions()
                }
                return
            }

            CAMERA_PERMISSION_CODE -> {
                if((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted
                    setUpButtonsWithCameraPermissions()
                } else {
                    // Permission denied
                    setUpButtonsWithoutCameraPermissions()
                }
                return
            }

            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

}