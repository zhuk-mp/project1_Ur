package ru.lt.project1_ur.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.lt.project1_ur.data.ProjectData
import ru.lt.project1_ur.data.RetainedProjectData
import ru.lt.project1_ur.state.ProjectViewState
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val data: RetainedProjectData
) : ViewModel(){
    val viewState: MutableLiveData<ProjectViewState.Cart> = MutableLiveData()

    init {
        update()
    }

    fun update() {
        viewState.value = data.data.renderCartInput()
    }
    fun beckAuth() {
        data.data = data.data.beckAuth()
    }

    private fun ProjectData.beckAuth(): ProjectData = copy(beckAuth = true)

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

}