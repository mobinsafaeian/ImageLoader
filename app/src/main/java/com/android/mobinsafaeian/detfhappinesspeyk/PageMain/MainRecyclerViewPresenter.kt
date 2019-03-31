package com.android.mobinsafaeian.detfhappinesspeyk.PageMain


import android.graphics.Bitmap
import com.android.mobinsafaeian.detfhappinesspeyk.model.connections.BaseUrls
import com.android.mobinsafaeian.detfhappinesspeyk.model.connections.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import com.android.mobinsafaeian.detfhappinesspeyk.BasePackage.GlobalApplication
import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos.DataResponse
import com.android.mobinsafaeian.detfhappinesspeyk.model.data.MainRecyclerViewListItem
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageDatabase
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageInfo
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.access.DataAccess
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.access.ImageRepository
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class MainRecyclerViewPresenter(var view: MainRecyclerViewItemViewInterface) : MainPresenterInterface {
    private val getIPApi1 by lazy {
        RetrofitBuilder.create(BaseUrls.WikipediaBaseUrl)
    }
    private val imageDatabase = ImageDatabase.getInstance(GlobalApplication.getContext())
    private var pushingData: ArrayList<ImageInfo> = ArrayList()
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var imageRepository: ImageRepository? =
        ImageRepository.getInstance(DataAccess.getInstance(imageDatabase!!.ImageInfoDAO()))

    override fun unbind() {
        compositeDisposable.clear()
    }

    fun getAllDataFromServer() {
        val disposable = getIPApi1.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                saveDataToDataBase(result)
                view.setImagesFromServer(result)
            },
                { error -> view.showError(error.message) })

        compositeDisposable.add(disposable)

    }

    private fun saveDataToDataBase(data: DataResponse) {
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

        val disposable = Observable.create(ObservableOnSubscribe<Any> { observableEmitter ->
            imageRepository!!.insertSomeImages(pushingData)
            Log.i("imageInsertion" , "hi")
            observableEmitter.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe(
                { success ->
                    //success
                    Log.i("imageInsertion" , "on success")
                    Toast.makeText(GlobalApplication.getContext(), "" + success, Toast.LENGTH_SHORT).show()
                },
                { throwable ->
                    view.showError(throwable.message)
                    Log.i("imageInsertion" , "on failure")
                    Toast.makeText(GlobalApplication.getContext(), "" + throwable, Toast.LENGTH_SHORT).show()
                }, { Toast.makeText(GlobalApplication.getContext(), "action", Toast.LENGTH_SHORT).show() })
        compositeDisposable.add(disposable)

    }

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

    fun deleteAllDataFromDatabase(){
        val disposable = Observable.create(ObservableOnSubscribe<Any> { deleteResult ->
            imageRepository!!.deleteAllImages()
            deleteResult.onComplete()
        })
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {result -> Log.i("DeleteDataFromDbResult" , result.toString())} ,
                {error -> Log.i("DeleteDataFromDbError" , error.toString())})
        compositeDisposable.add(disposable)
    }
}