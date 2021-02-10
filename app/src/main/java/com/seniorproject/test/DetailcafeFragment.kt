package com.seniorproject.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class DetailcafeFragment : Fragment() {
    //private lateinit var locationViewModel: LocationViewModel
    private lateinit var reference: DocumentReference
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
        val db = FirebaseFirestore.getInstance()
        db.collection("Cafes").whereEqualTo("CafeName",cname)
                .get()
                /*.addOnSuccessListener { documentSnapshot ->

                }*/
                .addOnCompleteListener {
                    //val result = StringBuffer()
                    var  desc= String()
                    var  location = String()
                    var  wt = String()
                    var  logolink = String()
                    var img =root.findViewById<ImageView>(R.id.logoimg)
                    if (it.isSuccessful){
                        for (document in it.result!!){
                            desc = document.getString("CafeDes").toString()
                            wt = document.getString("WorkTime").toString()
                            logolink = document.getString("Logo").toString()
                            //result1.append(document.data.getValue("CafeDes")).append("\n\n ")
                            //result2.append(document.data.getValue("WorkTime")).append(" ")
                        }
                        root.findViewById<TextView>(R.id.textView3).setText(desc)
                        root.findViewById<TextView>(R.id.textView5).setText(wt)
                        Picasso.get().load(logolink).into(img)
                    }
                }

        //val dbRef = db.collection("Cafes").whereEqualTo("CafeName",cname)
        /*dbRef.get().addOnSuccessListener { result->
            for (document in result){
                root.findViewById<TextView>(R.id.textView3).text = result
            }
        }*/


        return root
    }
}
