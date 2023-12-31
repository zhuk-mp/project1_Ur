package ru.lt.project1_ur.data

import ru.lt.project1_ur.state.ProjectViewState

data class ProjectData(
    val name: String? = null,
    val isDarkTheme: Boolean = true,
    val auth: Boolean = false,
    val beckAuth: Boolean = false,
    val typeId: Int? = null,
    val type: String? = null,
    val itemId: Int? = null,
    val itemName: String? = null,
    val itemAvatar: String? = null,
    val itemSpec: String? = null,
    val itemDesc: String? = null,
    val itemPhone: String? = null,
    val itemAddress: String? = null,
    val itemAddressFull: String? = null,
    val itemView: Int? = null,
    val itemRew: Int? = null,
    val personList: List<ProjectViewState.Person> = emptyList(),
    val catalogList: List<ProjectViewState.Catalog> = emptyList(),
    val messageList: MutableList<ProjectViewState.Message> = mutableListOf(),

    )