package ru.lt.project1_ur.state

import android.view.MenuItem
import ru.lt.project1_ur.data.CatalogItems

sealed class ProjectPersonIntent {
    object LoadInitialData : ProjectPersonIntent()
    data class ModelEntered(val model: ProjectViewState.Person) : ProjectPersonIntent()
    data class AddRecyclerView(val personList: List<ProjectViewState.Person>) : ProjectPersonIntent()
    data class ScrollUp(val recyclerView: androidx.recyclerview.widget.RecyclerView) : ProjectPersonIntent()
    object NavigateTo : ProjectPersonIntent()
}

sealed class ProjectCatalogIntent {
    object LoadInitialData : ProjectCatalogIntent()
    data class ModelEntered(val model: ProjectViewState.Catalog) : ProjectCatalogIntent()
    data class AddRecyclerView(val catalogList: List<ProjectViewState.Catalog>) : ProjectCatalogIntent()
    object NavigateTo : ProjectCatalogIntent()
}
sealed class ProjectCartIntent {
    object NavigateTo : ProjectCartIntent()
}
sealed class ProjectChatIntent {
    data class SendMessage(val message: ProjectViewState.Message) : ProjectChatIntent()
    data class BotSendMessage(val avatar: String?) : ProjectChatIntent()
}

sealed class ProjectStartIntent {
    data class  LoadInitialData(val name: String?, val auth: Boolean) : ProjectStartIntent()
    object NavigateTo : ProjectStartIntent()
}

sealed class ProjectLoginIntent {
    data class LoginEntered(val param: Int,val name: String,val pass: String) : ProjectLoginIntent()
    data class NavigateTo(val intents: NavigatorIntent) : ProjectLoginIntent()
}

sealed class ProjectBaseIntent {
    object LoadInitialData : ProjectBaseIntent()
    object SwitchThemeOff : ProjectBaseIntent()
    data class MenuItemClick(val menuItem: MenuItem) : ProjectBaseIntent()
    object NavigateTo : ProjectBaseIntent()
    data class SwitchTheme(val isDarkTheme: Boolean) : ProjectBaseIntent()
    object Logout : ProjectBaseIntent()
}