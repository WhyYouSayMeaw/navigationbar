package com.seniorproject.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment


class ThankPopUp : Fragment() {
    var cname: String? = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.detail_showalert, container, false)

        cname = arguments?.getString("cname")
        //Toast.makeText(this.context,cname,Toast.LENGTH_SHORT).show()
        Log.d("name",cname.toString())
        //back to cafe detail
        val closebtn = root.findViewById<ImageButton>(R.id.closebtn)
        closebtn.setOnClickListener {
            root.findViewById<ConstraintLayout>(R.id.showalert).visibility = View.INVISIBLE
            val activity = view?.context as AppCompatActivity
            val cafedetail = DetailcafeFragment()
            val bundle = Bundle()
            bundle.putString("name",cname)
            cafedetail.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.showalert, cafedetail).detach(cafedetail).attach(cafedetail).commit()
        }


        return root
    }
}