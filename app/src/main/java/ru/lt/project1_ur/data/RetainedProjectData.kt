package ru.lt.project1_ur.data

import android.util.Log
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
@ActivityRetainedScoped
class RetainedProjectData @Inject constructor() {

    var data = ProjectData()
    fun log(string: String? = null){
        Log.d("log----------------", string ?: data.toString())
    }
}