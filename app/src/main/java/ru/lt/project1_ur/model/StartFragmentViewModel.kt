package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.lt.project1_ur.R
import ru.lt.project1_ur.data.DataStoreHelper
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.NavigatorIntent
import ru.lt.project1_ur.state.ProjectStartIntent
import ru.lt.project1_ur.state.ProjectStartIntent.LoadInitialData
import ru.lt.project1_ur.state.ProjectStartIntent.NavigateTo
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class StartFragmentViewModel @Inject constructor(
    val data: RetainedProjectData,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel(){
    val viewState: MutableLiveData<ProjectViewState.Start> = MutableLiveData()

    private val _navigateChannel = Channel<NavigatorIntent>()
    val navigateFlow: Flow<NavigatorIntent> = _navigateChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            dataStoreHelper.getData(R.string.start_name).collect { value ->
                    processIntents(LoadInitialData(value, (value != null && value != "")), true)
            }
        }
    }
    fun processIntents(intents: ProjectStartIntent, update: Boolean = false) {
        data.data = data.data.processStartChange(intents)
        if (update)
            viewState.value = data.data.renderStartInput()
    }

    private fun ProjectData.processStartChange(event: ProjectStartIntent): ProjectData = when (event) {
        is LoadInitialData -> copy(name = event.name, auth = event.auth)
        NavigateTo -> navigateTo(auth)
    }

    private fun ProjectData.renderStartInput(): ProjectViewState.Start = ProjectViewState.Start(
        name = if (auth) "Здравствуйте, $name" else "",
        auth = auth
    )

    private fun navigateTo(auth: Boolean): ProjectData{
        if (auth) {
            _navigateChannel.trySend(NavigatorIntent.ToCatalog)
        } else {
            _navigateChannel.trySend(NavigatorIntent.ToLogin)
        }
        return data.data.copy()
    }

    override fun onCleared() {
        super.onCleared()
        _navigateChannel.close()
    }

}