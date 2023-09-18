package ru.lt.project1_ur.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.lt.project1_ur.R
import ru.lt.project1_ur.data.DataStoreHelper
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
open class BaseFragmentViewModel  @Inject constructor(
    val data: RetainedProjectData,
    @ApplicationContext private val context: Context,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {

    val viewState: MutableLiveData<ProjectViewState.BaseState> = MutableLiveData()
    val navigateToChatFragment: MutableLiveData<Boolean> = MutableLiveData(false)


    init {
        viewState.value = data.data.renderBaseInput()
    }

    private fun switchTheme() {

        val preferences = context.getSharedPreferences("settings", MODE_PRIVATE)
        val theme = preferences.getString("theme", "auto")
        val editor = preferences.edit()
        editor.putString("theme", if(theme == "light") "dark" else "light")
        editor.apply()
        data.data = data.data.switchTheme(theme == "dark")
        viewState.value = data.data.renderBaseInput(true)
    }

    fun isSwitchThemeOff() {
        viewState.value = viewState.value!!.copy(isSwitchTheme = false)
    }

    open fun handleCommonMenuItemClick(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.option_1 -> {
                if (!data.data.auth) {
                    navigateToChatFragment.value = true
                }
                true
            }
            R.id.option_2 -> {
                data.data = data.data.logout()
                dataStoreHelper.saveData(viewModelScope, R.string.start_name, "")
                viewState.value = data.data.renderBaseInput()
                true
            }
            R.id.option_3 -> {
                switchTheme()
                true
            }
            else -> false
        }
    }

    private fun ProjectData.logout(): ProjectData = copy(name = null, auth = false)
    private fun ProjectData.switchTheme(isDarkTheme: Boolean): ProjectData = copy(isDarkTheme = isDarkTheme)

    private fun ProjectData.renderBaseInput(isSwitchTheme: Boolean = false): ProjectViewState.BaseState = ProjectViewState.BaseState(
        menu1 = if (auth) name!! else "Войти",
        menu2 = if (auth) "Выйти" else "",
        menu2IsVisible = auth,
        isDarkTheme = isDarkTheme,
        isSwitchTheme = isSwitchTheme,
    )
}
