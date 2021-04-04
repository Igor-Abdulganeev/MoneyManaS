package com.friendroids.moneymana.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.friendroids.moneymana.db.models.CategoryBudgetEntity
import com.friendroids.moneymana.db.models.CategoryEntity
import com.friendroids.moneymana.ui.presentation_models.Categories
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDAO {
    //    @Query("SELECT * FROM categories WHERE active = :bool ORDER BY id ASC")
    @Query("SELECT A.id, A.image_category, A.title_category, B.month_budget, B.year_budget, B.sum_spent, B.sum_remained, B.sum_maximum FROM categories AS A INNER JOIN category_budget as B ON B.id=A.id AND B.month_budget=:month AND B.year_budget=:year WHERE active = :bool ORDER BY A.id ASC")
//fun getAllCategory(bool: Boolean): Flow<List<CategoryEntity>>
    fun getAllCategory(bool: Boolean, month: Int, year: Int): Flow<List<Categories>>
    //ManaRepositoryImpl

    @Query("SELECT * FROM categories WHERE active = :bool ORDER BY id ASC")
    fun getAllActualCategory(bool: Boolean): Flow<List<CategoryEntity>>

/*
    @Query("SELECT * FROM categories ORDER BY id ASC")
    suspend fun getListAll(): List<CategoryEntity>
    //ManaRepositoryImpl
*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categoryEntity: CategoryEntity)
    //PurchaseDB unused
    //ManaRepositoryImpl

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoriesList(categoryEntityList: List<CategoryEntity>)
    //ManaDatabase

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategoryBudgetsList(categoryBudgetEntityList: List<CategoryBudgetEntity>)
    //ManaDatabase

/*
    @Query("DELETE FROM categories WHERE id == :id")
    suspend fun deleteById(id: Long)
*/

/*
    @Query("DELETE FROM categories")
    suspend fun deleteAll()
*/

/*
    @Query("SELECT * FROM categories WHERE id == :id")
    fun getCategoriesById(id: Int): CategoryEntity
*/
}