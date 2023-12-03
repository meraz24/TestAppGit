package com.vsple.githubtestappmeraz.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import calvarytemple.Repository.ResponseListener
import com.vsple.githubtestappmeraz.Adapter.ContributorsAdaptor
import com.vsple.githubtestappmeraz.ApiService.ApiClient
import com.vsple.githubtestappmeraz.ApiService.ApiInterface
import com.vsple.githubtestappmeraz.Models.Contributors.ContributorsModelItem
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.Item
import com.vsple.githubtestappmeraz.Repository.Repository
import com.vsple.githubtestappmeraz.Util.Utility
import com.vsple.githubtestappmeraz.ViewModel.MainViewModel
import com.vsple.githubtestappmeraz.ViewModel.MainViewModelFactory
import com.vsple.githubtestappmeraz.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    private var selectedRepo: Item? = null
    lateinit var binding: ActivityDetailsBinding
    private lateinit var mainViewModel: MainViewModel
    private var contributorsAdaptor: ContributorsAdaptor? = null
    private var contributorsModelItem: MutableList<ContributorsModelItem>? = ArrayList()
    private var apiService: ApiInterface? = null
    private var loginName: String = ""
    private var repoName: String = ""
    private var url: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiClient.createService(ApiInterface::class.java, this)
        val repositories = Repository(apiService!!, this)

        mainViewModel = ViewModelProvider(
            this, MainViewModelFactory(repositories)
        )[MainViewModel::class.java]

        selectedRepo = intent.getSerializableExtra("selectedRepo") as? Item

        selectedRepo?.let {
            loginName = it.owner.login
            repoName = it.name
            url = it.html_url
            binding.tvDescription.text = it.description
            binding.tvProjectName.text = repoName
            binding.tvProjectUrl.text = url
        }

        binding.tvProjectUrl.setOnClickListener {
            val intent = Intent(this@DetailsActivity, WebAcitivyt::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }

        if (Utility.networkStatusCheck(this@DetailsActivity)) {
            mainViewModel.getContributors(loginName, repoName)
        } else {
            sequenceOf(
                Toast.makeText(
                    this@DetailsActivity,
                    "Please check your internet connection", Toast.LENGTH_SHORT
                )
            )
        }

        mainViewModel.contributorsLivedata.observe(

            this, androidx.lifecycle.Observer {
                when (it) {
                    is ResponseListener.Success -> {
                        if (it.data != null) {
                            Utility.hideLoader(binding.gifImageView)
                            contributorsModelItem!!.addAll(it.data)
                            Log.d(
                                "TAG",
                                "contributorsModelItem: " + contributorsModelItem.toString()
                            )
                            if (contributorsModelItem?.size!! > 0) {
                                setDataToAdapter(contributorsModelItem!!)
                            }
                        }
                    }

                    is ResponseListener.Failure -> {
                        Utility.hideLoader(binding.gifImageView)

                    }

                    is ResponseListener.Error -> {
                        Utility.hideLoader(binding.gifImageView)
                    }

                    else -> {
                        Utility.hideLoader(binding.gifImageView)

                    }
                }
            }
        )
    }

    private fun setDataToAdapter(datalist: List<ContributorsModelItem>) {
        val layoutManager =
            LinearLayoutManager(this@DetailsActivity, LinearLayoutManager.HORIZONTAL, false)
        binding?.recyclerView?.layoutManager = layoutManager
        contributorsAdaptor = ContributorsAdaptor(this@DetailsActivity, datalist)
        binding?.recyclerView?.adapter = contributorsAdaptor
    }
}