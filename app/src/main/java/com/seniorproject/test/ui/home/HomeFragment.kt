package com.seniorproject.test.ui.home


import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.seniorproject.test.DetailcafeFragment
import com.seniorproject.test.R
import com.seniorproject.test.SearchFragment
import com.squareup.picasso.Picasso

data class cafe(
        val CafeName: String = "",
        val CafeDes: String = "",
        val WorkTime: String = "",
        val Logo: String = ""
)
class cafeViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
interface Communicator {

    fun passDataCom(cafename: String)

}


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
        root.findViewById<RecyclerView>(R.id.rvCafes).layoutManager = LinearLayoutManager(this.context)



        //search data
        val searchbtn = root.findViewById<ImageButton>(R.id.searchbtn)

        searchbtn.setOnClickListener {
            //Toast.makeText(root.context,"go to search",Toast.LENGTH_SHORT).show()
            val activity = view?.context as AppCompatActivity
            val searchFragment = SearchFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.rec, searchFragment).commit()
        }






        return root
    }


}
