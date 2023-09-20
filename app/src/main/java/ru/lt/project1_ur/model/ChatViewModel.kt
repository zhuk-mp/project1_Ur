package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.ProjectChatIntent
import ru.lt.project1_ur.state.ProjectViewState
import ru.lt.project1_ur.state.ProjectViewState.Message
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val data: RetainedProjectData
) : ViewModel() {

    val viewState: MutableLiveData<ProjectViewState.ChatState> = MutableLiveData()

    init {
        viewState.value = data.data.renderChatInput()
    }
    fun processIntents(intents: ProjectChatIntent, update: Boolean = false) {
        data.data = data.data.processChatChange(intents)
        if (update)
            viewState.value = data.data.renderChatInput()
    }

    private fun ProjectData.processChatChange(event: ProjectChatIntent): ProjectData = when (event) {
        is ProjectChatIntent.SendMessage -> {
            messageList.add(event.message)
            data.data.copy(messageList = messageList)
        }
        is ProjectChatIntent.BotSendMessage -> {
            val possibleAnswers = listOf("Привет!", "Как дела?", "Что делаешь?", "Невероятно!")
            val randomAnswer = possibleAnswers.random()
            val message = Message(randomAnswer, false, event.avatar?:"")
            messageList.add(message)
            data.data.copy(messageList = messageList)
        }
    }
    private fun ProjectData.renderChatInput(): ProjectViewState.ChatState = ProjectViewState.ChatState(
        itemName = itemName,
        type = type,
        messageList = messageList
    )
}
