package com.seniorproject.test

import android.app.ActionBar
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

class DetailcafeFragment : Fragment() {
    //private lateinit var locationViewModel: LocationViewModel
    private lateinit var reference: DocumentReference
    var cname: String? = ""
    private lateinit var actionBar:ActionBar;
    //private lateinit var MenuModelArrayList : ArrayList<MenuModel>
    private lateinit var MenuAdapter:MenuAdapter
    //private lateinit var mMap: GoogleMap
    //private var LOCATION_PERMISSION_REQUEST = 1
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


        //.collection("Foods").document("food1")
//        MenuModelArrayList = ArrayList()
//        try{
//            db.collection("Cafes").whereEqualTo("CafeName",true)
//            //db.collection("Cafes").document("Cafes/Boxgallery-Nakhonpathom/")
//                .get()
//                .addOnSuccessListener { document ->
//                    if (document != null) {
//
//                        Log.d("food", "DocumentSnapshot data: ${document.data}")
//                    } else {
//                        Log.d("food", "No such document")
//                    }
//
////                        val foods = MenuModelArrayList
////                        for (document in task.result!!){
////                            foods.add(document.data))
////
////                        }
//                    }
//                }
//        catch (e : Exception){ }
//
//
//
//
//        //init list
//        MenuModelArrayList = ArrayList()
//        MenuModelArrayList
//            .add(MenuModel("https://scontent.fbkk22-4.fna.fbcdn.net/v/t31.0-8/22050985_1434689463279899_8508095488147149541_o.jpg?_nc_cat=111&ccb=3&_nc_sid=0aa96f&_nc_eui2=AeEMRI_PdEst5Wjb69ooKxJMNkHbgEFnkvA2QduAQWeS8JXZUWkiKYnm2M3QRGODu1s&_nc_ohc=auB6K-vq9TwAX8Kuxth&_nc_ht=scontent.fbkk22-4.fna&oh=5df881faecc99fe8af8634e1cf7c9ef9&oe=605D1058"))
//        MenuModelArrayList
//            .add(MenuModel("https://scontent.fbkk22-3.fna.fbcdn.net/v/t31.0-8/22050943_1434689663279879_3369224191100415496_o.jpg?_nc_cat=103&ccb=3&_nc_sid=0aa96f&_nc_eui2=AeH5yQehB98nSLFA6Wqt1RVO6TEVcimRNnfpMRVyKZE2d5TIU4Vr9Echw0o_Kz5BhjU&_nc_ohc=G_S1M8jsxa4AX9F0u1I&_nc_ht=scontent.fbkk22-3.fna&oh=ba0fab92dcd5a7739fe3a48f44c4264e&oe=605A8693"))
//
//        //setup adapter
//        MenuAdapter = MenuAdapter(root.context,MenuModelArrayList)
//        //setup to viewpager
//        root.findViewById<ViewPager>(R.id.menupager).adapter = MenuAdapter
//        root.findViewById<ViewPager>(R.id.menupager).setPadding(100,0,100,0)
//
//        //add page change listener
//        root.findViewById<ViewPager>(R.id.menupager).addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                //can change title of actionbar
//            }
//
//            override fun onPageSelected(position: Int) {
//
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//
//            }
//        })
        return root
    }


}
