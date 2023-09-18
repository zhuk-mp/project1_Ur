package ru.lt.project1_ur.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.lt.project1_ur.state.ProjectViewState

class ProjectPresenter(
    private val projectDataService: ProjectDataService
) {
    val catalogItems = MutableLiveData<List<ProjectViewState.Catalog>>()
    val personItems = MutableLiveData<List<ProjectViewState.Person>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    fun getTypeListFromServer() {
        coroutineScope.launch {
            try {
                val request = TypeListRequest(mapOf("id" to "catalog"))
                val response = projectDataService.getTypeList(request)
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
//                        Log.d("log----------------", "TypeListRequest: ${data.items}")
                        catalogItems.postValue(updateCatalogItems(data.items))
                    }
                } else {
                    Log.d("log----------------", response.toString() ?: "Unknown error")
                }
            } catch (e: Exception) {
                Log.d("log----------------", "Exception: $e")
            }
        }
    }
    fun fetchCatalogList(typeId: Int, page: Int, onComplete: (List<ProjectViewState.Person>) -> Unit) {
//        Log.d("log----------------", "page: $page")

        coroutineScope.launch {
            try {
                val response = projectDataService.getCatalogList(CatalogListRequest(typeId, page))
                if (response?.isSuccessful == true) {
                    val data = response.body()
                    if (data != null) {
//                        Log.d("log----------------", "onComplete: ${data?.content?.items?.data}")
                        val typeList = data.content.items.data
                        onComplete(updatePersonItems(typeList))
                    }
                } else {
                    // Обработка ошибки
                    Log.d("log----------------", response?.toString() ?: "Unknown error")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("log----------------", "Exception: $e")
            }
        }

    }

    fun fetchCatalogList(itemId: Int, page: Int) {
        fetchCatalogList(itemId, page) { newItems ->
            personItems.postValue(newItems)
        }
    }

    fun updateCatalogItems(typeList: List<TypeItem>): MutableList<ProjectViewState.Catalog> {
        val mutableList = mutableListOf<ProjectViewState.Catalog>()
        for (item in typeList) {
            val model = ProjectViewState.Catalog(
                id = item.id,
                avatar = item.file.filePath,
                sfera = item.title,
            )
            mutableList.add(model)
        }
        return mutableList
    }

    fun updatePersonItems(typeList: List<CatalogItems>): MutableList<ProjectViewState.Person> {
        val mutableList = mutableListOf<ProjectViewState.Person>()
        for (item in typeList) {
            val model = ProjectViewState.Person(
                id = item.id,
                avatar = item.publicAvatar.filePath,
                name =  item.name,
                spec = item.specials.getOrNull(0)?.title,
                count =  item.views,
                phone =  "+7(912)123-45-67",
                address = item.address.data.city,
                addressFull = item.address.address,
                desc =  item.description,
                stars =  item.activeReviewsCount,
            )
            mutableList.add(model)
        }
        return mutableList
    }

}