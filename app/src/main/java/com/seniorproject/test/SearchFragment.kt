package com.seniorproject.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.seniorproject.test.ui.home.HomeFragment

class SearchFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.home_search, container, false)
        //back home
        val backbtn = root.findViewById<ImageButton>(R.id.backbtn)

        backbtn.setOnClickListener {
            //Toast.makeText(root.context,"back home",Toast.LENGTH_SHORT).show()
            val activity = view?.context as AppCompatActivity
            val HomeFragment = HomeFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.searchpage, HomeFragment).commit()
        }

        return root

    }
}