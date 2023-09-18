package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.project1_ur.data.OnItemClickListener
import ru.lt.project1_ur.data.ProjectAdapterPerson
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.ProjectDataService
import ru.lt.project1_ur.data.ProjectPresenter
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject
@HiltViewModel
class PersonFragmentViewModel @Inject constructor(
    val data: RetainedProjectData,
    private val projectDataService: ProjectDataService
) : ViewModel(){

    val viewState: MutableLiveData<ProjectViewState.PersonState> = MutableLiveData()
    var navi = false

    val adapter by lazy {
        ProjectAdapterPerson(object : OnItemClickListener {
            override fun onItemClick(model: ProjectViewState.Person) {
                navi = true
                data.data = data.data.processPersonChange(model)
                viewState.value = data.data.renderPersonInput()
            }

            override fun onItemClick(model: ProjectViewState.Catalog) {
            }
        })
    }
    val presenter by lazy { ProjectPresenter(projectDataService) }

    init {
        val itemId = data.data.typeId
        if (itemId != null) {
            presenter.fetchCatalogList(itemId, 1)
        }
        viewState.value = data.data.renderPersonInput()
    }

    fun onNaviFalse() {
        navi = false
        viewState.value = data.data.renderPersonInput()
    }
    fun beckAuth() {
        data.data = data.data.beckAuth()
    }

    private fun ProjectData.beckAuth(): ProjectData = copy(beckAuth = true)
    private fun ProjectData.processPersonChange(model: ProjectViewState.Person): ProjectData =
        copy(
            itemId = model.id,
            itemName = model.name,
            itemAvatar = model.avatar,
            itemSpec = model.spec,
            itemDesc = model.desc,
            itemPhone = model.phone,
            itemAddress = model.address,
            itemAddressFull = model.addressFull,
            itemView = model.count,
            itemRew = model.stars,
        )

    private fun ProjectData.renderPersonInput(): ProjectViewState.PersonState = ProjectViewState.PersonState(
        itemId = itemId,
        itemName = itemName,
        navi = navi,
        type = type,
        auth = auth
    )
}