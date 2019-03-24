package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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
        listItems = ArrayList()
        main_recycler_view.layoutManager = LinearLayoutManager(context)
        presenter = MainRecyclerViewPresenter(this)
        presenter.getAllDataFromServer()
        adapter = MainRecyclerViewAdapter(context , listItems)
        main_recycler_view.adapter = adapter
        Log.i("mainFragment" , "hi")
    }

    override fun setImage(data:DataResponse) {
        for(i in 0 until data.query.allimages.size){
            listItems.add(MainRecyclerViewListItem(data.query.allimages[i].url))
        }
        adapter.notifyDataSetChanged()
    }

    override fun showError(message: String?) {
        Log.i("error message" , message)
    }

    override fun onPause() {
        super.onPause()
        presenter.unbind()
    }

}

