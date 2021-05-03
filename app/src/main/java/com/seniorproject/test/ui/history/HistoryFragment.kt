package com.seniorproject.test.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.seniorproject.test.*
import com.seniorproject.test.ui.dashboard.DashboardViewModel

class HistoryFragment : Fragment() {
    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var mAuth: FirebaseAuth
    var username:String? = ""
    private var HistoryAdapter:HistoryAdapter? = null
    private var mComment : List<History>? = null
    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        username = currentUser?.displayName.toString()
        recyclerView = root.findViewById(R.id.historyrecycler)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        mComment = ArrayList()
        val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("users/Reviews")
            .orderByChild("user_name").equalTo(username)//.equalTo(currentUser.displayName.toString())
        firebaseDatabase.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot) {

                for (snapshot in p0.children)
                {
                    val reviews : History? = snapshot.getValue(History::class.java)
                    //if (reviews != null)
                    //{
                    (mComment as ArrayList<History>).add(reviews!!)
                    //}
                }

                HistoryAdapter = HistoryAdapter(context!!,mComment!!,false)
                recyclerView!!.adapter = HistoryAdapter


            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        return root
    }
}