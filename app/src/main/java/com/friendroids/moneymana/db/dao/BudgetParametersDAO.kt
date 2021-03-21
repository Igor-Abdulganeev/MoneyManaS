package com.friendroids.moneymana.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.friendroids.moneymana.db.models.BudgetParameterCategorie
import com.friendroids.moneymana.db.models.BudgetParameterEntity
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface BudgetParametersDAO {
    @Query("SELECT * FROM budgetparameters ORDER BY _id ASC")
    suspend fun getAll(): List<BudgetParameterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budgetParameter: BudgetParameterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(budgetParameterList: List<BudgetParameterEntity>)

    @Query("DELETE FROM budgetparameters WHERE _id == :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM budgetparameters")
    suspend fun deleteAll()

    @Query("SELECT * FROM budgetparameters WHERE (datebudget BETWEEN :dateStart AND :dateEnd) AND (categorieid = :categorie)")
    fun getBudgetParameterByDateCategorie(dateStart: Date, dateEnd: Date, categorie: Int): BudgetParameterEntity

    @Query("SELECT * FROM budgetparameters WHERE datebudget = :dateBudget")
    fun getBudgetParametersByDate(dateBudget: Date): List<BudgetParameterEntity>

    @Query(
        "SELECT budgetparameters.*, categories.title AS categorietitle, categories.image AS categorieimageid " +
                "FROM budgetparameters, categories " +
                "WHERE budgetparameters.categorieid = categories._id " +
                "AND (budgetparameters.datebudget between :dateStart and :dateEnd)"
    )
    fun getBudgetParametersCByDate(dateStart: Date, dateEnd: Date): Flow<List<BudgetParameterCategorie>>

    @Query("SELECT * FROM total_budget WHERE id == :id")
    fun getTotalBudget(id: String): Flow<TotalBudgetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateTotalBudget(totalBudgetEntity: TotalBudgetEntity)
}