package com.friendroids.moneymana.data.repository

import android.util.Log
import androidx.room.withTransaction
import com.friendroids.moneymana.db.ManaDatabase
import com.friendroids.moneymana.db.models.CategoryEntity
import com.friendroids.moneymana.db.models.CheckEntity
import com.friendroids.moneymana.domain.repository.ManaCategoriesRepository
import com.friendroids.moneymana.ui.presentation_models.Categories
import com.friendroids.moneymana.ui.presentation_models.Check
import com.friendroids.moneymana.ui.presentation_models.ManaCategory
import com.friendroids.moneymana.utils.extensions.DateTimeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ManaCategoriesRepositoryImpl(private val db: ManaDatabase) : ManaCategoriesRepository {

    override fun getCategoryBudget(idCategory: Long): Flow<List<CheckEntity>>? =
        db.checksDAO.getAllFromCategory(idCategory)

    override fun loadChecks(idCategory: Long): Flow<List<CheckEntity>>? =
        db.checksDAO.getAllFromCategory(idCategory)

    override fun getManaCategories(): Flow<List<ManaCategory>> {
        val period = DateTimeConverter().getPeriod(0)
        return db.categoriesDAO.getAllCategory(true, period.month, period.year)
            .combine(db.checksDAO.getAllCheck()) { categories: List<Categories>, checks: List<CheckEntity> ->
                convertListToMana(categories, checks)
            }
    }
/*
    //для добавления новых категорий
    override suspend fun insertManaCategory(manaCategory: ManaCategory) =
        withContext(Dispatchers.IO) {
            db.categoriesDAO.insert(manaCategory.convertMana())
        }
*/

    override fun getListCategory(): Flow<List<ManaCategory>> =
        db.categoriesDAO.getAllActualCategory(true).map { listCategory ->
            listCategory.map { category ->
                ManaCategory(
                    id = category.id!!,
                    title = category.categoryName,
                    sumRemained = 0,
                    sumMaximum = 0,
                    imageId = category.imageId
                )
            }
        }

    override suspend fun insertCheck(check: Check) = withContext(Dispatchers.IO) {
        val period = DateTimeConverter().getPeriod(check.dateCheck)
        db.withTransaction {
            with(check) {
                db.checksDAO.insert(toCheckEntity(check = this))
                db.categoriesDAO.updateCategoryAddCheck(
                    sumCheck,
                    category.id,
                    period.month,
                    period.year
                )
            }
        }
    }

/*
    override suspend fun getListCategory(): List<ManaCategory> = withContext(Dispatchers.IO) {
        db.categoriesDAO.getListAll().map {
            ManaCategory(
                    id = it._id,
                    title = it.title,
                    sumRemained = it.sumRemained,
                    sumMaximum = it.sumMaximum,
                    imageId = it.imageId
            )
        }
    }
*/

    private fun convertListToMana(
        categories: List<Categories>,
        checks: List<CheckEntity>
    ): List<ManaCategory> =
        categories.map { category ->
            val sumChecks =
                checks.filter { it.idCategory == category.id }.sumByDouble { it.sumCheck }
            val sumRemained = category.sum_maximum - sumChecks
            ManaCategory(
                id = category.id!!,
                title = category.title_category,
                sumRemained = sumRemained.toInt(),
                sumMaximum = category.sum_maximum,
                imageId = category.image_category
            )
        }

    private fun toCheckEntity(check: Check): CheckEntity {
        //check = Check(id=null, dateCheck=1617534120,
        // category=Category(id=null, imageId=2131165283, title=Прочее),
        // sumCheck=100.0, fnCheck=0, iCheck=0, fpCheck=0)
        Log.d("CameraFragment", "check = $check")
        return CheckEntity(
            id = check.id,
            idCategory = check.category.id!!,
            dateCheck = check.dateCheck,
            sumCheck = check.sumCheck,
            fnCheck = check.fnCheck,
            iCheck = check.iCheck,
            fpCheck = check.fpCheck
        )
    }

/*
    private fun ManaCategory.convertMana() =
            CategoryEntity(
                    title = title,
                    sumMaximum = sumMaximum,
                    sumRemained = sumRemained,
                    imageId = imageId
            )
*/
}
