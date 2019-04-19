package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.GlobalApplication
import com.android.mobinsafaeian.detfhappinesspeyk.R
import com.android.mobinsafaeian.detfhappinesspeyk.model.data.MainRecyclerViewListItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.main_recycler_view_item.view.*
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainRecyclerViewAdapter(private val context: Context , private var listItems:ArrayList<MainRecyclerViewListItem> ,
                              private var view:RecyclerViewDialogInterface): RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder>() {

    private lateinit var item:MainRecyclerViewListItem
    private var size:Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_view_item , parent , false))
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun removeAllItems(){
        size = listItems.size
        listItems.clear()
        if(!listItems.isEmpty()) Log.i("listItem" , "not clear")
        notifyItemRangeRemoved(0 , size)
    }


    override fun onBindViewHolder(holder: MainRecyclerViewAdapter.MyViewHolder, position: Int) {
        item = listItems[position]
        Picasso.with(context).load(item.imageItem as String).fit().into(holder.imageItem)


        holder.downloadButton.setOnClickListener {
            view.getPermissionsAndDownloadImage(listItems[holder.adapterPosition].imageItem as String , listItems[holder.adapterPosition].name)
        }

        holder.deleteButton.setOnClickListener {
            view.getPermissionsAndDeleteImage(listItems[holder.adapterPosition].name)
        }

        holder.infoButton.setOnClickListener {
            view.showInformationDialog(listItems[holder.adapterPosition].name , listItems[holder.adapterPosition].timeStamp)
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var imageItem:ImageView = view.main_recycler_view_image_item
        var downloadButton:FloatingActionButton = view.recycler_view_download_button
        var deleteButton:FloatingActionButton = view.recycler_view_delete_button
        var infoButton:FloatingActionButton = view.recycler_view_info_button
    }


}