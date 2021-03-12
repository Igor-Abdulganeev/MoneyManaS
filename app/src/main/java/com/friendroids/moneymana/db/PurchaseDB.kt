package com.friendroids.moneymana.db

import android.content.Context
import androidx.room.ColumnInfo
import com.friendroids.moneymana.db.models.BudgetParameterCategorie
import com.friendroids.moneymana.db.models.BudgetParameterEntity
import com.friendroids.moneymana.db.models.CategorieEntity
import com.friendroids.moneymana.ui.presentation_models.BudgetParameter
import com.friendroids.moneymana.ui.presentation_models.Categorie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*

class PurchaseDB(applicationContext: Context) {
    private val purchaseDB = DataBase.create(applicationContext)

    suspend fun loadCategories(): List<Categorie> =
            purchaseDB.categoriesDAO.getAll().map { toCategorie(it) }

    suspend fun updateCategorieInDB(categorie: Categorie): Boolean =
            withContext(Dispatchers.IO) {
                purchaseDB.categoriesDAO.insert(toCategorieEntity(categorie))
                true
                }

    suspend fun loadBudgetParameters(dateBudget:Date): List<BudgetParameter> =
            purchaseDB.budgetParametersDAO.getBudgetParametersCByDate(dateBudget).map { toBudgetParameter(it) }

    suspend fun updateBudgetParametersInDB(budgetParameter: BudgetParameter): Boolean =
            withContext(Dispatchers.IO) {
                purchaseDB.budgetParametersDAO.insert(toBudgetParameterEntity(budgetParameter))
                true
            }

    private fun toCategorie(categorieEntity: CategorieEntity): Categorie {
        return categorieEntity?.let { categorieEntity ->
            with(categorieEntity) {
                Categorie(id = _id, title = title, imageId = imageId)
            }
        }
    }

    private fun toBudgetParameter(budgetParameterCategorie: BudgetParameterCategorie) = BudgetParameter(
            id = budgetParameterCategorie._id,
            dateBudget = budgetParameterCategorie.dateBudget,
            categorie = Categorie(budgetParameterCategorie.categorieId,budgetParameterCategorie.categorieImageId,budgetParameterCategorie.categorieTitle),
            summaPlan = budgetParameterCategorie.summaPlan,
            summaFact = budgetParameterCategorie.summaFact
    )

    private fun toBudgetParameterEntity(budgetParameter: BudgetParameter) = BudgetParameterEntity(
            _id = budgetParameter.id,
            categorieId = budgetParameter.categorie.id,
            dateBudget = budgetParameter.dateBudget,
            summaPlan = budgetParameter.summaPlan,
            summaFact = budgetParameter.summaFact
    )

    private fun toCategorieEntity(categorie: Categorie) = CategorieEntity(
            _id = categorie.id,
            title = categorie.title,
            imageId = categorie.imageId
    )

 }