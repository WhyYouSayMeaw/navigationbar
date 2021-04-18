package com.seniorproject.test

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.seniorproject.test.ui.home.HomeFragment


class SearchFragment : Fragment() {

    //    lateinit var searchtext : EditText
//    lateinit var recyclerView: RecyclerView
//    lateinit var database: DatabaseReference
/*    private var recyclerView : RecyclerView? = null
    private var searchAdapter : SearchAdapter? = null
    private var mCafes : MutableList<Search_Cafes>? = null*/

    private var searchAdapter:SearchAdapter? = null
    private var mCafes : List<Search_Cafes>? = null
    private var recyclerView:RecyclerView? = null
    private var searchtext : EditText? = null


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

        //search cafe name
        recyclerView = root.findViewById(R.id.search_result_rec)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        searchtext = root.findViewById(R.id.searchtext)
        mCafes = ArrayList()
        retrieveAllCafes()
        searchtext!!.addTextChangedListener(object : TextWatcher
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                search(cs.toString().toLowerCase())
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })


        return root

    }

    private fun retrieveAllCafes()
    {
        val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("cafes")
        firebaseDatabase.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot) {
                (mCafes as ArrayList<Search_Cafes>).clear()
                if(searchtext!!.text.toString() == "")
                {
                    for (snapshot in p0.children)
                    {
                        val searchCafes : Search_Cafes? = snapshot.getValue(Search_Cafes::class.java)
                        if (searchCafes != null)
                        {
                            (mCafes as ArrayList<Search_Cafes>).add(searchCafes!!)
                        }
                    }
                    searchAdapter = SearchAdapter(context!!,mCafes!!,false)
                    recyclerView!!.adapter = searchAdapter
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun search(str:String)
    {
        val queryfirebaseDatabase = FirebaseDatabase.getInstance().reference
                .child("cafes").orderByChild("searchname")
                .startAt(str)
                .endAt(str + "\uf8ff")

        queryfirebaseDatabase.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot) {

                (mCafes as ArrayList<Search_Cafes>).clear()
                for (snapshot in p0.children)
                {
                    val searchCafes : Search_Cafes? = snapshot.getValue(Search_Cafes::class.java)
                    if (searchCafes != null)
                    {
                        (mCafes as ArrayList<Search_Cafes>).add(searchCafes!!)
                    }
                }
                searchAdapter = SearchAdapter(context!!,mCafes!!,false)
                recyclerView!!.adapter = searchAdapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }


    }
