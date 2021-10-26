package com.example.sample.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.adapter.FavListAdapter
import com.example.sample.database.DBClass
import com.example.sample.modelClasses.FavItem
import java.util.*

class FavouriteFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var dbClass: DBClass? = null
    private val favItems: ArrayList<FavItem> = ArrayList<FavItem>()
    private var adapter: FavListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_favourite, container, false)

        dbClass = DBClass(activity)
        recyclerView = root.findViewById(R.id.ff_rl_fav_images)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = GridLayoutManager(
            root.context,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        loadData()
        return root
    }

    @SuppressLint("Range")
    private fun loadData() {
        if (favItems != null) {
            favItems.clear()
        }
        val db = dbClass!!.readableDatabase
        val cursor = dbClass!!.selectAllFav()
        try {
            while (cursor.moveToNext()) {
//                String title = cursor.getString(cursor.getColumnIndex(dbClass.ITEM_TITLE));
                val id = cursor.getString(cursor.getColumnIndex("0")).toInt()
                val image = cursor.getString(cursor.getColumnIndex("1"))
                //                String description = cursor.getString(cursor.getColumnIndex(dbClass.ITEM_DESCRIPTION));
                val favItem = FavItem(id, image)
                favItems.add(favItem)
            }
        } finally {
            if (cursor.isClosed) {
                cursor.close()
            }
            db.close()
        }
        adapter = FavListAdapter(favItems, requireActivity())
        recyclerView!!.adapter = adapter
    }

}