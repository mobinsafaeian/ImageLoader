package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos.DataResponse

interface MainRecyclerViewItemViewInterface {
    fun setImage(data:DataResponse)
    fun showError(message:String?)
}