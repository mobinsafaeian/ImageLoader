package com.android.mobinsafaeian.detfhappinesspeyk.PageMain


import android.Manifest
import android.app.AlertDialog
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.BaseActivity
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.NetworkChecking
import com.android.mobinsafaeian.detfhappinesspeyk.R
import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos.DataResponse
import com.android.mobinsafaeian.detfhappinesspeyk.model.data.MainRecyclerViewListItem
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageInfo
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), MainViewInterface , RecyclerViewDialogInterface {

    //definitions
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MainRecyclerViewAdapter
    private lateinit var listItems:ArrayList<MainRecyclerViewListItem>
    private lateinit var intentFilter: IntentFilter
    private lateinit var networkChecking: NetworkChecking
    private var name:String? = null
    private var url:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initializations
        init()




    }

    /**
     * initializations
     */
    private fun init() {
        presenter = MainPresenter(this)
        //get IP from server
        presenter.getIPFromServer()
        intentFilter = IntentFilter()
        listItems = ArrayList()
        main_recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = MainRecyclerViewAdapter(this , listItems , presenter , this)
        main_recycler_view.adapter = adapter
        networkChecking = NetworkChecking(this , presenter , adapter)
        delete_the_folder.setOnClickListener {
            presenter.deleteImageFolder()
        }
    }


    /**
     * whenever data received from server , this method will be called by presenter to update the ui
     * @param ip is the user ip that retrieved from server
     */
    override fun onSuccessGettingIp(ip: String) {
        ip_progress_bar.visibility = View.GONE
        ip_text.visibility = View.VISIBLE
        ip_text.text = ip
    }

    /**
     * whenever retrieving data faced with a problem , presenter will call this method to show the error message
     * @param message is the error message that should be shown
     */
    override fun onFailureGettingIp(message: String) {
        ip_progress_bar.visibility = View.GONE
        ip_text.visibility = View.VISIBLE
        ip_text.text = "..."
    }

    /**
     * if the response of retrieving data was correct and successful ,
     * this function will be called to pass parse data and pass them to adapter
     */
    override fun setImagesFromServer(data: DataResponse) {
        listItems.clear()
        for(i in 0 until data.query.allimages.size){
            listItems.add(MainRecyclerViewListItem(data.query.allimages[i].url ,
                data.query.allimages[i].name ,
                data.query.allimages[i].timestamp))
        }
        adapter.notifyDataSetChanged()
    }

    /**
     * if the retrieving data from server was impossible ,
     * this function will be called to pass data that loaded from database and pass them to adapter
     */
    override fun setImagesFromDatabase(data: List<ImageInfo>) {
        listItems.clear()
        for (i in 0 until data.size){
            listItems.add(MainRecyclerViewListItem(data[i].image , data[i].name , data[i].timeStamp))
        }
        adapter.notifyDataSetChanged()
    }


    /**
     * if the response of retrieving data faced with a problem ,
     * this function will be called to log the error
     */
    override fun showError(message: String?) {
        Log.i("error message" , message)
    }

    /**
     * when user clicks the information button , this function will be called to show a dialog
     */
    override fun showInformationDialog(name: String, timeStamp: String) {
        AlertDialog.Builder(this)
            .setTitle("Image Information")
            .setMessage("name : $name \ntime stamp : $timeStamp")
            .create()
            .show()
    }

    /**
     *  when user clicks the download button , this function will be called to take permissions and save images
     */
    override fun getPermissionsAndDownloadImage(url:String , name:String) {
        this.name = name
        this.url = url
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    presenter.downloadImage(url , name)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("permission needed!")
                        .setMessage("to download the images , you need get the permission")
                        .setPositiveButton(
                            "yes"
                        ) { dialog, which ->
                            getPermissionsAndDownloadImage(url , name)
                        }
                        .setNegativeButton(
                            "no"
                        ) { dialog, which ->
                            dialog.dismiss() }
                        .create().show()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(this@MainActivity , " :(" , Toast.LENGTH_SHORT).show()
                }
            }).check()
    }
    /**
     * when app is in onPause mode , we should call the unbind() method to dispose disposable variable
     */
    /*override fun onPause() {
        super.onPause()
        presenter.unbind()
    }*/

    // show a simple exit dialog when user clicks on backPressedButton
    override fun onBackPressed() {
        exitDialog("Exit", "Do you want to exit?", "yes", "no")
    }

    /**
     * when app starts to load , the networkChecking instance should register as a receiver for listening to connection status
     */
    override fun onStart() {
        super.onStart()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChecking , intentFilter)
    }



    /**
     * when app is in onPause mode , we should call the unbind() method to dispose disposable variable
     * also the networkChecking instance should unregister
     */
    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChecking)
        presenter.unbind()
        networkChecking.unbind()
    }
}
