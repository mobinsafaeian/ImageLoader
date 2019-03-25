package com.android.mobinsafaeian.detfhappinesspeyk.BasePackage


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.android.mobinsafaeian.detfhappinesspeyk.PageMain.MainRecyclerViewPresenter
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket


class NetworkChecking(private val context: Context, private val presenter:MainRecyclerViewPresenter) : BroadcastReceiver() {


    override fun onReceive(p0: Context?, p1: Intent?) {
        hasInternetConnection().subscribe { hasInternet ->
            if (hasInternet){
                Toast.makeText(context , "successful connection:D" , Toast.LENGTH_SHORT).show()
                presenter.getAllDataFromServer()
            }
            else Toast.makeText(context , "connection problem..." , Toast.LENGTH_SHORT).show()
        }
    }

    private fun hasInternetConnection(): Single<Boolean> {
        return Single.fromCallable {
            try {
                // Connect to Google DNS to check for connection
                val timeoutMs = 1500
                val socket = Socket()
                val socketAddress = InetSocketAddress("8.8.8.8", 53)

                socket.connect(socketAddress, timeoutMs)
                socket.close()

                true
            } catch (e: IOException) {
                false
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}