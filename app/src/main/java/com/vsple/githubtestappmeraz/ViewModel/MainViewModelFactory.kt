package com.vsple.githubtestappmeraz.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import calvarytemple.Repository.ResponseListener
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.SearchDataModel
import com.vsple.githubtestappmeraz.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


class MainViewModelFactory(private val repositories: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repositories) as T
    }
}