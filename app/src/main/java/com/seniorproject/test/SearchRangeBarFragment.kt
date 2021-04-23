package com.seniorproject.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.seniorproject.test.ui.home.HomeFragment

class SearchRangeBarFragment : Fragment() {

    private var pricegroup : String = ""
    private var pg : String = ""
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.home_pricesearch, container, false)

        //close btn
        val closebtn = root.findViewById<ImageButton>(R.id.closesearchbtn)
        closebtn.setOnClickListener {
            val activity = view?.context as AppCompatActivity
            val HomeFragment = HomeFragment()
            activity.supportFragmentManager.beginTransaction().replace(R.id.pricesearchpage, HomeFragment).commit()
        }

        val price = root.findViewById<RadioGroup>(R.id.PriceGroup)
        price.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.p1 ){
                pricegroup = root.findViewById<RadioButton>(R.id.p1).text.toString()
                pg = "1"
//                Toast.makeText(this.context,lowpricecheck,Toast.LENGTH_SHORT).show()
//                Log.d("lowprice",lowpricecheck)
            }

            if (checkedId == R.id.p2 ){
                pricegroup = root.findViewById<RadioButton>(R.id.p2).text.toString()
                pg = "2"
//                Toast.makeText(this.context,lowpricecheck,Toast.LENGTH_SHORT).show()
//                Log.d("lowprice",lowpricecheck)
            }
            if (checkedId == R.id.p3 ){
                pricegroup = root.findViewById<RadioButton>(R.id.p3).text.toString()
                pg = "3"
//                Toast.makeText(this.context,lowpricecheck,Toast.LENGTH_SHORT).show()
//                Log.d("lowprice",lowpricecheck)
            }
            if (checkedId == R.id.p4 ){
                pricegroup = root.findViewById<RadioButton>(R.id.p4).text.toString()
                pg = "4"
//                Toast.makeText(this.context,lowpricecheck,Toast.LENGTH_SHORT).show()
//                Log.d("lowprice",lowpricecheck)
            }
            if (checkedId == R.id.p5 ){
                pricegroup = root.findViewById<RadioButton>(R.id.p5).text.toString()
                pg = "5"
//                Toast.makeText(this.context,lowpricecheck,Toast.LENGTH_SHORT).show()
//                Log.d("lowprice",lowpricecheck)
            }
           /* lowpricecheck.putString("lowprice",checkedId.toString())
            Toast.makeText(root.context,lowpricecheck.toString(),Toast.LENGTH_SHORT).show()*/
            //lowpricecheck = checkedId.toString()
            //Toast.makeText(root.context,pg,Toast.LENGTH_SHORT).show()
            //Log.d("lowprice",lowpricecheck)
        }



        val applybtn = root.findViewById<Button>(R.id.applybtn)
        applybtn.setOnClickListener {
            //Toast.makeText(root.context,"back home",Toast.LENGTH_SHORT).show()
            val activity = view?.context as AppCompatActivity
            val ResultFragment = SearchResultFragment()
            val bundle = Bundle()
            bundle.putString("group",pg)
            ResultFragment.arguments = bundle
            activity.supportFragmentManager.beginTransaction().replace(R.id.pricesearchpage, ResultFragment).commit()
        }

        return root

    }
}