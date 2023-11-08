package com.example.teststorymaker

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.CoroutineScope

@Database(entities = [MyStoryData::class], version = 1)
abstract class MyRoomDB : RoomDatabase() {

    abstract fun MyStoryDAO(): MyStoryDAO

    // 데이터 베이스 객체를 싱글톤으로 인스턴스.
    companion object {
        private var instance: MyRoomDB? = null

        @Synchronized
        fun getInstance(context: Context): MyRoomDB? {
            if (instance == null)
                synchronized(MyRoomDB::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyRoomDB::class.java,
                        "room.db"
                    )
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }

}