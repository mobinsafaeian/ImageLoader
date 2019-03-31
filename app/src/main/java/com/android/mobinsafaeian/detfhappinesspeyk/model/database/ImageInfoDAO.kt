package com.android.mobinsafaeian.detfhappinesspeyk.model.database

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface ImageInfoDAO {
    @Query("SELECT * FROM image_table WHERE id=:userID")
    fun getImageById(userID:Int): Flowable<ImageInfo>

    @get:Query("SELECT * FROM image_table")
    val getAllImages:Flowable<List<ImageInfo>>

    @Insert
    fun insertImage(vararg imageInfo: ImageInfo)

    @Insert
    fun insertSomeImages(imageInfo:List<ImageInfo>)

    @Update
    fun updateImageInfo(vararg imageInfo: ImageInfo)

    @Delete
    fun deleteImage(imageInfo: ImageInfo)

    @Query("DELETE FROM image_table")
    fun deleteAllImages()

}