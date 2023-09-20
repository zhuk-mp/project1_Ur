package ru.lt.project1_ur.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.lt.project1_ur.R
import ru.lt.project1_ur.data.DataStoreHelper
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.NavigatorIntent.To
import ru.lt.project1_ur.state.NavigatorIntent
import ru.lt.project1_ur.state.ProjectBaseIntent
import ru.lt.project1_ur.state.ProjectBaseIntent.*
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
open class BaseFragmentViewModel  @Inject constructor(
    val data: RetainedProjectData,
    @ApplicationContext private val context: Context,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {

    val viewState: MutableLiveData<ProjectViewState.BaseState> = MutableLiveData()

    private val _navigateChannel = Channel<NavigatorIntent>()
    val navigateFlow: Flow<NavigatorIntent> = _navigateChannel.receiveAsFlow()


    init {
        processIntents(LoadInitialData,true)
    }
    fun processIntents(intents: ProjectBaseIntent, update: Boolean = false, isSwitchTheme: Boolean = false) {
        data.data = data.data.processBaseChange(intents)
        if (update)
            viewState.value = data.data.renderBaseInput(isSwitchTheme)
    }

    private fun switchTheme() {
        val preferences = context.getSharedPreferences("settings", MODE_PRIVATE)
        val theme = preferences.getString("theme", "light")
        val editor = preferences.edit()
        editor.putString("theme", if(theme == "light") "dark" else "light")
        editor.apply()
        processIntents(SwitchTheme(theme == "dark"), update = true, isSwitchTheme = true)
    }

    private fun ProjectData.processBaseChange(event: ProjectBaseIntent): ProjectData = when (event) {
        LoadInitialData -> copy()
        SwitchThemeOff -> copy()
        Logout -> copy(name = null, auth = false)
        is MenuItemClick -> handleCommonMenuItemClick(event.menuItem, auth)
        NavigateTo -> navigateTo()
        is SwitchTheme -> copy(isDarkTheme = event.isDarkTheme)
    }

    private fun ProjectData.renderBaseInput(isSwitchTheme: Boolean): ProjectViewState.BaseState = ProjectViewState.BaseState(
        menu1 = if (auth) name!! else "Войти",
        menu2 = if (auth) "Выйти" else "",
        menu2IsVisible = auth,
        isDarkTheme = isDarkTheme,
        isSwitchTheme = isSwitchTheme,
    )

    private fun handleCommonMenuItemClick(menuItem: MenuItem, auth: Boolean): ProjectData {
        when (menuItem.itemId) {
            R.id.option_1 -> {
                if (!auth) {
                    processIntents(NavigateTo)
                }
            }
            R.id.option_2 -> {
                dataStoreHelper.saveData(viewModelScope, R.string.start_name, "")
                processIntents(Logout,true)
            }
            R.id.option_3 -> {
                switchTheme()
            }
        }
        return data.data.copy()
    }

    private fun navigateTo(): ProjectData{
        _navigateChannel.trySend(To)
        return data.data.copy()
    }

    override fun onCleared() {
        super.onCleared()
        _navigateChannel.close()
    }
}
