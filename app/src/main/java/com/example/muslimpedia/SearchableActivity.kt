package com.example.muslimpedia

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muslimpedia.adapter.NewsAdapter
import com.example.muslimpedia.data.repository.NewsRepository
import com.example.muslimpedia.databinding.ActivitySearchableBinding
import com.example.muslimpedia.ui.NewsViewModel
import com.example.muslimpedia.utils.NewsViewModelFactory

class SearchableActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchableBinding
    private val searchModel: NewsViewModel by viewModels {
        NewsViewModelFactory(NewsRepository())
    }
    private var querySearch: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchableBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        handleIntent(intent)
        searchModel.searchNews.observe(this@SearchableActivity){ dataSearch ->
            binding.apply {
                if (dataSearch.articles.isEmpty()){
                    tvNoNews.text = "No News About"
                    tvNoNews.visibility = View.VISIBLE
                }
                else{
                    rvSearchResults.apply {
                        val mAdapter = NewsAdapter()
                        mAdapter.setData(dataSearch.articles)

                        adapter = mAdapter
                        layoutManager = LinearLayoutManager(this@SearchableActivity)
                        visibility = View.VISIBLE
                    }
                }
            }
        }
        searchModel.isLoading.observe(this@SearchableActivity){
            showLoading(it)
        }
        val searchManager = getSystemService(Context.SEARCH_SERVICE)as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun showLoading(is_loading: Boolean) {
        if (is_loading){
            binding.loadingView.root.visibility = View.VISIBLE
        }else{
            binding.loadingView.root.visibility = View.GONE
        }
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action){
            intent.getStringExtra(SearchManager.QUERY.also { query ->
                querySearch = query

                binding.apply {
                    rvSearchResults.visibility = View.GONE
                    tvNoNews.visibility = View.VISIBLE
                    searchView.setQuery("", false)
                    searchView.queryHint = query
                    searchView.clearFocus()
                }

                doMySearch(query)
            })

        }

    }

    private fun doMySearch(query: String) {
        searchModel.getSearchNews(query)

    }
}