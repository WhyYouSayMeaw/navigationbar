package com.seniorproject.test.ui.location

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.api.Context
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.LatLng
import com.seniorproject.test.DetailcafeFragment
import com.seniorproject.test.MainActivity
import com.seniorproject.test.R
import com.seniorproject.test.ui.home.cafe
import com.seniorproject.test.ui.home.cafeViewHolder
import com.squareup.picasso.Picasso

data class cafe(
        val CafeName: String = "",
        val CafeDistance: Float,
        val cafePicture: String = "",
        val cafeLatLng: GeoPoint
)
class cafeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
interface Communicator {
    fun passDataCom(cafename: String)
}


class LocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var locationViewModel: LocationViewModel
    private val db = Firebase.firestore

    private lateinit var mMap: GoogleMap
    private lateinit var locationManger: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var currentLatLng: com.google.android.gms.maps.model.LatLng

    //-----------------------get current location  youtube EDMT Dev
    private var latitude : Double = 0.toDouble()
    private var longitude : Double = 0.toDouble()
    private lateinit var LatLng : LatLng

    lateinit var mLastLocation: Location
    private var mMarker : Marker?=null

//    private lateinit var mMap: GoogleMap

    //location
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    companion object {
        private const val MY_PERMISSION_CODE: Int = 1000
    }
//-----------------------get current location  youtube EDMT Dev
    override fun onCreateView(

            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        locationViewModel =
                ViewModelProvider(this).get(LocationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_location, container, false)

//        val mapFragment = (activity as FragmentActivity).supportFragmentManager
//                .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

//        locationManger = (activity as FragmentActivity).getSystemService(android.content.Context.LOCATION_SERVICE) as LocationManager
//        locationListener = object: LocationListener{
//            override fun onLocationChanged(location: Location) {
////                location.text = " Location: "+location.latitude.toString()+" , "+location.longitude.toString()
//                currentLatLng = com.google.android.gms.maps.model.LatLng(location.latitude, location.longitude)
//            }
//            override fun onProviderDisabled(provider: String) {
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
//        }

//-----------------------get current location  youtube EDMT Dev
        // request runtime permission
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            if (checkLocationPermission()){
                buildLocationRequest()
                buildLocationCallback()

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
            }else{
                buildLocationRequest()
                buildLocationCallback()

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
                fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
            }


        val query = db.collection("Cafes")
        val options = FirestoreRecyclerOptions.Builder<cafe>().setQuery(query, cafe::class.java)
                .setLifecycleOwner(this).build()
        val adapter = object : FirestoreRecyclerAdapter<cafe, cafeViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cafeViewHolder {
                val view = LayoutInflater.from(this@LocationFragment.context).inflate(R.layout.near_by_item, parent, false)
                return cafeViewHolder(view)
            }



            override fun onBindViewHolder(holder: cafeViewHolder, position: Int, model: cafe) {
                val cafeName: TextView = holder.itemView.findViewById(R.id.cafename)
                val cafeDis: TextView = holder.itemView.findViewById(R.id.cafeDistance)
                val cafePic: ImageView = holder.itemView.findViewById(R.id.cafepic)
                val cafeGeo : GeoPoint
                cafeName.text = model.CafeName
//                cafeDis.text = model.cafeDistance
                Picasso.get().load(model.Logo).into(cafePic)

                holder.itemView.setOnClickListener(object :View.OnClickListener{
                    override fun onClick(v: View?) {
                        val activity = v!!.context as AppCompatActivity
                        val detailcafe = DetailcafeFragment()
                        val bundle = Bundle()
                        bundle.putString("name",model.CafeName)
                        detailcafe.arguments = bundle
                        activity.supportFragmentManager.beginTransaction().replace(R.id.Nearby,detailcafe).addToBackStack(null).commit()

                    }
                })
            }

        }
        root.findViewById<RecyclerView>(R.id.nearbyCafe).adapter = adapter
        root.findViewById<RecyclerView>(R.id.nearbyCafe).layoutManager = LinearLayoutManager(this.context)

        return root

    }
//-----------------------get current location  youtube EDMT Dev
    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0!!.locations[p0!!.locations.size-2] // get last location

//                if (mMarker!=null){
//                    mMarker!!.remove()
//                }
//
                latitude = mLastLocation.latitude
                longitude =mLastLocation.longitude
                Log.d(TAG, "onLocationResult: ")

//                val latLng = LatLng(latitude,longitude)

//                val markerOptions = MarkerOptions()
//                        .position(latLng)
//                        .title("Your Position")
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
//                mMarker = mMap!!.addMarker
//                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
//                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun checkLocationPermission():Boolean {
        return if (ContextCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),MY_PERMISSION_CODE)
            } else
                ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),MY_PERMISSION_CODE)
            false
        } else
            true
        }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MY_PERMISSION_CODE->{
                if (grantResults.size >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        if (checkLocationPermission()){
                            buildLocationRequest()
                            buildLocationCallback()

                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())
                            fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback, Looper.myLooper())
                            mMap!!.isMyLocationEnabled=true
                        }
                    }
                }
                else
                    Toast.makeText(this.context,"Permission Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap!!
        val sydney = com.google.android.gms.maps.model.LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    override fun onMapReady(googleMap: GoogleMap){
        mMap = googleMap

        //init google play service
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                mMap!!.isMyLocationEnabled=true
            }
        }else
            mMap!!.isMyLocationEnabled=true
        //Enable Zoom control (not necsessary)
        mMap.uiSettings.isZoomControlsEnabled=true

    }
//    override fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<out String>,
//            grantResults: IntArray
//    ) {
//        when(requestCode){
//            10 -> requestLocationButton()
//            else -> { }
//        }
//    }

//    private fun requestLocationButton() {
//        if(ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
//                requestPermissions  (arrayOf(
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.INTERNET),10
//                )
//            }
//            return
//        }
//        locationManger.requestLocationUpdates("gps",5000,0f,locationListener)
////        gpsBtn.setOnClickListener{
////            if(currentLatLng!=null){
////                latText.setText(currentLatLng.latitude.toString())
////                lonText.setText(currentLatLng.longitude.toString())
////            }
////        }
//    }


//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//    }
//    override fun onPause() {
//        super.onPause()
//        locationManger.removeUpdates(locationListener)
//        Log.d("GPSStatus", "On Pause!")
//    }

}



