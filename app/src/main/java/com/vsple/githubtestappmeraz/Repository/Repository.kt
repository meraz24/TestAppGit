package com.vsple.githubtestappmeraz.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import calvarytemple.Repository.ResponseListener
import com.vsple.githubtestappmeraz.ApiService.ApiInterface
import com.vsple.githubtestappmeraz.Models.Contributors.ContributorsModel
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.SearchDataModel
import com.vsple.githubtestappmeraz.Util.Utility
import okhttp3.internal.UTC
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val apiInterface: ApiInterface, private val applicationContext: Context
) {

    private val searchRepoMutableLiveData =
        MutableLiveData<ResponseListener<SearchDataModel?>>()
    val searchRepLiveData: LiveData<ResponseListener<SearchDataModel?>>
        get() = searchRepoMutableLiveData


    fun getRepoSearchData(searchQuary:String,pageNo:Int) {

        if (Utility.networkStatusCheck(applicationContext)) {
            try {
                val call: Call<SearchDataModel> = apiInterface.searchRepositories(searchQuary,10,pageNo)
                call.enqueue(object : Callback<SearchDataModel?> {

                    override fun onResponse(
                        call: Call<SearchDataModel?>,
                        response: Response<SearchDataModel?>
                    ) {
                        if (response.code() in 200..299) {
                            searchRepoMutableLiveData.postValue(ResponseListener.Success(response.body()))
                            Log.d("TAG", "responseRESULTSHOW: "+response.body().toString())
                        } else {
                            try {
                                searchRepoMutableLiveData.postValue(
                                    ResponseListener.Failure(
                                        response.errorBody()!!.string()
                                    )
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onFailure(call: Call<SearchDataModel?>, t: Throwable) {
                        Utility.showShortMsg(applicationContext,"Something Went Wrong")

                    }
                })
            } catch (e: JSONException) {
                e.printStackTrace()
                Utility.showShortMsg(applicationContext,"Something Went Wrong")
            }
        }
    }

    private val contributorMutableLiveData =
        MutableLiveData<ResponseListener<ContributorsModel?>>()
    val contributorLiveData: LiveData<ResponseListener<ContributorsModel?>>
        get() = contributorMutableLiveData

    fun getContributors(contributor:String,repoName:String) {

        if (Utility.networkStatusCheck(applicationContext)) {
            try {
                val call: Call<ContributorsModel> = apiInterface.getContributors(contributor,repoName)
                call.enqueue(object : Callback<ContributorsModel?> {

                    override fun onResponse(
                        call: Call<ContributorsModel?>,
                        response: Response<ContributorsModel?>
                    ) {
                        if (response.code() in 200..299) {
                            contributorMutableLiveData.postValue(ResponseListener.Success(response.body()))
                            Log.d("TAG", "responseCOntributor: "+response.body().toString())
                        } else {
                            try {
                                contributorMutableLiveData.postValue(
                                    ResponseListener.Failure(
                                        response.errorBody()!!.string()
                                    )
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onFailure(call: Call<ContributorsModel?>, t: Throwable) {
                        Utility.showShortMsg(applicationContext,"Something Went Wrong")

                    }
                })
            } catch (e: JSONException) {
                e.printStackTrace()
                Utility.showShortMsg(applicationContext,"Something Went Wrong")
            }
        }
    }


}