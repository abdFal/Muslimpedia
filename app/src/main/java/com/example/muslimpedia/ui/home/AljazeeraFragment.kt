package com.example.muslimpedia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muslimpedia.R
import com.example.muslimpedia.adapter.NewsAdapter
import com.example.muslimpedia.data.repository.NewsRepository
import com.example.muslimpedia.databinding.FragmentAljazeeraBinding
import com.example.muslimpedia.databinding.FragmentCommonBinding
import com.example.muslimpedia.ui.NewsViewModel
import com.example.muslimpedia.utils.NewsViewModelFactory

class AljazeeraFragment : Fragment() {
    private lateinit var binding: FragmentAljazeeraBinding
    private val aljazeeraViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(NewsRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAljazeeraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myAdapter = NewsAdapter()
        if (aljazeeraViewModel.alJazeeraNews.value == null){
            aljazeeraViewModel.getAlJazeraNews()
        }
        aljazeeraViewModel.getAlJazeraNews()
        aljazeeraViewModel.alJazeeraNews.observe(viewLifecycleOwner){data ->
            myAdapter.setData(data.articles)
            binding.rvAljazeeraNews.apply {
                adapter = myAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        aljazeeraViewModel.isLoading.observe(viewLifecycleOwner){
            isLoading(it)
        }
    }

    private fun isLoading(is_loading: Boolean) {
        if (is_loading){
            binding.loadingView.root.visibility = View.VISIBLE
        }else{
            binding.loadingView.root.visibility = View.GONE
        }
    }
}