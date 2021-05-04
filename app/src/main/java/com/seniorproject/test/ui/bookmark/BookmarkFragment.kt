package com.seniorproject.test.ui.bookmark

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.seniorproject.test.R
import com.seniorproject.test.ui.account.AccountViewModel

class BookmarkFragment :Fragment() {
    private lateinit var bookmarkViewModel: BookmarkViewModel

//    private lateinit var mMap : GoogleMap
//    private var lattitude : Double =0.0
//    private var longitude : Double = 0.0
//
//    private lateinit var mLastLocation : Location

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        bookmarkViewModel =
                ViewModelProvider(this).get(BookmarkViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_bookmark, container, false)
        val textView: TextView = root.findViewById(R.id.text_bookmark)
        bookmarkViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }


}