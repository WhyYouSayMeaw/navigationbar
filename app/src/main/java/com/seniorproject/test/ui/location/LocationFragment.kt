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

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var latLng : LatLng

    private lateinit var locationViewModel: LocationViewModel
    private val db = Firebase.firestore

    override fun onCreateView(

            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        locationViewModel =
                ViewModelProvider(this).get(LocationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_location, container, false)
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.requireContext())
        fetchLocation()

        return root

    }

    override fun onMapReady(p0: GoogleMap?) {
        TODO("Not yet implemented")
    }
    private fun fetchLocation() {

        val task = fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if (it != null){
                Toast.makeText(context, "${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
                Log.d("locationJaaaaaaaa", "${it.latitude} ${it.longitude}")
            }
        }
    }


}



