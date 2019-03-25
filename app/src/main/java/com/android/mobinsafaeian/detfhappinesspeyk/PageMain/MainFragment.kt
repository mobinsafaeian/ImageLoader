package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.NetworkChecking
import com.android.mobinsafaeian.detfhappinesspeyk.R
import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos.DataResponse
import com.android.mobinsafaeian.detfhappinesspeyk.model.data.MainRecyclerViewListItem
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() , MainRecyclerViewItemViewInterface {


    //inflate the xml file to view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main , container , false)
    }

    //definitions
    private lateinit var presenter: MainRecyclerViewPresenter
    private lateinit var adapter: MainRecyclerViewAdapter
    private lateinit var listItems:ArrayList<MainRecyclerViewListItem>
    private lateinit var intentFilter:IntentFilter
    private lateinit var networkChecking: NetworkChecking

    //main method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializations
        init()

    }

    /**
     * initializations
     */
    private fun init() {
        intentFilter = IntentFilter()
        listItems = ArrayList()
        main_recycler_view.layoutManager = LinearLayoutManager(context)
        presenter = MainRecyclerViewPresenter(this)
        networkChecking = NetworkChecking(context!! , presenter)
        adapter = MainRecyclerViewAdapter(context , listItems)
        main_recycler_view.adapter = adapter
    }

    /**
     * if the response of retrieving data was correct and successful , this function will be called to pass parse data and pass them to adapter
     */
    override fun setImage(data:DataResponse) {
        for(i in 0 until data.query.allimages.size){
            listItems.add(MainRecyclerViewListItem(data.query.allimages[i].url))
        }
        adapter.notifyDataSetChanged()
    }

    /**
     * if the response of retrieving data faced with a problem , this function will be called to log the error
     */
    override fun showError(message: String?) {
        Log.i("error message" , message)
    }

    /**
     * when app starts to load , the networkChecking instance should register as a receiver for listening to connection status
     */
    override fun onStart() {
        super.onStart()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context!!.registerReceiver(networkChecking , intentFilter)
    }

    /**
     * when app is in onPause mode , we should call the unbind() method to dispose disposable variable
     * also the networkChecking instance should unregister
     */
    override fun onPause() {
        super.onPause()
        context!!.unregisterReceiver(networkChecking)
        presenter.unbind()
    }


}

