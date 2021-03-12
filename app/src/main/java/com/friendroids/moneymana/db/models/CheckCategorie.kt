package com.friendroids.moneymana.db.models

import androidx.room.ColumnInfo
import com.friendroids.moneymana.db.DBContract
import java.util.*

class CheckCategorie (
    @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_ID)
    val _id: Int,

    @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_CATEGORIE_ID)
    val categorieId: Int,

    @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_DATE_CHECK)
    val dateCheck: Date,

    @ColumnInfo(name = DBContract.Checks.COLUMN_NAME_SUMMA)
    val summa: Double,

    @ColumnInfo(name = "categorietitle")
    val categorieTitle:String,

    @ColumnInfo(name = "categorieimageid")
    val categorieImageId:Int
)