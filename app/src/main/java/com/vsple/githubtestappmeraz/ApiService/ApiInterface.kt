package com.vsple.githubtestappmeraz.ApiService

import com.vsple.githubtestappmeraz.Models.Contributors.ContributorsModel
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.SearchDataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Call<SearchDataModel>

    //GetContributors
    @GET("repos/{pathcontributors}/{pathrepo}/contributors")
    fun getContributors(
        @Path("pathcontributors") pathcontributors: String,
        @Path("pathrepo") pathrepo: String,
    ): Call<ContributorsModel>
}