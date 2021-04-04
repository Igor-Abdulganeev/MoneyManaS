package com.friendroids.moneymana.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.friendroids.moneymana.db.models.TotalBudgetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDAO {
/*
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
*/

/*
    @Query("SELECT * FROM budgetparameters WHERE (datebudget BETWEEN :dateStart AND :dateEnd) AND (categorieid = :categorie)")
    fun getBudgetParameterByDateCategorie(dateStart: Date, dateEnd: Date, categorie: Int): BudgetParameterEntity

    @Query("SELECT * FROM budgetparameters WHERE datebudget = :dateBudget")
    fun getBudgetParametersByDate(dateBudget: Date): List<BudgetParameterEntity>
*/

/*
    @Query(
        "SELECT budgetparameters.*, categories.title AS categorietitle, categories.image AS categorieimageid " +
                "FROM budgetparameters, categories " +
                "WHERE budgetparameters.categorieid = categories._id " +
                "AND (budgetparameters.datebudget between :dateStart and :dateEnd)"
    )
    fun getBudgetParametersCByDate(dateStart: Date, dateEnd: Date): Flow<List<BudgetParameterCategorie>>
*/

    @Query("SELECT * FROM total_budget WHERE month_budget = :month AND year_budget = :year")
    fun getTotalBudget(month: Int, year: Int): Flow<TotalBudgetEntity>

/*
    @Query("SELECT * FROM total_budget WHERE MAX(id) LIMIT 1")
    fun getLastTotalBudget(): Flow<TotalBudgetEntity>
*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateTotalBudget(totalBudgetEntity: TotalBudgetEntity)
}