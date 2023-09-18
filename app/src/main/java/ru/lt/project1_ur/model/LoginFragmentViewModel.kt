package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.project1_ur.data.DataStoreHelper
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject
@HiltViewModel
class LoginFragmentViewModel @Inject constructor(
    val data: RetainedProjectData,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel(){


    val viewState: MutableLiveData<ProjectViewState.LoginState> = MutableLiveData()

    init {
        viewState.value = data.data.renderLoginInput()
    }


    fun onLoginEntered(param: Int, name: String, pass: String) {
        data.data = data.data.processLoginChange(name, true)
        dataStoreHelper.saveData(viewModelScope, param, data.data.name!!)
    }
    fun beckAuth() {
        data.data = data.data.beckAuth()
    }


    private fun ProjectData.processLoginChange(name: String, auth: Boolean): ProjectData = copy(name = name, auth = auth)
    private fun ProjectData.beckAuth(): ProjectData = copy(beckAuth = false)

    private fun ProjectData.renderLoginInput(): ProjectViewState.LoginState = ProjectViewState.LoginState(
        beckAuth = beckAuth
    )


}