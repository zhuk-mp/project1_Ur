package ru.lt.project1_ur.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.lt.project1_ur.R
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

class DataStoreHelper @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) {


    fun saveData(scope: CoroutineScope, key: Int, value: String) {
        scope.launch {
            val dataStoreKey = stringPreferencesKey(context.getString(key))
            dataStore.edit { settings ->
                settings[dataStoreKey] = value
                val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putBoolean("isUserLoggedIn", value.isNotEmpty())
                editor.apply()
            }
        }
    }

    fun getData(key: Int): Flow<String?> {
        val dataStoreKey = stringPreferencesKey(context.getString(key))
        return dataStore.data.map { preferences ->
            preferences[dataStoreKey]
        }
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        val dataStoreFile = File(context.filesDir, context.getString(R.string.data_store_file))
        return PreferenceDataStoreFactory.create { dataStoreFile }
    }
}
