package com.example.sample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.database.DBClass
import com.example.sample.modelClasses.FavItem
import com.squareup.picasso.Picasso

class FavListAdapter(private val favItems: ArrayList<FavItem>, private val context: Context) :
    RecyclerView.Adapter<FavListAdapter.ViewHolder>() {

    private var dbClass: DBClass? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        dbClass = DBClass(context)
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.fav_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.favTextView.setText(favItems.get(position).getFav_title());
        Picasso.get().load(favItems[position].fav_image)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.favImageView)
    }

    override fun getItemCount(): Int {
        return favItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var favImageView: ImageView = itemView.findViewById(R.id.fav_image)

        //        TextView favTextView;
        var favBtn: Button = itemView.findViewById(R.id.fav_button)

        init {
            //            favTextView = itemView.findViewById(R.id.fav_textview);
            favBtn.setOnClickListener {
                val position = adapterPosition
                val favItem: FavItem = favItems[position]
                dbClass!!.removeFav(favItem.fav_id)
                removeItem(position)
            }
        }
    }

    private fun removeItem(position: Int) {
        favItems.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, favItems.size)
    }

}