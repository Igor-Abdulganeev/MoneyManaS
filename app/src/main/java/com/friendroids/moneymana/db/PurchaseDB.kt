package com.friendroids.moneymana.db

import android.content.Context
import com.friendroids.moneymana.db.models.BudgetParameterCategory
import com.friendroids.moneymana.db.models.BudgetParameterEntity
import com.friendroids.moneymana.db.models.CategoryEntity
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.ui.presentation_models.BudgetParameter
import com.friendroids.moneymana.ui.presentation_models.Category
import com.friendroids.moneymana.ui.presentation_models.Check
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

/*
    fun loadBudgetParameters(dateBudget: Date): Flow<List<BudgetParameter>> = flow {
        val c = Calendar.getInstance()
        c.time = dateBudget
        c.add(Calendar.MONTH, 1)
        purchaseDB.budgetParametersDAO.getBudgetParametersCByDate(dateBudget, c.time)
                .collect { lBPC ->
                    emit(lBPC.map { toBudgetParameter(it) })
                }
    }
*/

    suspend fun updateBudgetParametersInDB(budgetParameter: BudgetParameter): Boolean =
            withContext(Dispatchers.IO) {
                purchaseDB.budgetParametersDAO.insert(toBudgetParameterEntity(budgetParameter))
                true
            }

/*
    fun loadChecks(dateBudget: Date, categorie: Categorie): Flow<List<Check>> = flow {
        val c = Calendar.getInstance()
        c.time = dateBudget
        c.add(Calendar.MONTH, 1)
        purchaseDB.checksDAO.getCheckCategorieByDate(dateBudget, c.time, categorie.id)
                .collect { lCC ->
                    emit(lCC.map { toCheck(it) })
                }
    }
*/

    suspend fun insertCheckInDb(check: Check) = withContext(Dispatchers.IO) {
        purchaseDB.checksDAO.insert(toCheckEntity(check = check))
    }
/*
    suspend fun updateCheckInDB(check: Check, dateBudget: Date): Boolean =
            withContext(Dispatchers.IO) {
                val c = Calendar.getInstance()
                c.time = dateBudget
                c.add(Calendar.MONTH, 1)
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
                purchaseDB.checksDAO.insert(toCheckEntity(check))
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
                true
            }
*/

//    private fun toCategorie(categorieEntity: CategorieEntity): Categorie {
//        return categorieEntity?.let { categorieEntity ->
//            with(categorieEntity) {
//                Categorie(id = _id, title = title, imageId = imageId)
//            }
//        }
//    }

/*
    private fun toCheck(checkCategory: CheckCategorie): Check {
        return checkCategory.let { checkCategory ->
            with(checkCategory) {
                Check(
                        id = _id.toLong(),
                        dateCheck = dateCheck,
                        sumCheck = summa,
                        categorie = Categorie(categorieId, categorieImageId, categorieTitle)
                )
            }
        }
    }
*/

    private fun toBudgetParameter(budgetParameterCategory: BudgetParameterCategory) =
            BudgetParameter(
                    id = budgetParameterCategory._id,
                    dateBudget = budgetParameterCategory.dateBudget,
                    category = Category(
                            id = budgetParameterCategory.idCategory,
                            imageId = budgetParameterCategory.imageIdCategory,
                            title = budgetParameterCategory.titleCategory
                    ),
                    sumBudget = budgetParameterCategory.sumBudget,
                    sumFact = budgetParameterCategory.sumFact
            )

    private fun toBudgetParameterEntity(budgetParameter: BudgetParameter) = BudgetParameterEntity(
            _id = budgetParameter.id,
            idCategory = budgetParameter.category.id,
            dateBudget = budgetParameter.dateBudget,
            sumBudget = budgetParameter.sumBudget,
            sumFact = budgetParameter.sumFact
    )

    private fun toCategorieEntity(categorie: ManaCategory) = categorie.id?.let {
        CategoryEntity(
                _id = it,
                title = categorie.title,
                imageId = categorie.imageId,
                sumMaximum = categorie.sumMaximum,
                sumRemained = categorie.sumRemained
        )
    }

    private fun toCheckEntity(check: Check) = CheckEntity(
            id = check.id,
            idCategory = check.category.id,
            dateCheck = check.dateCheck,
            sumCheck = check.sumCheck,
            fnCheck = check.fnCheck,
            iCheck = check.iCheck,
            fpCheck = check.fpCheck
    )
}