package com.android.mobinsafaeian.detfhappinesspeyk.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ImageInfo::class], version = 1)
abstract class ImageDatabase : RoomDatabase(){

    abstract fun ImageInfoDAO(): ImageInfoDAO

    companion object {

        /*@Volatile*/
        object InstanceObj {
            var instance:ImageDatabase? = null
        }


        fun getInstance(context: Context):ImageDatabase? {
                    InstanceObj.instance = Room.databaseBuilder(context
                        , ImageDatabase::class.java , "image-db")
                        .build()
            return InstanceObj.instance
        }

    }

    fun destroyInstance(){
        InstanceObj.instance = null
    }

}