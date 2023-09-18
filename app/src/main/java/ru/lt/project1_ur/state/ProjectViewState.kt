package ru.lt.project1_ur.state

sealed class ProjectViewState {
    data class Start(
        val name: String? = null,
        val auth: Boolean = false,
    )
    data class CatalogState(
        val navi: Boolean,
        val itemId: Int?,
        val type: String?,
        val auth: Boolean
    )

    data class Catalog(
        val id: Int,
        val avatar: String,
        val sfera: String,
    )
    data class PersonState(
        val navi: Boolean,
        val itemId: Int?,
        val itemName: String?,
        val type: String?,
        val auth: Boolean
    )
    data class LoginState(
        val beckAuth: Boolean
    )
    data class ChatState(
        val itemName: String?,
        val type: String?
    )

    data class BaseState(
        val menu1: String,
        val menu2: String,
        val menu2IsVisible: Boolean,
        val isDarkTheme: Boolean,
        val isSwitchTheme: Boolean = false,
    )

    data class Person(
        val id: Int,
        val avatar: String,
        val name: String,
        val spec: String?,
        val phone: String?,
        val address: String?,
        val addressFull: String?,
        val desc: String?,
        val count: Int = 0,
        val stars: Int = 0
    )

    data class Cart(
        val id: Int,
        val avatar: String?,
        val name: String,
        val spec: String?,
        val type: String?,
        val phone: String?,
        val address: String?,
        val desc: String?,
        val count: Int?,
        val stars: Int?,
        val auth: Boolean
    )

    data class Message(
        val text: String,
        val isUser: Boolean,
        val avatar: String,
    )
}