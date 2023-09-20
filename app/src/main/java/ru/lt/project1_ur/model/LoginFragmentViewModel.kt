package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.lt.project1_ur.data.DataStoreHelper
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.NavigatorIntent
import ru.lt.project1_ur.state.ProjectLoginIntent
import ru.lt.project1_ur.state.ProjectLoginIntent.LoginEntered
import ru.lt.project1_ur.state.ProjectLoginIntent.NavigateTo
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    val data: RetainedProjectData,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel(){

    val viewState: MutableLiveData<ProjectViewState.LoginState> = MutableLiveData()
    private val _navigateChannel = Channel<NavigatorIntent>()
    val navigateFlow: Flow<NavigatorIntent> = _navigateChannel.receiveAsFlow()

    init {
        viewState.value = data.data.renderLoginInput()
    }fun processIntents(intents: ProjectLoginIntent, update: Boolean = false) {
        data.data = data.data.processLoginChange(intents)
        if (update)
            viewState.value = data.data.renderLoginInput()
    }

    private fun ProjectData.processLoginChange(event:ProjectLoginIntent): ProjectData = when (event) {
        is LoginEntered -> {
            dataStoreHelper.saveData(viewModelScope, event.param, event.name)
            copy(name = event.name, auth = true,beckAuth = false)
        }
        is NavigateTo -> navigateTo(event.intents)
    }

    private fun ProjectData.renderLoginInput(): ProjectViewState.LoginState = ProjectViewState.LoginState(
        beckAuth = beckAuth
    )

    private fun navigateTo(event: NavigatorIntent): ProjectData{
        _navigateChannel.trySend(event)
        return data.data.copy(beckAuth = false)
    }

    override fun onCleared() {
        super.onCleared()
        _navigateChannel.close()
    }
}