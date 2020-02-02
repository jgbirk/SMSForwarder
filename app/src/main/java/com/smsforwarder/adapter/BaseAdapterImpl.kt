package com.smsforwarder.adapter

import android.content.Context
import com.smsforwarder.BR

open class BaseAdapterImpl(context: Context) : BaseAdapter(context) {

    lateinit var items: List<RowViewModel>

    override fun getItemViewType(position: Int) = items[position].resId

    override fun getItemCount() = if (::items.isInitialized) items.size else 0

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        items[position].bindingPosition = position

        holder.viewDataBinding.apply {
            setVariable(BR.viewModel, items[position])
            executePendingBindings()
        }
    }
}
