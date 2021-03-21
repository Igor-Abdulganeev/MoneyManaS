package com.friendroids.moneymana.db

import android.content.Context
import com.friendroids.moneymana.db.models.BudgetParameterCategorie
import com.friendroids.moneymana.db.models.BudgetParameterEntity
import com.friendroids.moneymana.db.models.CategorieEntity
import com.friendroids.moneymana.db.models.CheckCategorie
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.ui.presentation_models.BudgetParameter
import com.friendroids.moneymana.ui.presentation_models.Categorie
import com.friendroids.moneymana.ui.presentation_models.Check
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date

class PurchaseDB(applicationContext: Context) {
    private val purchaseDB = DataBase.create(applicationContext)

    /*suspend fun loadCategories(): Flow<List<Categorie>> = flow {
        purchaseDB.categoriesDAO.getAll().collect { categorieEntity ->
            emit(categorieEntity.map { toCategorie(it) })
        }
    }*/

    suspend fun updateCategorieInDB(categorie: ManaCategory): Boolean =
        withContext(Dispatchers.IO) {
            toCategorieEntity(categorie)?.let { purchaseDB.categoriesDAO.insert(it) }
            true
        }

    fun loadBudgetParameters(dateBudget: Date): Flow<List<BudgetParameter>> = flow {
        val c = Calendar.getInstance()
        c.time = dateBudget
        c.add(Calendar.MONTH, 1)
        purchaseDB.budgetParametersDAO.getBudgetParametersCByDate(dateBudget, c.time)
                .collect { lBPC ->
                    emit(lBPC.map { toBudgetParameter(it) })
                }
    }

    suspend fun updateBudgetParametersInDB(budgetParameter: BudgetParameter): Boolean =
        withContext(Dispatchers.IO) {
            purchaseDB.budgetParametersDAO.insert(toBudgetParameterEntity(budgetParameter))
            true
        }

    fun loadChecks(dateBudget: Date, categorie: Categorie): Flow<List<Check>> = flow {
        val c = Calendar.getInstance()
        c.time = dateBudget
        c.add(Calendar.MONTH, 1)
        purchaseDB.checksDAO.getCheckCategorieByDate(dateBudget, c.time, categorie.id)
                .collect { lCC ->
                    emit(lCC.map { toCheck(it) })
                }
    }

    suspend fun updateCheckInDB(check: Check, dateBudget: Date): Boolean =
        withContext(Dispatchers.IO) {
            val c = Calendar.getInstance()
            c.time = dateBudget
            c.add(Calendar.MONTH, 1)
/*
            purchaseDB.checksDAO.getCheckById(check.id)?.let {
                purchaseDB.budgetParametersDAO.getBudgetParameterByDateCategorie(
                    dateBudget,
                    c.time,
                    check.categorie.id
                )?.let {
                    purchaseDB.budgetParametersDAO.insert(
                        BudgetParameterEntity(
                            it._id,
                            it.categorieId,
                            it.dateBudget,
                            it.summaPlan,
                            it.summaFact - check.summa
                        )
                    )
                }
            }
*/
            purchaseDB.checksDAO.insert(toCheckEntity(check))
/*
            purchaseDB.budgetParametersDAO.getBudgetParameterByDateCategorie(
                dateBudget,
                c.getTime(),
                check.categorie.id
            )?.let {
                purchaseDB.budgetParametersDAO.insert(
                    BudgetParameterEntity(
                        it._id,
                        it.categorieId,
                        it.dateBudget,
                        it.summaPlan,
                        it.summaFact + check.summa
                    )
                )
            }
*/
            true
        }

//    private fun toCategorie(categorieEntity: CategorieEntity): Categorie {
//        return categorieEntity?.let { categorieEntity ->
//            with(categorieEntity) {
//                Categorie(id = _id, title = title, imageId = imageId)
//            }
//        }
//    }

    private fun toCheck(checkCategory: CheckCategorie): Check {
        return checkCategory.let { checkCategory ->
            with(checkCategory) {
                Check(
                        id = _id,
                        dateCheck = dateCheck,
                        summa = summa,
                        categorie = Categorie(categorieId, categorieImageId, categorieTitle)
                )
            }
        }
    }

    private fun toBudgetParameter(budgetParameterCategorie: BudgetParameterCategorie) =
        BudgetParameter(
            id = budgetParameterCategorie._id,
            dateBudget = budgetParameterCategorie.dateBudget,
            categorie = Categorie(
                budgetParameterCategorie.categorieId,
                budgetParameterCategorie.categorieImageId,
                budgetParameterCategorie.categorieTitle
            ),
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

    private fun toCategorieEntity(categorie: ManaCategory) = categorie.id?.let {
        CategorieEntity(
            _id = it,
            title = categorie.title,
            imageId = categorie.imageId,
            maxSum = categorie.maxSum,
            sumRemained = categorie.sumRemained
        )
    }

    private fun toCheckEntity(check: Check) = CheckEntity(
        _id = check.id,
        categorieId = check.categorie.id,
        dateCheck = check.dateCheck,
        summa = check.summa
    )
}