package com.seniorproject.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class HistoryAdapter(
    mContext: Context,
    mComments : List<History>,
    isFragment : Boolean
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder?>() {
    private val mContext:Context
    private val mComments: List<History>
    private val isFragment : Boolean
    init {
        this.mContext = mContext
        this.mComments = mComments
        this.isFragment = isFragment
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.history_item,viewGroup,false)
        return HistoryAdapter.ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review : History? = mComments[position]
        holder.cafename.text = review!!.cname
        holder.comment.text = review!!.reviews
        holder.time.text = review!!.timestamp
        //Log.d("rrrr",review.user_name)
        //Picasso.get().load(review.profile).into(holder.profile)
        holder.rating.stepSize = .5f
        holder.rating.rating = review!!.ratings.toFloat()
    }

    override fun getItemCount(): Int {
        return mComments.size
    }
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var cafename : TextView
        var comment : TextView
        var time : TextView
        //var profile : ImageView
        lateinit var rating : RatingBar

        init {

            cafename = itemView.findViewById(R.id.history_cafename)
            comment = itemView.findViewById(R.id.history_review)
            time = itemView.findViewById(R.id.history_date)
            //profile = itemView.findViewById(R.id.usprofile)
            rating = itemView.findViewById(R.id.history_rate)

        }

    }
}