package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.mobinsafaeian.detfhappinesspeyk.R
import com.android.mobinsafaeian.detfhappinesspeyk.model.data.MainRecyclerViewListItem
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.main_recycler_view_item.view.*
import android.graphics.BitmapFactory
import android.util.Log
import java.io.IOException
import java.net.URL


class MainRecyclerViewAdapter(private var context: Context? , private var listItems:ArrayList<MainRecyclerViewListItem> ,
                              private var presenter: MainRecyclerViewPresenter): RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder>() {

    private lateinit var item:MainRecyclerViewListItem
    private var size:Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerViewAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_view_item , parent , false))
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    fun removeItem(){
        size = listItems.size
        listItems.clear()
        notifyItemRangeRemoved(0 , size)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewAdapter.MyViewHolder, position: Int) {
        item = listItems[position]
        Picasso.with(context).load(item.imageItem as String).fit().into(holder.imageItem)

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        var imageItem:ImageView = view.main_recycler_view_image_item
        private lateinit var alphaValueAnimator:ValueAnimator
        private var animatedValue:Float = 0f
        private var alphaFlag = false
        init {
            view.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            /*if (!alphaFlag) cardAnimation(0f , 0.4f , imageItem)
            else cardAnimation(0.4f , 0f , imageItem)
            alphaFlag = !alphaFlag*/
        }

        private fun cardAnimation(initialValue:Float, finalValue:Float, view:View){
            alphaValueAnimator = ValueAnimator.ofFloat(initialValue , finalValue)
            alphaValueAnimator.addUpdateListener {
                animatedValue = it.animatedValue as Float
                view.alpha = animatedValue
                view.requestLayout()
            }
            alphaValueAnimator.duration = 200
            alphaValueAnimator.start()
        }

    }


}