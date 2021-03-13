package com.friendroids.moneymana.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.friendroids.moneymana.db.models.*

@Database(
    entities = [CategorieEntity::class, CheckEntity::class, BudgetParameterEntity::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract val categoriesDAO: CategoriesDAO
    abstract val checksDAO: ChecksDAO
    abstract val budgetParametersDAO: BudgetParametersDAO

    companion object {
        private var instance: DataBase? = null

        fun newInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: create(context).also { instance = it }
        }

        fun create(applicationContext: Context): DataBase = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java,
            DBContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}