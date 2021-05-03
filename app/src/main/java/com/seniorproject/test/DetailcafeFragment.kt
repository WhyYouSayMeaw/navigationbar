package com.seniorproject.test

import android.app.ActionBar
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.seniorproject.test.ui.home.HomeFragment
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.google.firebase.database.*

class DetailcafeFragment : Fragment() {
    //private lateinit var locationViewModel: LocationViewModel
    private lateinit var reference: DocumentReference
    var cname: String? = ""
    var username:String? = ""
    var rate:String? = "0.0"
    private lateinit var actionBar:ActionBar;
    private lateinit var MenuAdapter:MenuAdapter
    private lateinit var ratingbar:RatingBar
    private lateinit var review:EditText
    private lateinit var submitbtn:Button
    private lateinit var mAuth: FirebaseAuth
    private var CommentAdapter:CommentAdapter? = null
    private var mComment : List<Comment>? = null
    private var recyclerView:RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detailcafe, container, false)

        cname = arguments?.getString("name")
        root.findViewById<TextView>(R.id.textView2).text = cname
        val db = FirebaseFirestore.getInstance()
        db.collection("Cafes").whereEqualTo("CafeName",cname)
                .get()
                .addOnCompleteListener {
                    var  desc= String()
                    var  ad = String()
                    var  wt = String()
                    var  logolink = String()
                    var img =root.findViewById<ImageView>(R.id.logoimg)
                    if (it.isSuccessful){
                        for (document in it.result!!){
                            desc = document.getString("CafeDes").toString()
                            wt = document.getString("WorkTime").toString()
                            ad = document.getString("Address").toString()
                            logolink = document.getString("Logo").toString()
                        }
                        root.findViewById<TextView>(R.id.textView3).setText(desc)
                        root.findViewById<TextView>(R.id.textView4).setText(ad)
                        root.findViewById<TextView>(R.id.textView5).setText(wt)
                        Picasso.get().load(logolink).into(img)
                    }
                }

        //LoadMenuPics
//        val storage = Firebase.storage
//        val listRef = storage.reference.child("files/uid")
        val storage = FirebaseStorage.getInstance()
