package com.seniorproject.test.ui.bookmark

import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.seniorproject.test.R
import java.nio.file.Files.find

open class MapsFragment : Fragment(),OnMapReadyCallback {


    //101 trial
//    private lateinit var mMap : GoogleMap
//    private var lattitude : Double =0.0
//    private var longitude : Double = 0.0
//
//    private lateinit var mLastLocation : Location
//    private var mMarker : Marker? = null
//
//
//    //location
//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    lateinit var locationRequest: LocationRequest
//    lateinit var locationCallback: LocationCallback
//
//    companion object{
//        private const val MY_PERMISSION_CODE : Int = 1000
//    }

//    private val callback = OnMapReadyCallback { googleMap ->
//
//
//
//    }

    private var mLocationRequest: LocationRequest? = null
    private val UPDATE_INTERVAL = (10 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

    private var latitude = 0.0
    private var longitude = 0.0

    private lateinit var mGoogleMap: GoogleMap


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        //101 trial
        //request runtime permissionb
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if (checkLocationPermission()){
//                buildLocationRequest()
//                buildLocationCallback()
//
//                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
//                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
//            }
//        }else{
//            buildLocationRequest()
//            buildLocationCallback()
//
//            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
//        }

//        Log.d("userLocation",lattitude.toString())

    }

    override fun onStart() {
        super.onStart()
        startLocationUpdates()
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mGoogleMap = googleMap;

        if (mGoogleMap != null) {
            mGoogleMap!!.addMarker(MarkerOptions().position(LatLng(latitude, longitude)).title("Current Location"))
        }

    }

    // 3.
    private fun startLocationUpdates() {
        // initialize location request object
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.run {
            setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            setInterval(UPDATE_INTERVAL)
            setFastestInterval(FASTEST_INTERVAL)
        }

        // initialize location setting request builder object
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        // initialize location service object
        val settingsClient = LocationServices.getSettingsClient(this.requireActivity())
        settingsClient!!.checkLocationSettings(locationSettingsRequest)

        // call register location listener
        registerLocationListner()
    }

    private fun registerLocationListner() {
        // initialize location callback object
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                onLocationChanged(locationResult!!.lastLocation)
            }
        }
        // 4. add permission if android version is greater then 23
        if(Build.VERSION.SDK_INT >= 23 && checkPermission()) {
            LocationServices.getFusedLocationProviderClient(this.requireActivity()).requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper())
        }
    }

    //
    private fun onLocationChanged(location: Location) {
        // create message for toast with updated latitude and longitudefa
        var msg = "Updated Location: " + location.latitude  + " , " +location.longitude

        // show toast message with updated location
        //Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
        val location = LatLng(location.latitude, location.longitude)
        mGoogleMap!!.clear()
        mGoogleMap!!.addMarker(MarkerOptions().position(location).title("Current Location"))
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    private fun checkPermission() : Boolean {
        if (ContextCompat.checkSelfPermission(this.requireContext() , android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions()
            return false
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this.requireActivity(), arrayOf("Manifest.permission.ACCESS_FINE_LOCATION"),1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if (permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION ) {
                registerLocationListner()
            }
        }
    }


    //101 trial
//    override fun onStop() {
//
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//        super.onStop()
//    }
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        //init google play service
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if (ContextCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//            {
//                mMap!!.isMyLocationEnabled = true
//            }
//
//        }else{
//            mMap!!.isMyLocationEnabled = true
//        }
//
//        // enable zoom control
//        mMap.uiSettings.isZoomControlsEnabled = true
//    }
//
//    private fun buildLocationCallback() {
//        locationCallback = object : LocationCallback(){
//            override fun onLocationResult(p0: LocationResult?) {
//                mLastLocation = p0!!.locations.get(p0!!.locations.size-2) //get last location
//
//                if (mMarker!=null){
//                    mMarker!!.remove()
//                    Log.d("addMarker",mMarker.toString())
//                }
//
//                lattitude = mLastLocation.latitude
//                longitude = mLastLocation.longitude
//
////                lattitude.toDouble()
////                longitude.toDouble()
//
//                val latLng : LatLng = LatLng(lattitude,longitude)
//                val markerOptions = MarkerOptions()
//                    .position(latLng)
//                    .title("Your Position")
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//
//                mMarker = mMap!!.addMarker(markerOptions)
//
//                //move Camera
//                mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,11f))
////                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
//
//                Log.d("currentLocatrion",latLng.toString())
//            }
//        }
//    }
//
//    private fun buildLocationRequest() {
//        locationRequest = LocationRequest()
//        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        locationRequest.interval = 5000
//        locationRequest.fastestInterval = 3000
//        locationRequest.smallestDisplacement = 10f
//    }
//
//    private fun checkLocationPermission(): Boolean {
//        if (ContextCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//        {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION))
//                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(
//                    android.Manifest.permission.ACCESS_FINE_LOCATION
//                ),MY_PERMISSION_CODE)
//            else
//                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(
//                    android.Manifest.permission.ACCESS_FINE_LOCATION
//                ),MY_PERMISSION_CODE)
//            return false
//        }
//        else
//            return true
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when(requestCode){
//            MY_PERMISSION_CODE -> {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(
//                            this.requireContext(),
//                            android.Manifest.permission.ACCESS_FINE_LOCATION
//                        ) == PackageManager.PERMISSION_GRANTED
//                    )
//                        if (checkLocationPermission())
//                            buildLocationRequest()
//                            buildLocationCallback()
//
//                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
//                            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
//                            mMap!!.isMyLocationEnabled = true
//                } else {
//                    Toast.makeText(this.requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }


}