package com.android.mobinsafaeian.detfhappinesspeyk.PageMain



import com.android.mobinsafaeian.detfhappinesspeyk.model.connections.BaseUrls
import com.android.mobinsafaeian.detfhappinesspeyk.model.connections.RetrofitBuilder
import com.android.mobinsafaeian.detfhappinesspeyk.model.data.MainRecyclerViewListItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainRecyclerViewPresenter(var view:MainRecyclerViewItemViewInterface) : MainPresenterInterface {
    private val getIPApi1 by lazy {
        RetrofitBuilder.create(BaseUrls.WikipediaBaseUrl)
    }
    private var disposable: Disposable? = null
    private var listItems:List<MainRecyclerViewListItem> = ArrayList()

    override fun unbind() {
        disposable?.dispose()
    }
    fun getAllDataFromServer() {
            disposable = getIPApi1.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ result -> view.setImage(result) } ,
                            { error -> view.showError(error.message) })

    }
}