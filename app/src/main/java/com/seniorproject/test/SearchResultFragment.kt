package com.seniorproject.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.seniorproject.test.ui.home.HomeFragment

class SearchResultFragment : Fragment(){
    var group: String? = ""
    private var FilterSearchAdapter:FilterSearchAdapter? = null
    private var mCafes : List<Filter_Search_Cafes>? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.home_searchresult, container, false)

        //show data in recyclerview
        group = arguments?.getString("group")
        //root.findViewById<TextView>(R.id.group).text = group
        recyclerView = root.findViewById(R.id.filter_search_result_rec)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        mCafes = ArrayList()
        retrieveAllCafes()

        //back btn
        val backbtn = root.findViewById<ImageButton>(R.id.backbtn)
        backbtn.setOnClickListener {
            val activity = view?.context as AppCompatActivity
            val HomeFragment = HomeFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.searcresulthpage, HomeFragment).commit()
        }
        return root
    }
    private fun retrieveAllCafes()
    {
        val g1 = FirebaseDatabase.getInstance().reference.child("cafes").orderByChild("pricegroup").equalTo("supercheap")
        val g2 = FirebaseDatabase.getInstance().reference.child("cafes").orderByChild("pricegroup").equalTo("cheap")
        val g3 = FirebaseDatabase.getInstance().reference.child("cafes").orderByChild("pricegroup").equalTo("mid")
        val g4 = FirebaseDatabase.getInstance().reference.child("cafes").orderByChild("pricegroup").equalTo("expensive")

        if (group == "1"){

                g1.addValueEventListener(object : ValueEventListener
                {
                    override fun onDataChange(p0: DataSnapshot) {
                        for (snapshot in p0.children)
                        {
                            val searchCafes : Filter_Search_Cafes? = snapshot.getValue(Filter_Search_Cafes::class.java)
                            if (searchCafes != null)
                            {
                                (mCafes as ArrayList<Filter_Search_Cafes>).add(searchCafes!!)
                            }
                        }
                        FilterSearchAdapter = FilterSearchAdapter(context!!,mCafes!!,false)
                        recyclerView!!.adapter = FilterSearchAdapter

                    }

                    override fun onCancelled(p0: DatabaseError) {

                    }
                })

        }
        else if (group == "2"){

            g2.addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(p0: DataSnapshot) {
                    for (snapshot in p0.children)
                    {
                        val searchCafes : Filter_Search_Cafes? = snapshot.getValue(Filter_Search_Cafes::class.java)
                        if (searchCafes != null)
                        {
                            (mCafes as ArrayList<Filter_Search_Cafes>).add(searchCafes!!)
                        }
                    }
                    FilterSearchAdapter = FilterSearchAdapter(context!!,mCafes!!,false)
                    recyclerView!!.adapter = FilterSearchAdapter

                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })

        }
        else if (group == "3"){

            g3.addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(p0: DataSnapshot) {
                    for (snapshot in p0.children)
                    {
                        val searchCafes : Filter_Search_Cafes? = snapshot.getValue(Filter_Search_Cafes::class.java)
                        if (searchCafes != null)
                        {
                            (mCafes as ArrayList<Filter_Search_Cafes>).add(searchCafes!!)
                        }
                    }
                    FilterSearchAdapter = FilterSearchAdapter(context!!,mCafes!!,false)
                    recyclerView!!.adapter = FilterSearchAdapter

                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })

        }
        else if (group == "4"){

            g4.addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(p0: DataSnapshot) {
                    for (snapshot in p0.children)
                    {
                        val searchCafes : Filter_Search_Cafes? = snapshot.getValue(Filter_Search_Cafes::class.java)
                        if (searchCafes != null)
                        {
                            (mCafes as ArrayList<Filter_Search_Cafes>).add(searchCafes!!)
                        }
                    }
                    FilterSearchAdapter = FilterSearchAdapter(context!!,mCafes!!,false)
                    recyclerView!!.adapter = FilterSearchAdapter

                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })

        }
        else{
            val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("cafes")
            firebaseDatabase.addValueEventListener(object : ValueEventListener
            {
                override fun onDataChange(p0: DataSnapshot) {

                    for (snapshot in p0.children)
                    {
                        val searchCafes : Filter_Search_Cafes? = snapshot.getValue(Filter_Search_Cafes::class.java)
                        if (searchCafes != null)
                        {
                            (mCafes as ArrayList<Filter_Search_Cafes>).add(searchCafes!!)
                        }
                    }
                    FilterSearchAdapter = FilterSearchAdapter(context!!,mCafes!!,false)
                    recyclerView!!.adapter = FilterSearchAdapter

                }

                override fun onCancelled(p0: DatabaseError) {

                }
            })
        }

    }
}