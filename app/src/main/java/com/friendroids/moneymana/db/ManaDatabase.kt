package com.friendroids.moneymana.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.friendroids.moneymana.R
import com.friendroids.moneymana.db.dao.BudgetDAO
import com.friendroids.moneymana.db.dao.CategoriesDAO
import com.friendroids.moneymana.db.dao.ChecksDAO
import com.friendroids.moneymana.db.models.CategoryBudgetEntity
import com.friendroids.moneymana.db.models.CategoryEntity
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.db.models.TotalBudgetEntity

@Database(
    entities = [CategoryEntity::class, CheckEntity::class, TotalBudgetEntity::class, CategoryBudgetEntity::class],
    version = 1
)
abstract class ManaDatabase : RoomDatabase() {
    abstract val categoriesDAO: CategoriesDAO
    abstract val checksDAO: ChecksDAO
    abstract val budgetDAO: BudgetDAO

    companion object {
        @Volatile
        private var INSTANCE: ManaDatabase? = null

        fun getInstance(context: Context): ManaDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: create(context).also { INSTANCE = it }
            }

        fun create(applicationContext: Context): ManaDatabase = Room.databaseBuilder(
            applicationContext,
            ManaDatabase::class.java,
            DBContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // moving to a new thread
                    ioThread {
                        getInstance(applicationContext)?.let {
                            it.categoriesDAO.insertCategoriesList(categoriesExample)
                            it.categoriesDAO.insertCategoryBudgetsList(categoriesBudgetExample)
//                            it.checksDAO.insertList(checkExample)
                        }
                    }
                }
            })
            .build()

        private val categoriesExample = listOf(
            CategoryEntity(1, R.drawable.beaker_check, "Прочее", true),
            CategoryEntity(2, R.drawable.food, "Продукты", true),
            CategoryEntity(3, R.drawable.drama_masks, "Развлечение", true),
            CategoryEntity(4, R.drawable.tshirt_crew_outline, "Одежда", true),
            CategoryEntity(5, R.drawable.gas_station, "Бензин", true),
            CategoryEntity(6, R.drawable.ambulance, "Здоровье", true),
            CategoryEntity(7, R.drawable.hiking, "Путешествия", true),
            CategoryEntity(8, R.drawable.weight_lifter, "Спорт", true)
        )
        private val categoriesBudgetExample = listOf(
            CategoryBudgetEntity(1, 1, 4, 2021, 0.00, 0.00, 0),
            CategoryBudgetEntity(2, 2, 4, 2021, 0.00, 0.00, 0),
            CategoryBudgetEntity(3, 3, 4, 2021, 0.00, 0.00, 0),
            CategoryBudgetEntity(4, 4, 4, 2021, 0.00, 0.00, 0),
            CategoryBudgetEntity(5, 5, 4, 2021, 0.00, 0.00, 0),
            CategoryBudgetEntity(6, 6, 4, 2021, 0.00, 0.00, 0),
            CategoryBudgetEntity(7, 7, 4, 2021, 0.00, 0.00, 0),
            CategoryBudgetEntity(8, 8, 4, 2021, 0.00, 0.00, 0)
        )

/*
        private val checkExample = listOf(
                CheckEntity(null, 1, setCurrentDateTime(), 1000.00, 9960440300119563, 7611, 3036044891),
                CheckEntity(null, 2, setCurrentDateTime(), 2000.00, 0, 0, 0),
                CheckEntity(null, 3, setCurrentDateTime(), 3000.00, 0, 0, 0),
                CheckEntity(null, 4, setCurrentDateTime(), 1050.00, 0, 0, 0),
                CheckEntity(null, 5, setCurrentDateTime(), 1000.00, 0, 0, 0),
                CheckEntity(null, 6, setCurrentDateTime(), 2000.00, 0, 0, 0)
        )
*/

/*
        private fun setCurrentDateTime(): Long =
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    Date().time.div(1000)
                } else {
                    LocalDateTime.now(ZoneId.systemDefault()).toEpochSecond(ZoneOffset.UTC)
                }

        private val defaultSettings = TotalBudgetEntity(sum = 0, daysTillRestartCount = 30)
*/
    }
}