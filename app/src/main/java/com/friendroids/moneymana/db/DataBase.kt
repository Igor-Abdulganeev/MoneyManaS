package com.friendroids.moneymana.db

import android.content.Context
import android.os.Build
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.friendroids.moneymana.R
import com.friendroids.moneymana.db.dao.BudgetParametersDAO
import com.friendroids.moneymana.db.dao.CategoriesDAO
import com.friendroids.moneymana.db.dao.ChecksDAO
import com.friendroids.moneymana.db.models.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

@Database(
        entities = [CategoryEntity::class, CheckEntity::class, BudgetParameterEntity::class, TotalBudgetEntity::class],
        version = 1
)
@TypeConverters(DateConverter::class)
abstract class DataBase : RoomDatabase() {
    abstract val categoriesDAO: CategoriesDAO
    abstract val checksDAO: ChecksDAO
    abstract val budgetParametersDAO: BudgetParametersDAO

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: create(context).also { INSTANCE = it }
            }

        fun create(applicationContext: Context): DataBase = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java,
            DBContract.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // moving to a new thread
                    ioThread {
                        getInstance(applicationContext)?.let {
                            it.categoriesDAO.insertList(categorieExample)
                            it.checksDAO.insertList(checkExample)
                            it.budgetParametersDAO.insertList(budgetParameterExample)
                            it.budgetParametersDAO.updateTotalBudget(defaultSettings)
                        }
                    }
                }
            })
            .build()

//        private val categorieExample = listOf(
//            CategorieEntity(0, 1, "Total budget"),
//            CategorieEntity(0, 1, "Foods"),
//            CategorieEntity(0, 1, "Fuel"),
//            CategorieEntity(0, 1, "Clothes"),
//            CategorieEntity(0, 1, "Other")
//        )

        private val categorieExample = listOf(
                //CategorieEntity(null, R.drawable.bag_suitcase, "Total budget", 87900, 100000),
                CategoryEntity(null, R.drawable.drama_masks, "Прочее", 30000, 40000),
                CategoryEntity(null, R.drawable.hiking, "Путешествия", 10000, 60000),
                CategoryEntity(null, R.drawable.food, "Продукты", 10000, 10000),
                CategoryEntity(null, R.drawable.fuel, "Бензин", 2500, 5000),
                CategoryEntity(null, R.drawable.face_man_profile, "Одежда", 800, 8000),
                CategoryEntity(null, R.drawable.adjust, "Курсы", 8000, 10000),
                CategoryEntity(null, R.drawable.adjust, "Другое", -200, 10000)
        )

        private val checkExample = listOf(
                CheckEntity(null, 1, setCurrentDateTime(), 1000.00, 9960440300119563, 7611, 3036044891),
                CheckEntity(null, 2, setCurrentDateTime(), 2000.00, 0, 0, 0),
                CheckEntity(null, 3, setCurrentDateTime(), 3000.00, 0, 0, 0),
                CheckEntity(null, 4, setCurrentDateTime(), 1050.00, 0, 0, 0),
                CheckEntity(null, 5, setCurrentDateTime(), 1000.00, 0, 0, 0),
                CheckEntity(null, 6, setCurrentDateTime(), 2000.00, 0, 0, 0)
/*            CheckEntity(null, 1, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 1, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 2, setCurrentDateTime(), 1000.00),
            CheckEntity(null, 2, setCurrentDateTime(), 2000.00),
            CheckEntity(null, 2, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 2, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 2, setCurrentDateTime(), 1000.00),
            CheckEntity(null, 3, setCurrentDateTime(), 2000.00),
            CheckEntity(null, 3, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 3, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 3, setCurrentDateTime(), 1000.00),
            CheckEntity(null, 4, setCurrentDateTime(), 2000.00),
            CheckEntity(null, 4, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 4, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 4, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 4, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 4, setCurrentDateTime(), 1000.00),
            CheckEntity(null, 2, setCurrentDateTime(), 2000.00),
            CheckEntity(null, 2, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 2, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 2, setCurrentDateTime(), 1000.00),
            CheckEntity(null, 3, setCurrentDateTime(), 2000.00),
            CheckEntity(null, 3, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 3, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 3, setCurrentDateTime(), 1000.00),
            CheckEntity(null, 3, setCurrentDateTime(), 2000.00),
            CheckEntity(null, 1, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 1, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 1, setCurrentDateTime(), 1000.00),
            CheckEntity(null, 1, setCurrentDateTime(), 2000.00),
            CheckEntity(null, 1, setCurrentDateTime(), 3000.00),
            CheckEntity(null, 4, setCurrentDateTime(), 1050.00),
            CheckEntity(null, 4, setCurrentDateTime(), 1140.00)*/
        )

        private fun getDateBudget(): Date {
            var c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -10);
            return c.getTime()
        }

        private fun setCurrentDateTime(): Long =
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    Date().time.div(1000)
                } else {
                    LocalDateTime.now(ZoneId.systemDefault()).toEpochSecond(ZoneOffset.UTC)
                }

        private val budgetParameterExample = listOf(
                BudgetParameterEntity(0, 0, setCurrentDateTime(), 100000.00, 87900.00),
                BudgetParameterEntity(0, 1, setCurrentDateTime(), 10000.00, 10000.00),
                BudgetParameterEntity(0, 2, setCurrentDateTime(), 5000.00, 2500.00),
                BudgetParameterEntity(0, 3, setCurrentDateTime(), 8000.00, 800.00),
                BudgetParameterEntity(0, 4, setCurrentDateTime(), 10000.00, 8900.00)
        )

        private val defaultSettings = TotalBudgetEntity(sum = 0, daysTillRestartCount = 30)
    }
}