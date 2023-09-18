package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.lt.project1_ur.R
import ru.lt.project1_ur.data.DataStoreHelper
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class StartFragmentViewModel @Inject constructor(
    val data: RetainedProjectData,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel(){
    val viewState: MutableLiveData<ProjectViewState.Start> = MutableLiveData()

    init {
        viewModelScope.launch {
            dataStoreHelper.getData(R.string.start_name).collect { value ->
                if (value != null && value != "") {
                    data.data = data.data.processStartChange(value, true)
                }
                viewState.value = data.data.renderStartInput()

            }
        }
    }

    private fun ProjectData.processStartChange(name: String, auth: Boolean): ProjectData = copy(name = name, auth = auth)

    private fun ProjectData.renderStartInput(): ProjectViewState.Start = ProjectViewState.Start(
        name = name,
        auth = auth
    )

}