//        val storageref = storage.reference.child("Cafes/Boxgallery-Nakhonpathom/Foods")
        val foodstorageref = storage.reference.child("Cafes/"+cname+"/Foods")
        val foodimagelist : ArrayList<Menu> = ArrayList()

        val foodlistallTask : Task<ListResult> = foodstorageref.listAll()
        foodlistallTask.addOnCompleteListener { result ->
            val items : List<StorageReference> = result.result!!.items
            //add cycler for add imageurl
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    Log.d("item","$it")
                    foodimagelist.add(Menu(it.toString()))
                }.addOnCompleteListener {
                    root.findViewById<RecyclerView>(R.id.menurecycler).adapter = MenuAdapter(foodimagelist,root.context)
                    root.findViewById<RecyclerView>(R.id.menurecycler).layoutManager = LinearLayoutManager(root.context,LinearLayoutManager.HORIZONTAL ,false)
                }
            }
        }

        //loadCatPics
        val catstorageref = storage.reference.child("Cafes/"+cname+"/cats")
        val catimagelist : ArrayList<Cats> = ArrayList()
        val catlistallTask: Task<ListResult> = catstorageref.listAll()
        catlistallTask.addOnCompleteListener { result ->
            val items : List<StorageReference> = result.result!!.items
            //add cycler for add imageurl
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    Log.d("item","$it")
                    catimagelist.add(Cats(it.toString()))
                }.addOnCompleteListener {
                    root.findViewById<RecyclerView>(R.id.catrecycler).adapter = CatsAdapter(catimagelist,root.context)
                    root.findViewById<RecyclerView>(R.id.catrecycler).layoutManager = LinearLayoutManager(root.context,LinearLayoutManager.HORIZONTAL ,false)
                }
            }
        }


        //LoadCafePics
        val cafestorageref = storage.reference.child("Cafes/"+cname+"/cafePic")
        val cafeimagelist : ArrayList<CatsPic> = ArrayList()
        val cafelistallTask: Task<ListResult> = cafestorageref.listAll()
        cafelistallTask.addOnCompleteListener { result ->
            val items : List<StorageReference> = result.result!!.items
            //add cycler for add imageurl
            items.forEachIndexed { index, item ->
                item.downloadUrl.addOnSuccessListener {
                    Log.d("item","$it")
                    cafeimagelist.add(CatsPic(it.toString()))
                }.addOnCompleteListener {
                    root.findViewById<RecyclerView>(R.id.caferecycler).adapter = CatsPicAdapter(cafeimagelist,root.context)
                    root.findViewById<RecyclerView>(R.id.caferecycler).layoutManager = LinearLayoutManager(root.context,LinearLayoutManager.HORIZONTAL,false)
                }
            }
        }

        //review
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        username = currentUser?.displayName.toString()
        //Log.d("profile",currentUser.photoUrl.toString())
        //username = arguments?.getString("username")
        //username = mAuth.currentUser.displayName
        //Toast.makeText(this.context,username,Toast.LENGTH_SHORT).show()
        ratingbar = root.findViewById(R.id.ratingBar)
        ratingbar.rating = 0.0f
        ratingbar.stepSize = .5f
        ratingbar.setOnRatingBarChangeListener { ratingBar, fl, b ->
            //Toast.makeText(this.context,"Rating : $fl",Toast.LENGTH_SHORT).show()
            rate = fl.toFloat().toString()
        }
        review = root.findViewById(R.id.reviewtext)
        submitbtn = root.findViewById(R.id.submitbtn)

        //input data
        submitbtn.setOnClickListener {
            //InputData()
            val user_name : String = username.toString()
            val ratings : String = rate.toString()
            val reviews : String = review.text.toString().trim()
            val cafename : String = cname.toString()
            val profile = currentUser.photoUrl.toString()
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            val formatted = current.format(formatter)

            val database = FirebaseDatabase.getInstance().getReference("reviews")
            database.child(cname.toString()).child("Ratings").child(database.push().key.toString()).setValue(Reviews(cafename,user_name,profile,ratings,reviews,formatted))
            //Log.d("review","$cname : $user_name : $ratings : $reviews : $formatted : $profile")
            .addOnSuccessListener {
                //Toast.makeText(this.context,"Successfully...",Toast.LENGTH_SHORT).show()
                //root.findViewById<CardView>(R.id.cardView).visibility = View.INVISIBLE
                //root.findViewById<ScrollView>(R.id.cafedeatailpage).fullScroll(ScrollView.SCROLL_INDICATOR_TOP)
                val activity = view?.context as AppCompatActivity
                val popup = ThankPopUp()
                val bundle = Bundle()
                bundle.putString("cname",cname)
                popup.arguments = bundle
                activity.supportFragmentManager.beginTransaction().replace(R.id.detailpg, popup).commit()
                ratingbar.rating = 0.0f
                review.setText("")

            }
            val database2 = FirebaseDatabase.getInstance().getReference("users")
            database2.child("Reviews").child(database2.push().key.toString()).setValue(Reviews(cafename,user_name,profile,ratings,reviews,formatted))
        }

        //show comment in recyclerview
        //group = arguments?.getString("group")
        //root.findViewById<TextView>(R.id.group).text = group
        recyclerView = root.findViewById(R.id.allcomment)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        mComment = ArrayList()
        retrieveAllComment()



        return root
    }

    private fun retrieveAllComment() {
        val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("reviews/"+cname+"/Ratings")
        firebaseDatabase.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot) {

                    for (snapshot in p0.children)
                    {
                        val reviews : Comment? = snapshot.getValue(Comment::class.java)
                        //if (reviews != null)
                        //{
                            (mComment as ArrayList<Comment>).add(reviews!!)
                        //}
                    }

                CommentAdapter = CommentAdapter(context!!,mComment!!,false)
                recyclerView!!.adapter = CommentAdapter


            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
/*
    private fun InputData() {
        val user_name: String? = username
        val rating: String = "" + ratingbar.rating
        val review: String = review.text.toString().trim()

        //for time of review
        val timestamp: String = "" + System.currentTimeMillis()

        //setup data in hashmap
        val hashmap: HashMap<String, String> = HashMap<String,String>()
        //hashmap.put("username",""+user_name)
        hashmap.put("ratings",""+rating)
        hashmap.put("reviews",""+review)
        //hashmap.put("timestamp",""+timestamp)

        //put to firebase Users>>Cafe Name>>Rating
        val database = FirebaseDatabase.getInstance().getReference("Users")
        database.child(cname.toString()).child("Ratings").child(user_name.toString()).updateChildren(hashmap as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this.context,"success",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this.context,"not success",Toast.LENGTH_SHORT).show()
                }
    }*/


}
