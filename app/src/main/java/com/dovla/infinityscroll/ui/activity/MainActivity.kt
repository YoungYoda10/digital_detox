package com.dovla.infinityscroll.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dovla.infinityscroll.databinding.ActivityMainBinding
import com.dovla.infinityscroll.ui.adapter.RecyclerViewAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var rowsArrayList: ArrayList<String?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        populateData()
        initAdapter()
        initScrollListener()
    }

    private fun populateData() {
        var i = 0
        while (i < 10) {
            rowsArrayList.add("Item $i")
            i++
        }
    }

    private fun initAdapter() {
        recyclerViewAdapter = RecyclerViewAdapter(rowsArrayList)
        binding.recyclerView.adapter = recyclerViewAdapter
    }

    private fun initScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                linearLayoutManager?.let {
                    if (it.findLastCompletelyVisibleItemPosition() == rowsArrayList.size - 1) {
                        loadMore()
                    }
                }
            }
        })
    }

    private fun loadMore() {
        rowsArrayList.add(null)
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            rowsArrayList.removeAt(rowsArrayList.size - 1)
            val scrollPosition: Int = rowsArrayList.size
            recyclerViewAdapter!!.notifyItemRemoved(scrollPosition)
            var currentSize = scrollPosition
            val nextLimit = currentSize + 1
            while (currentSize - 1 < nextLimit) {
                rowsArrayList.add("Item $currentSize")
                currentSize++
            }
            recyclerViewAdapter!!.notifyDataSetChanged()
        }
    }
}