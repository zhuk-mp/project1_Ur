package ru.lt.project1_ur.state

sealed class NavigatorIntent {
    object ToCart: NavigatorIntent()
    object ToLogin: NavigatorIntent()
    object ToPerson: NavigatorIntent()
    object ToChat: NavigatorIntent()
    object ToCatalog: NavigatorIntent()
    object ToWeb: NavigatorIntent()
    object To: NavigatorIntent()
}