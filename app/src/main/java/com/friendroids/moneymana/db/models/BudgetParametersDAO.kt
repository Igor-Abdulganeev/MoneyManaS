package com.friendroids.moneymana.db.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.util.*

@Dao
interface BudgetParametersDAO {
    @Query("SELECT * FROM budgetparameters ORDER BY _id ASC")
    suspend fun getAll(): List<BudgetParameterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budgetParameter: BudgetParameterEntity)

    @Query("DELETE FROM budgetparameters WHERE _id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM budgetparameters")
    suspend fun deleteAll()

    @Query("SELECT * FROM budgetparameters WHERE datebudget = :dateBudget")
    fun getBudgetParametersByDate(dateBudget: Date): List<BudgetParameterEntity>

    @Query("SELECT budgetparameters.*, categories.title AS categorietitle, categories.image AS categorieimageid " +
            "FROM budgetparameters, categories " +
            "WHERE budgetparameters.categorieid = categories._id "+
    "AND budgetparameters.datebudget = :dateBudget")
    fun getBudgetParametersCByDate(dateBudget: Date): List<BudgetParameterCategorie>
}