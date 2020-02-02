package com.smsforwarder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter(
    private val context: Context
) : RecyclerView.Adapter<BindingHolder>() {

    /**
     * This should return the resId of the view model
     */
    abstract override fun getItemViewType(position: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(context),
            viewType,
            parent,
            false)

        return BindingHolder(viewDataBinding)
    }
}
