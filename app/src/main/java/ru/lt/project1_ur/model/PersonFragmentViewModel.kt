package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.ProjectDataService
import ru.lt.project1_ur.data.ProjectPresenter
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.NavigatorIntent
import ru.lt.project1_ur.state.ProjectPersonIntent
import ru.lt.project1_ur.state.ProjectPersonIntent.LoadInitialData
import ru.lt.project1_ur.state.ProjectPersonIntent.AddRecyclerView
import ru.lt.project1_ur.state.ProjectPersonIntent.ModelEntered
import ru.lt.project1_ur.state.ProjectPersonIntent.NavigateTo
import ru.lt.project1_ur.state.ProjectPersonIntent.ScrollUp
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class PersonFragmentViewModel @Inject constructor(
    val data: RetainedProjectData,
    private val projectDataService: ProjectDataService
) : ViewModel(){

    val viewState: MutableLiveData<ProjectViewState.PersonState> = MutableLiveData()
    private var countPage = 1

    private val _navigateChannel = Channel<NavigatorIntent>()
    val navigateFlow: Flow<NavigatorIntent> = _navigateChannel.receiveAsFlow()
    val presenter by lazy { ProjectPresenter(projectDataService) }

    init {
        processIntents(LoadInitialData)
    }
    fun processIntents(intents: ProjectPersonIntent, update: Boolean = false) {
        data.data = data.data.processPersonChange(intents)
        if (update)
            viewState.value = data.data.renderPersonInput()
    }
    private fun ProjectData.processPersonChange(event: ProjectPersonIntent): ProjectData = when (event) {
        LoadInitialData -> loadInitialData(typeId)
        NavigateTo -> navigateTo()
        is ScrollUp -> {
            event.recyclerView.smoothScrollToPosition(0)
            data.data.copy()
        }
        is ModelEntered -> copy(
            itemId = event.model.id,
            itemName = event.model.name,
            itemAvatar = event.model.avatar,
            itemSpec = event.model.spec,
            itemDesc = event.model.desc,
            itemPhone = event.model.phone,
            itemAddress = event.model.address,
            itemAddressFull = event.model.addressFull,
            itemView = event.model.count,
            itemRew = event.model.stars,
        )
        is AddRecyclerView -> copy(personList = event.personList)
    }

    private fun ProjectData.renderPersonInput(): ProjectViewState.PersonState = ProjectViewState.PersonState(
        itemId = itemId,
        itemName = itemName,
        type = type,
        auth = auth,
        personList = personList,
        typeId = typeId ?: 0,
        countPage = ++countPage
    )

    private fun navigateTo(): ProjectData{
        _navigateChannel.trySend(NavigatorIntent.ToCart)
        return data.data.copy()
    }

    private fun loadInitialData(itemId: Int?): ProjectData{
        if (itemId != null) {
            presenter.fetchCatalogList(itemId, 1)
        }
        return data.data.copy()
    }

    override fun onCleared() {
        super.onCleared()
        _navigateChannel.close()
    }
}