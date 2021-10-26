package com.example.sample.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.database.DBClass
import com.example.sample.modelClasses.ImageDetails
import com.squareup.picasso.Picasso

class ImageListAdapter(private val context: Context, private val list: List<ImageDetails>) :
    RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    private lateinit var dbClass: DBClass
    //private val clickListner: ItemClickListner = clickListner

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        dbClass = DBClass(context)

        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)
        if (firstStart) {
            createTableOnFirstStart()
        }
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: ImageDetails = list[position]
        readCursorData(item, holder)
        Picasso.get().load(item.imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.ivImage)
        //        holder.tvTitle.setText(item.getTitle());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clickListner.onItemClick(list.get(position));
//            }
//        });
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivImage: ImageView = itemView.findViewById(R.id.il_imageView)
        //var tvTitle: TextView? = null
        var btnFavorite: Button = itemView.findViewById(R.id.il_favBtn)

        init {
            //            tvTitle = itemView.findViewById(R.id.il_titleTextView);
            btnFavorite.setOnClickListener {
                val position = adapterPosition
                val listItem: ImageDetails = list[position]
                if (listItem.getFavStatus().equals("0")) {
                    listItem.setFavStatus("1")
                    dbClass.insertIntoDatabase(
                        listItem.imageUrl,
                        listItem.imageId,
                        listItem.getFavStatus()
                    )
                    btnFavorite.setBackgroundResource(R.drawable.red)
                } else {
                    listItem.setFavStatus("0")
                    dbClass.removeFav(listItem.imageId)
                    btnFavorite.setBackgroundResource(R.drawable.grey)
                }
            }
        }
    }

    private fun createTableOnFirstStart() {
        dbClass.insertEmpty()
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()
    }

    @SuppressLint("Range")
    private fun readCursorData(listItem: ImageDetails, viewHolder: ViewHolder) {
        val cursor: Cursor = dbClass.readAllData(listItem.imageId)
        val db: SQLiteDatabase = dbClass.readableDatabase
        try {
            while (cursor.moveToNext()) {
                val itemFavStatus = cursor.getString(cursor.getColumnIndex(DBClass.FAVORITE_STATUS))
                listItem.setFavStatus(itemFavStatus)
                if (itemFavStatus != null && itemFavStatus == "1") {
                    viewHolder.btnFavorite.setBackgroundResource(R.drawable.red)
                } else if (itemFavStatus != null && itemFavStatus == "0") {
                    viewHolder.btnFavorite.setBackgroundResource(R.drawable.grey)
                }
            }
        } finally {
            if (cursor.isClosed) {
                cursor.close()
            }
            db.close()
        }
    }

    interface ItemClickListner {
        fun onItemClick(imageDetails: ImageDetails?)
    }

}