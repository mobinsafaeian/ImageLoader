package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mobinsafaeian.detfhappinesspeyk.R
import com.android.mobinsafaeian.detfhappinesspeyk.model.data.MainRecyclerViewListItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_recycler_view_item.view.*

class MainRecyclerViewAdapter(private var context: Context? , private var listItems:ArrayList<MainRecyclerViewListItem>): RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder>() {

    private lateinit var item:MainRecyclerViewListItem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_view_item , parent , false))
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: MainRecyclerViewAdapter.MyViewHolder, position: Int) {
        item = listItems[position]
        Picasso.with(context).load(item.imageItem).fit().into(holder.imageItem)
    }

    class MyViewHolder(view:View): RecyclerView.ViewHolder(view){
        var imageItem = view.main_recycler_view_image_item
    }

}