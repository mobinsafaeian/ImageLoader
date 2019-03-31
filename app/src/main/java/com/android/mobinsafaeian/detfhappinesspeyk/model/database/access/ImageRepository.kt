package com.android.mobinsafaeian.detfhappinesspeyk.model.database.access

import com.android.mobinsafaeian.detfhappinesspeyk.model.database.ImageInfo
import io.reactivex.Flowable

class ImageRepository(private val dataAccessInterface: DataAccessInterface):DataAccessInterface {

    companion object {
        @Volatile
        private var INSTANCE:ImageRepository? = null

        fun getInstance(dataAccessInterface: DataAccessInterface):ImageRepository {
            if(INSTANCE == null) INSTANCE = ImageRepository(dataAccessInterface)
            return INSTANCE!!
        }
    }

    override fun getImageById(userID: Int): Flowable<ImageInfo> {
        return dataAccessInterface.getImageById(userID)
    }

    override val getAllImages: Flowable<List<ImageInfo>>
        get() = dataAccessInterface.getAllImages

    override fun insertImage(vararg imageInfo: ImageInfo) {
        dataAccessInterface.insertImage(*imageInfo)
    }

    override fun insertSomeImages(imageInfo: List<ImageInfo>) {
        dataAccessInterface.insertSomeImages(imageInfo)
    }

    override fun updateImageInfo(vararg imageInfo: ImageInfo) {
        dataAccessInterface.updateImageInfo(*imageInfo)
    }

    override fun deleteImage(imageInfo: ImageInfo) {
        dataAccessInterface.deleteImage(imageInfo)
    }

    override fun deleteAllImages() {
        dataAccessInterface.deleteAllImages()
    }

}