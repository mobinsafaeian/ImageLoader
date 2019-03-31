package com.android.mobinsafaeian.detfhappinesspeyk.model.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ImageInfo::class], version = 1)
abstract class ImageDatabase : RoomDatabase(){

    abstract fun ImageInfoDAO(): ImageInfoDAO

    companion object {
        //use a singleton object to prevent creating multiple objects
        @Volatile
        private var instance:ImageDatabase? = null

        fun getInstance(context: Context):ImageDatabase? {
            if (instance == null){
                    instance = Room.databaseBuilder(context
                        , ImageDatabase::class.java , "image-db")
                        .build()
            }
            return instance
        }

    }

    fun destroyInstance(){
        instance = null
    }

}