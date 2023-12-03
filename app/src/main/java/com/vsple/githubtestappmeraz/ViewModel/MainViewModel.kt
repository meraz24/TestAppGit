package com.vsple.githubtestappmeraz.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import calvarytemple.Repository.ResponseListener
import com.vsple.githubtestappmeraz.Models.Contributors.ContributorsModel
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.SearchDataModel
import com.vsple.githubtestappmeraz.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {
    val searchRepositoryLivedata: LiveData<ResponseListener<SearchDataModel?>>
        get() = repository.searchRepLiveData

    fun getSearchRepoData(searchQuery: String, pageNo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRepoSearchData(searchQuery, pageNo)
        }
    }

    val contributorsLivedata: LiveData<ResponseListener<ContributorsModel?>>
        get() = repository.contributorLiveData

    fun getContributors(contributor: String,reponame:String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getContributors(contributor,reponame)
        }
    }
}