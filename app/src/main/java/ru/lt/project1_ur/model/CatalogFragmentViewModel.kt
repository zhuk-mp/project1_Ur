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
import ru.lt.project1_ur.state.ProjectCatalogIntent
import ru.lt.project1_ur.state.ProjectCatalogIntent.LoadInitialData
import ru.lt.project1_ur.state.ProjectCatalogIntent.ModelEntered
import ru.lt.project1_ur.state.ProjectCatalogIntent.NavigateTo
import ru.lt.project1_ur.state.ProjectCatalogIntent.AddRecyclerView
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class CatalogFragmentViewModel @Inject constructor(
    val data: RetainedProjectData,
    private val projectDataService: ProjectDataService
) : ViewModel(){

    val viewState: MutableLiveData<ProjectViewState.CatalogState> = MutableLiveData()

    private val _navigateChannel = Channel<NavigatorIntent>()
    val navigateFlow: Flow<NavigatorIntent> = _navigateChannel.receiveAsFlow()
    val presenter by lazy { ProjectPresenter(projectDataService) }

    init {
        processIntents(LoadInitialData)
    }
    fun processIntents(intents: ProjectCatalogIntent, update: Boolean = false) {
        data.data = data.data.processCatalogChange(intents)
        if (update)
            viewState.value = data.data.renderCatalogInput()
    }
    private fun ProjectData.processCatalogChange(event: ProjectCatalogIntent): ProjectData = when (event) {
        LoadInitialData -> loadInitialData()
        NavigateTo -> navigateTo()
        is ModelEntered -> copy(typeId = event.model.id, type = event.model.sfera)
        is AddRecyclerView -> copy(catalogList = event.catalogList)
    }

    private fun ProjectData.renderCatalogInput(): ProjectViewState.CatalogState = ProjectViewState.CatalogState(
        itemId = typeId,
        type = type,
        auth = auth,
        catalogList = catalogList
    )

    private fun navigateTo(): ProjectData{
            _navigateChannel.trySend(NavigatorIntent.ToPerson)
        return data.data.copy()
    }

    private fun loadInitialData(): ProjectData{
        presenter.getTypeListFromServer()
        return data.data.copy()
    }

    override fun onCleared() {
        super.onCleared()
        _navigateChannel.close()
    }
}