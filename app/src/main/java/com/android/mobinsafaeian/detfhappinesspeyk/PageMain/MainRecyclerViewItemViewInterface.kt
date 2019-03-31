package com.android.mobinsafaeian.detfhappinesspeyk.PageMain

import com.android.mobinsafaeian.detfhappinesspeyk.model.Pojo.MainDataPojos.DataResponse
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageInfo

interface MainRecyclerViewItemViewInterface {
    fun setImagesFromServer(data:DataResponse)
    fun showError(message:String?)
    fun setImagesFromDatabase(data:List<ImageInfo>)
}