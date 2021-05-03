package com.seniorproject.test


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
class MenuAdapter (private val items:List<Menu>, private val context: Context):
    RecyclerView.Adapter<MenuAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.detail_picture,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MenuAdapter.ViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load(item.Imageurl).into(holder.imageView)
    }
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val imageView:ImageView = view.findViewById(R.id.imgView)
    }
}

//class MenuAdapter(private val context: Context,private val MenuModelArrayList : ArrayList<MenuModel>):
//    PagerAdapter() {
//    override fun getCount(): Int {
//        return MenuModelArrayList.size // return list of records/items
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view == `object`
//    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val view = LayoutInflater.from(context).inflate(R.layout.detail_picture,container,false)
//        //get data
//        val model = MenuModelArrayList[position]
//        val menuimg = model.menuurl
//        //set data to ui views
//        Picasso.get().load(menuimg).into( view.findViewById<ImageView>(R.id.imageView))
//        //add view to container
//        container.addView(view,position)
//
//        return view
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        container.removeView(`object` as View)
//    }
//}