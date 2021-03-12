package com.friendroids.moneymana.db

import android.content.Context
import androidx.room.ColumnInfo
import com.friendroids.moneymana.db.models.*
import com.friendroids.moneymana.ui.presentation_models.BudgetParameter
import com.friendroids.moneymana.ui.presentation_models.Categorie
import com.friendroids.moneymana.ui.presentation_models.Check
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

    suspend fun loadBudgetParameters(dateBudget:Date): List<BudgetParameter> {
        var c = Calendar.getInstance();
        c.setTime(dateBudget);
        c.add(Calendar.MONTH, 1);
        return purchaseDB.budgetParametersDAO.getBudgetParametersCByDate(dateBudget,c.getTime())
            .map { toBudgetParameter(it) }
    }

    suspend fun updateBudgetParametersInDB(budgetParameter: BudgetParameter): Boolean =
            withContext(Dispatchers.IO) {
                purchaseDB.budgetParametersDAO.insert(toBudgetParameterEntity(budgetParameter))
                true
            }

    suspend fun loadChecks(dateBudget:Date,categorie: Categorie): List<Check> {
        var c = Calendar.getInstance();
        c.setTime(dateBudget);
        c.add(Calendar.MONTH, 1);
        return purchaseDB.checksDAO.getCheckCategorieByDate(dateBudget, c.getTime(), categorie.id)
            .map { toCheck(it) }
    }

    suspend fun updateCheckInDB(check: Check): Boolean =
        withContext(Dispatchers.IO) {
            purchaseDB.checksDAO.insert(toCheckEntity(check))
            true
        }

    private fun toCategorie(categorieEntity: CategorieEntity): Categorie {
        return categorieEntity?.let { categorieEntity ->
            with(categorieEntity) {
                Categorie(id = _id, title = title, imageId = imageId)
            }
        }
    }

    private fun toCheck(checkCategorie: CheckCategorie): Check {
        return checkCategorie?.let { checkCategorie ->
            with(checkCategorie) {
                Check(
                    id = _id,
                    dateCheck = dateCheck,
                    summa = summa,
                    categorie = Categorie(categorieId,categorieImageId,categorieTitle)
                )
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
    private fun toCheckEntity(check: Check) = CheckEntity(
        _id = check.id,
        categorieId = check.categorie.id,
        dateCheck = check.dateCheck,
        summa = check.summa
    )
}