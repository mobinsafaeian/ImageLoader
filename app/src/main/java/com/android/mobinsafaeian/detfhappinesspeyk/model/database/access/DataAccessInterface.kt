package com.android.mobinsafaeian.detfhappinesspeyk.model.database.access

import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageInfo
import io.reactivex.Flowable

interface DataAccessInterface {
    fun getImageById(userID:Int): Flowable<ImageInfo>
    val getAllImages: Flowable<List<ImageInfo>>
    fun insertImage(vararg imageInfo: ImageInfo)
    fun insertSomeImages(imageInfo:List<ImageInfo>)
    fun updateImageInfo(vararg imageInfo: ImageInfo)
    fun deleteImage(imageInfo: ImageInfo)
    fun deleteAllImages()
}