package com.seniorproject.test.ui.home


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seniorproject.test.*
import com.squareup.picasso.Picasso

class cafe(
        val CafeName: String = "",
        val CafeDes: String = "",
        val WorkTime: String = "",
        val Logo: String = "",
        val cluster: String = ""
)
class cafeViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
interface Communicator {
    fun passDataCom(cafename: String)

}


class HomeFragment() : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    var username:String? = ""
    var Cluster :String? = ""
    private var mComment : List<Info>? = null

    private lateinit var homeViewModel: HomeViewModel
    private val db = Firebase.firestore

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        //val checkuser =
        //Recommendation
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        username = currentUser?.displayName.toString()
        val firebaseDatabase = FirebaseDatabase.getInstance().reference.child("users/Info")
                .orderByChild("user_name").equalTo(username)//.equalTo(currentUser.displayName.toString())
        firebaseDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    Cluster = snapshot.child("cluster").getValue(String::class.java)
                    //Toast.makeText(root.context, Cluster, Toast.LENGTH_SHORT).show()
                    val query = db.collection("Cafes").whereEqualTo("cluster",Cluster)
                    val options = FirestoreRecyclerOptions.Builder<cafe>().setQuery(query,cafe::class.java)
                            .setLifecycleOwner(this@HomeFragment).build()
                    val adapter = object : FirestoreRecyclerAdapter<cafe,cafeViewHolder>(options){
                        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cafeViewHolder {
                            val view = LayoutInflater.from(this@HomeFragment.context).inflate(R.layout.home_item_cafes,parent,false)
                            return cafeViewHolder(view)
                        }

                        override fun onBindViewHolder(holder: cafeViewHolder, position: Int, model: cafe) {
                            val tvName:TextView = holder.itemView.findViewById(R.id.search_cafename)
                            val tvDes:TextView = holder.itemView.findViewById(R.id.result_cafedes)
                            val tvTime:TextView = holder.itemView.findViewById(R.id.result_cafewt)
                            val ivLogo :ImageView = holder.itemView.findViewById(R.id.search_cafepic)
                            tvName.text = model.CafeName
                            tvDes.text = model.CafeDes
                            tvTime.text = model.WorkTime
                            Picasso.get().load(model.Logo).into(ivLogo)
                            // see cafe detail
                            holder.itemView.setOnClickListener(object :View.OnClickListener{
                                override fun onClick(v: View?) {
                                    val activity = v!!.context as AppCompatActivity
                                    val detailcafe = DetailcafeFragment()
                                    val bundle = Bundle()
                                    bundle.putString("name",model.CafeName)
                                    detailcafe.arguments = bundle
                                    activity.supportFragmentManager.beginTransaction().replace(R.id.rec,detailcafe).addToBackStack(null).commit()

                                }
                            })
                        }
                    }
                    root.findViewById<RecyclerView>(R.id.rvCafes).adapter = adapter
                    root.findViewById<RecyclerView>(R.id.rvCafes).layoutManager = LinearLayoutManager(this@HomeFragment.context)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
        //val clustergroup : String = Cluster.toString()
        //Log.d("cluster" , Cluster.toString())




        //search data
        val searchbtn = root.findViewById<ImageButton>(R.id.searchbtn)

        searchbtn.setOnClickListener {
            //Toast.makeText(root.context,"go to search",Toast.LENGTH_SHORT).show()
            val activity = view?.context as AppCompatActivity
            val searchFragment = SearchFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.rec, searchFragment).commit()
        }


        //search range price bar
        val pricesearch = root.findViewById<ImageButton>(R.id.searchprice)
        pricesearch.setOnClickListener {
            //Toast.makeText(root.context,"go to search",Toast.LENGTH_SHORT).show()
            val activity = view?.context as AppCompatActivity
            val pricesearchFragment = SearchRangeBarFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.rec, pricesearchFragment).commit()

//            val mAlertDialog = AlertDialog.Builder(root.context)
//            mAlertDialog.setTitle("Filter By")
        }




        return root
    }


}
