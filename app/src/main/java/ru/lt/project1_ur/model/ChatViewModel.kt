package ru.lt.project1_ur.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.project1_ur.data.ChatAdapter
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val data: RetainedProjectData
) : ViewModel() {

    val viewState: MutableLiveData<ProjectViewState.ChatState> = MutableLiveData()

    val adapter by lazy { ChatAdapter() }
    private val _messages = MutableLiveData<List<ProjectViewState.Message>>()
    val messages: LiveData<List<ProjectViewState.Message>> = _messages

    init {
        viewState.value = data.data.renderChatInput()
    }

    fun addMessage(message: ProjectViewState.Message) {
        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()
        currentMessages.add(message)
        _messages.value = currentMessages
    }
    private fun ProjectData.renderChatInput(): ProjectViewState.ChatState = ProjectViewState.ChatState(
        itemName = itemName,
        type = type,
    )
}
