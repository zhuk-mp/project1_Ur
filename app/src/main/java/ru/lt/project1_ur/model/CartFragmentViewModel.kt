package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.NavigatorIntent
import ru.lt.project1_ur.state.ProjectCartIntent
import ru.lt.project1_ur.state.ProjectCartIntent.NavigateTo
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val data: RetainedProjectData
) : ViewModel(){
    val viewState: MutableLiveData<ProjectViewState.Cart> = MutableLiveData()

    private val _navigateChannel = Channel<NavigatorIntent>()
    val navigateFlow: Flow<NavigatorIntent> = _navigateChannel.receiveAsFlow()

    init {
        viewState.value = data.data.renderCartInput()
    }

    fun processIntents(intents: ProjectCartIntent) {
        data.data = data.data.processCartChange(intents)
    }

    private fun ProjectData.processCartChange(event: ProjectCartIntent): ProjectData = when (event) {
        NavigateTo -> navigateTo(auth)
    }

    private fun ProjectData.renderCartInput(): ProjectViewState.Cart = ProjectViewState.Cart(
        id = itemId ?: 0,
        avatar = itemAvatar,
        name =  itemName ?: "",
        spec = itemSpec,
        desc = itemDesc,
        type = type,
        address = itemAddressFull,
        count = itemView,
        stars = itemRew,
        phone = itemPhone,
        auth = auth
    )

    private fun navigateTo(auth: Boolean): ProjectData{
        if (auth) {
            _navigateChannel.trySend(NavigatorIntent.ToChat)
        } else {
            _navigateChannel.trySend(NavigatorIntent.ToLogin)
        }
        return data.data.copy(beckAuth = !auth)
    }

    override fun onCleared() {
        super.onCleared()
        _navigateChannel.close()
    }

}