package com.seniorproject.test.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.LatLng
import com.seniorproject.test.DetailcafeFragment
import com.seniorproject.test.R
import com.seniorproject.test.ui.home.cafeViewHolder
import com.squareup.picasso.Picasso

data class cafeLocationFragment(
        val CafeName: String,
        val CafeDistance: Float,
        val CafePicture: String,
        val CafeLatitude : String,
        val CafeLongitude: String,
        val WorkTime : String
) {
    constructor() : this("",0.toFloat(),"", "","","") {
    }
}

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
        val options = FirestoreRecyclerOptions.Builder<cafeLocationFragment>().setQuery(query, cafeLocationFragment::class.java)
                .setLifecycleOwner(this).build()
        val adapter = object : FirestoreRecyclerAdapter<cafeLocationFragment, cafeViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cafeViewHolder {
                val view = LayoutInflater.from(this@LocationFragment.context).inflate(R.layout.near_by_item, parent, false)
                return cafeViewHolder(view)
            }



            @SuppressLint("ResourceType")
            override fun onBindViewHolder(holder: cafeViewHolder, position: Int, model: cafeLocationFragment) {
                val cafeName: TextView = holder.itemView.findViewById(R.id.cafename)
//                val cafeDis: TextView = holder.itemView.findViewById(R.id.cafeDistance)
                val cafePic: ImageView = holder.itemView.findViewById(R.id.cafepic)

//                val latLng : LatLng
                val lat : String = model.CafeLatitude
                val lng : String = model.CafeLongitude
//                var Geo : GeoPoint = GeoPoint(lat,lng)
//                lat = Geo.latitude.toDouble()
//                lng = Geo.longitude.toDouble()
//                lat = Geo.longitude.also { model.Longitude }
//                lng = Geo.latitude.also { model.Lattitude}
                cafeName.text = model.CafeName
//                Geo = GeoPoint(lat,lng)
                /*lat = model.CafeLat
                lng = model.CafeLng*/

                if (model.CafePicture.isEmpty()) {
                    cafePic.setImageResource(R.drawable._01681745_264364834917056_1840657655873588240_n)
                } else{
                    Picasso.get().load(model.CafePicture).into(cafePic)
                }
//                Picasso.get().load(model.CafePicture).into(cafePic)
//                Log.d("cafeGeo",lat + model.CafeName)

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



