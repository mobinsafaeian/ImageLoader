package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos.DataResponse
import com.android.mobinsafaeian.detfhappinesspeyk.model.data.MainRecyclerViewListItem

interface MainRecyclerViewItemViewInterface {
    fun setImage(data:DataResponse)
    fun showError(message:String?)
}