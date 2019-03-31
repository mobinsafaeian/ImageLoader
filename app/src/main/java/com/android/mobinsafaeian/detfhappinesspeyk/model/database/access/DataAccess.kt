package com.android.mobinsafaeian.detfhappinesspeyk.model.database.access

import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageInfo
import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageInfoDAO
import io.reactivex.Flowable

class DataAccess(private val imageInfoDAO: ImageInfoDAO):DataAccessInterface {


    companion object {
        @Volatile
        private var INSTANCE:DataAccess? = null

        fun getInstance(imageInfoDAO: ImageInfoDAO):DataAccess {
            if(INSTANCE == null) INSTANCE = DataAccess(imageInfoDAO)
            return INSTANCE!!
        }
    }


    override fun getImageById(userID: Int): Flowable<ImageInfo> {
        return imageInfoDAO.getImageById(userID)
    }

    override val getAllImages: Flowable<List<ImageInfo>>
        get() = imageInfoDAO.getAllImages

    override fun insertImage(vararg imageInfo: ImageInfo) {
        imageInfoDAO.insertImage(*imageInfo)
    }

    override fun insertSomeImages(imageInfo: List<ImageInfo>) {
        imageInfoDAO.insertSomeImages(imageInfo)
    }

    override fun updateImageInfo(vararg imageInfo: ImageInfo) {
        imageInfoDAO.updateImageInfo(*imageInfo)
    }

    override fun deleteImage(imageInfo: ImageInfo) {
        imageInfoDAO.deleteImage(imageInfo)
    }

    override fun deleteAllImages() {
        imageInfoDAO.deleteAllImages()
    }
}