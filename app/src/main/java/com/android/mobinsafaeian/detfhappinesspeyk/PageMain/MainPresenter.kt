package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import com.android.mobinsafaeian.detfhappinesspeyk.model.connections.BaseUrls
import com.android.mobinsafaeian.detfhappinesspeyk.model.connections.RetrofitBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(val view:MainViewInterface) : MainPresenterInterface{

    private val getIPApi by lazy {
        RetrofitBuilder.create(BaseUrls.IPBaseUrl)
    }
    private var disposable: Disposable? = null

    override fun unbind() {
        disposable?.dispose()
    }

    fun getIPFromServer(){
        disposable = getIPApi.getIp()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {result -> view.updateUi(result.ip)},
                {error -> view.showError(error.message!!)}
            )
    }
}