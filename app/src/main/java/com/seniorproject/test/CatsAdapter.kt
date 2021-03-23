package com.seniorproject.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CatsAdapter (private val items:List<Cats>, private val context: Context):
        RecyclerView.Adapter<CatsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
              LayoutInflater.from(context).inflate(
                      R.layout.detail_catpic,
                      parent,
                      false
              )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load(item.Imageurl).into(holder.catimageView)
    }



    class ViewHolder (view: View):RecyclerView.ViewHolder(view){
        val catimageView: ImageView = view.findViewById(R.id.catimageView)

    }
}