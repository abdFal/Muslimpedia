package com.example.muslimpedia

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import com.example.muslimpedia.adapter.SectionPagerAdapter
import com.example.muslimpedia.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setUpViewPager()
    }

    private fun setUpViewPager() {
        binding.vpNews.adapter = SectionPagerAdapter(this)
        val tabList = arrayOf(
            "Common",
            "About Quran",
            "Aljazeera",
            "Warn For You",
        )
        TabLayoutMediator(binding.tabs, binding.vpNews) {tab, page ->
            tab.text = tabList[page]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE)as SearchManager
        (menu?.findItem(R.id.search_menu)?.actionView as androidx.appcompat.widget.SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        return true
    }
}