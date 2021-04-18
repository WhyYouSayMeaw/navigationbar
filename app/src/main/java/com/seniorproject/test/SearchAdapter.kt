package com.seniorproject.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class SearchAdapter(
        mContext: Context,
        mCafes : List<Search_Cafes>,
        isFragment : Boolean
) : RecyclerView.Adapter<SearchAdapter.ViewHolder?>()
{
    private val mContext:Context
    private val mCafes: List<Search_Cafes>
    private val isFragment : Boolean
    init {
        this.mContext = mContext
        this.mCafes = mCafes
        this.isFragment = isFragment
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(mContext).inflate(R.layout.cafe_search_item,viewGroup,false)
        return SearchAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val searchcafes : Search_Cafes? = mCafes[position]
        holder.cafename.text = searchcafes!!.cafename
        Picasso.get().load(searchcafes.cafepic).into(holder.cafepic)
    }

    override fun getItemCount(): Int {
        return mCafes.size
    }



    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
            var cafename : TextView
            var cafepic : ImageView

            init {

                cafename = itemView.findViewById(R.id.search_cafename)
                cafepic = itemView.findViewById(R.id.search_cafepic)
            }
    }


}

