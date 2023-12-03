package com.vsple.githubtestappmeraz.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import calvarytemple.Repository.ResponseListener
import com.vsple.githubtestappmeraz.Adapter.RepoListAdapter
import com.vsple.githubtestappmeraz.ApiService.ApiClient
import com.vsple.githubtestappmeraz.ApiService.ApiInterface
import com.vsple.githubtestappmeraz.Models.SearchRepoModel.Item
import com.vsple.githubtestappmeraz.Repository.Repository
import com.vsple.githubtestappmeraz.Util.Utility
import com.vsple.githubtestappmeraz.ViewModel.MainViewModel
import com.vsple.githubtestappmeraz.ViewModel.MainViewModelFactory
import com.vsple.githubtestappmeraz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var repoListAdapter: RepoListAdapter? = null
    private var repoDataModelList: MutableList<Item>? = ArrayList()
    private var apiService: ApiInterface? = null
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1
    private var isFromSearch: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiClient.createService(ApiInterface::class.java, this)
        val repositories = Repository(apiService!!, this)

        mainViewModel = ViewModelProvider(
            this, MainViewModelFactory(repositories)
        )[MainViewModel::class.java]


        Utility.showLoader(binding.gifImageView)
        mainViewModel.getSearchRepoData("all", currentPage)

        binding.imageSearch.setOnClickListener {
            var textSearch = binding.searchView.text.toString().trim()
            currentPage = 1
            mainViewModel.getSearchRepoData(textSearch, currentPage)
            repoDataModelList?.clear()
        }

        mainViewModel.searchRepositoryLivedata.observe(

            this, androidx.lifecycle.Observer {
                when (it) {
                    is ResponseListener.Success -> {
                        if (it.data != null) {
                            Utility.hideLoader(binding.gifImageView)
                            Log.d("TAG", "datashow" + it.data.items.toString())
                            repoDataModelList!!.addAll(it.data.items)
                            if (repoDataModelList?.size!! > 0) {
                                setDataToAdapter(repoDataModelList!!)
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

    private fun setDataToAdapter(datalist: List<Item>) {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding?.recyclerView?.layoutManager = layoutManager
        repoListAdapter = RepoListAdapter(this@MainActivity, datalist)
        binding?.recyclerView?.adapter = repoListAdapter

        binding?.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 0) {
                        loadMoreItems()
                    }
                }
            }
        })
        repoListAdapter?.setOnClickListener(object :
            RepoListAdapter.OnClickListener {
            override fun llButtonViewDetails(position: Int) {
                val selectedRepo = repoDataModelList?.get(position)
                val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                intent.putExtra("selectedRepo", selectedRepo)
                startActivity(intent)
            }
        })

    }

    private fun loadMoreItems() {
        mainViewModel.getSearchRepoData("all", currentPage)
        currentPage++
        isLoading = true
        isLoading = false
    }
}
