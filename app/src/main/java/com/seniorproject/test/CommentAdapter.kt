package com.seniorproject.test

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CommentAdapter(
    mContext: Context,
    mComments : List<Comment>,
    isFragment : Boolean
) : RecyclerView.Adapter<CommentAdapter.ViewHolder?>()
{
    private val mContext:Context
    private val mComments: List<Comment>
    private val isFragment : Boolean
    init {
        this.mContext = mContext
        this.mComments = mComments
        this.isFragment = isFragment
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(mContext).inflate(R.layout.detail_reviews_item,viewGroup,false)
        return CommentAdapter.ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review : Comment? = mComments[position]
        holder.username.text = review!!.user_name
        holder.comment.text = review!!.reviews
        holder.time.text = review!!.timestamp
        //Log.d("rrrr",review.user_name)
        Picasso.get().load(review.profile).into(holder.profile)
    }

    override fun getItemCount(): Int {
        return mComments.size
    }
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var username : TextView
        var comment : TextView
        var time : TextView
        var profile : ImageView

        init {

            username = itemView.findViewById(R.id.user_name)
            comment = itemView.findViewById(R.id.comment)
            time = itemView.findViewById(R.id.time)
            profile = itemView.findViewById(R.id.usprofile)

        }

    }

}