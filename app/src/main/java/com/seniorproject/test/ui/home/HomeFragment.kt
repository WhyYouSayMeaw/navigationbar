package com.seniorproject.test.ui.home


import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seniorproject.test.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

data class cafe(
        val CafeName: String = "",
        val CafeDes: String = "",
        val WorkTime: String = "",
        val Logo: String = ""
)
class cafeViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)


class HomeFragment() : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    val db = Firebase.firestore
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        //Recommendation
        val query = db.collection("Cafes")
        val options = FirestoreRecyclerOptions.Builder<cafe>().setQuery(query,cafe::class.java)
                .setLifecycleOwner(this).build()
        val adapter = object : FirestoreRecyclerAdapter<cafe,cafeViewHolder>(options){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cafeViewHolder {
                val view = LayoutInflater.from(this@HomeFragment.context).inflate(R.layout.home_item_cafes,parent,false)
                return cafeViewHolder(view)
            }

            override fun onBindViewHolder(holder: cafeViewHolder, position: Int, model: cafe) {
                val tvName:TextView = holder.itemView.findViewById(R.id.cafename)
                val tvDes:TextView = holder.itemView.findViewById(R.id.cafedes)
                val tvTime:TextView = holder.itemView.findViewById(R.id.cafewt)
                val ivLogo :ImageView = holder.itemView.findViewById(R.id.cafepic)
                tvName.text = model.CafeName
                tvDes.text = model.CafeDes
                tvTime.text = model.WorkTime
                Picasso.get().load(model.Logo).into(ivLogo)
                //Glide.with().load(model.Logo).into(ivLogo)

            }
        }
        root.findViewById<RecyclerView>(R.id.rvCafes).adapter = adapter
        root.findViewById<RecyclerView>(R.id.rvCafes).layoutManager = LinearLayoutManager(this.context)
        return root
    }


}
