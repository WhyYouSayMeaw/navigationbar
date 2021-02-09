package com.seniorproject.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.seniorproject.test.ui.location.LocationViewModel

class DetailcafeFragment : Fragment() {
    //private lateinit var locationViewModel: LocationViewModel
    var cname: String? = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       /* locationViewModel =
            ViewModelProvider(this).get(LocationViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_detailcafe, container, false)
        //val textView: TextView = root.findViewById(R.id.text_location)
       /*locationViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        cname = arguments?.getString("name")
        root.findViewById<TextView>(R.id.textView2).text = cname
        return root
    }
}
