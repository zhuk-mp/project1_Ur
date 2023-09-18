package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.project1_ur.data.OnItemClickListener
import ru.lt.project1_ur.data.ProjectAdapterCatalog
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.ProjectDataService
import ru.lt.project1_ur.data.ProjectPresenter
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class CatalogFragmentViewModel @Inject constructor(
    val data: RetainedProjectData,
    private val projectDataService: ProjectDataService
) : ViewModel(){

    val viewState: MutableLiveData<ProjectViewState.CatalogState> = MutableLiveData()
    var navi = false

    val adapter by lazy {
        ProjectAdapterCatalog(object : OnItemClickListener {
            override fun onItemClick(model: ProjectViewState.Person) {
            }

            override fun onItemClick(model: ProjectViewState.Catalog) {
                navi = true
                data.data = data.data.processCatalogChange(model)
                viewState.value = data.data.renderCatalogInput()
                data.log()
            }
        })
    }
    val presenter by lazy { ProjectPresenter(projectDataService) }

    init {
        presenter.getTypeListFromServer()
    }

    fun onNaviFalse() {
        navi = false
        viewState.value = data.data.renderCatalogInput()
    }

    private fun ProjectData.processCatalogChange(model: ProjectViewState.Catalog): ProjectData = copy(typeId = model.id, type = model.sfera)

    private fun ProjectData.renderCatalogInput(): ProjectViewState.CatalogState = ProjectViewState.CatalogState(
        itemId = typeId,
        type = type,
        navi = navi,
        auth = auth
    )
}