package com.dicoding.kabar.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.kabar.R
import com.dicoding.kabar.databinding.FragmentNewsBinding
import com.dicoding.kabar.viewmodelfactory.ViewModelFactory
import com.dicoding.moviecatalogue.valueobject.Status

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var fragementViewModel: NewsViewModel
    private lateinit var rvNews: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragementViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(NewsViewModel::class.java)

        fragementViewModel.setNews()

        val adapter = NewsAdapter()
        val layoutManager = LinearLayoutManager(activity)
        rvNews = view.findViewById(R.id.rv_news)

        rvNews.layoutManager = layoutManager
        rvNews.setHasFixedSize(true)
        adapter.notifyDataSetChanged()
        rvNews.adapter = adapter

        showLoading(true)
        fragementViewModel.getNews().observe(viewLifecycleOwner, { news ->
            if (news != null) {
                adapter.setData(news)
                adapter.notifyDataSetChanged()
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        val progressBar = ProgressBar(activity)
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}


