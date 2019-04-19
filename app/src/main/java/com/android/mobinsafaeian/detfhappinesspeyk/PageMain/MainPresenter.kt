package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.GlobalApplication
import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos.DataResponse
import com.android.mobinsafaeian.detfhappinesspeyk.model.connections.BaseUrls
import com.android.mobinsafaeian.detfhappinesspeyk.model.connections.RetrofitBuilder
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageDatabase
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageInfo
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.access.DataAccess
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.access.ImageRepository
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class MainPresenter(private val view:MainViewInterface) : MainPresenterInterface{

    //definitions
    private val getIPApi by lazy {
        RetrofitBuilder.create(BaseUrls.IPBaseUrl)
    }

    private val getDataApi by lazy {
        RetrofitBuilder.create(BaseUrls.WikipediaBaseUrl)
    }
    private val imageDatabase = ImageDatabase.getInstance(GlobalApplication.getContext())
    private var pushingData: ArrayList<ImageInfo> = ArrayList()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var imageRepository: ImageRepository? =
        ImageRepository.getInstance(DataAccess.getInstance(imageDatabase!!.ImageInfoDAO()))

    private lateinit var target: Target
    val root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()

    /**
     * this method will be called when app is on onPause mode.
     * in this situation we need to clear compositeDisposable object to prevent memory leaks
     */
    override fun unbind() {
        compositeDisposable.clear()
    }

    fun getIPFromServer(){
        val disposable = getIPApi.getIp()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {result -> view.onSuccessGettingIp(result.ip)},
                {error -> view.onFailureGettingIp(error.message!!)}
            )
        compositeDisposable.add(disposable)
    }
    /**
     * retrofit callback
     * this function gets images from server and passes the result to view
     */
    fun getAllDataFromServer() {
        val disposable = getDataApi.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                saveDataToDataBase(result)
                view.setImagesFromServer(result)
            },
                { error -> view.showError(error.message) })

        compositeDisposable.add(disposable)

    }

    /**
     * room callback
     * this function saves the information (that came from the server) to database
     */
    private fun saveDataToDataBase(data: DataResponse) {
        //parse data
        for (i in 0 until data.query.allimages.size) {
            pushingData.add(
                ImageInfo(
                    data.query.allimages[i].name,
                    data.query.allimages[i].url,
                    data.query.allimages[i].timestamp
                )
            )
            Log.i("pushing data" , pushingData[i].name + " " + pushingData[i].image + " " + pushingData[i].timeStamp)
        }
        //room callback and insert the images
        val disposable = Observable.create(ObservableOnSubscribe<Any> { observableEmitter ->
            imageRepository!!.insertSomeImages(pushingData)
            Log.i("imageInsertion" , "hi")
            observableEmitter.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { success ->
                    //success
                    Log.i("imageInsertion" , "on success")
                    Toast.makeText(GlobalApplication.getContext(), "" + success, Toast.LENGTH_SHORT).show()
                },
                { throwable ->
                    // error
                    view.showError(throwable.message)
                    Log.i("imageInsertion" , "on failure")
                    Toast.makeText(GlobalApplication.getContext(), "" + throwable, Toast.LENGTH_SHORT).show()
                } , {})
        compositeDisposable.add(disposable)

    }

    /**
     * room callback
     * this function gets all data from database
     */
    fun getAllDataFromDatabase() {
        val disposable = imageRepository!!.getAllImages
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ images ->
                view.setImagesFromDatabase(images)
            }, { error ->
                view.showError(error.message)
            })
        compositeDisposable.add(disposable)
    }

    /**
     * room callback
     * this function deletes all data in database
     */
    fun deleteAllDataFromDatabase(){
        val disposable = Observable.create(ObservableOnSubscribe<Any> { deleteResult ->
            imageRepository!!.deleteAllImages()
            deleteResult.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {result -> Log.i("DeleteDataFromDbResult" , result.toString()) } ,
                {error -> Log.i("DeleteDataFromDbError" , error.toString()) })
        compositeDisposable.add(disposable)
    }


    /**
     * this function loads image using picasso and creates a directory in Downloads directory and save the images in it
     * because the target is being garbage collected , we have to use a global object of target
     */
    fun downloadImage(url:String , name:String) {
        target = object : Target{
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            override fun onBitmapFailed(errorDrawable: Drawable?) {}
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                try {
                    var myDir = File("$root/mobin")

                    if(!myDir.exists())
                        myDir.mkdir()
                    myDir = File(myDir , name)
                    val out = FileOutputStream(myDir)
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG , 90 , out)

                    Toast.makeText(GlobalApplication.getContext() , "file $name saved" , Toast.LENGTH_SHORT).show()
                    out.flush()
                    out.close()
                }
                catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        Picasso.with(GlobalApplication.getContext())
            .load(url)
            .into(target)
    }

    /**
     * delete an image from file
     */
    fun deleteImageFromFile(fileName:String){
        val myFile = File("$root/mobin/$fileName")
        var deleted = false
        if (myFile.exists()){
            deleted = myFile.delete()
        }
        else{
            Toast.makeText(GlobalApplication.getContext() , "this image not exists" , Toast.LENGTH_SHORT).show()
            return
        }
        if (deleted) Toast.makeText(GlobalApplication.getContext() , "image deleted" , Toast.LENGTH_SHORT).show()
        else Toast.makeText(GlobalApplication.getContext() , "image not deleted" , Toast.LENGTH_SHORT).show()
    }

    /**
     * delete the folder
     */
    fun deleteImageFolder(){
        val myFile = File("$root/mobin/")
        var deleted = false
        if (myFile.exists()){
            val files = myFile.listFiles()
            for(i in 0 until files.size){
                if (!files[i].isDirectory){
                    files[i].delete()
                }
            }
            deleted = myFile.delete()
        }
        else{
            Toast.makeText(GlobalApplication.getContext() , "folder mobin not exists" , Toast.LENGTH_SHORT).show()
            return
        }
        if (deleted) Toast.makeText(GlobalApplication.getContext() , "folder mobin deleted" , Toast.LENGTH_SHORT).show()
        else Toast.makeText(GlobalApplication.getContext() , "folder mobin not deleted" , Toast.LENGTH_SHORT).show()
    }
}