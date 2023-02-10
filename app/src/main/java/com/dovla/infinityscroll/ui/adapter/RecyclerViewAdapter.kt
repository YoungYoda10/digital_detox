package com.dovla.infinityscroll.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.dovla.infinityscroll.R
import com.dovla.infinityscroll.databinding.ItemRowBinding
import com.dovla.infinityscroll.ui.adapter.RecyclerViewAdapter.MyViewHolder

class RecyclerViewAdapter(private var mItemList: List<String?>) : Adapter<MyViewHolder>() {

    class MyViewHolder(binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }
}