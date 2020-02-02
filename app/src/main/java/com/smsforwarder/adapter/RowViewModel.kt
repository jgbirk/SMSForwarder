package com.smsforwarder.adapter

import androidx.annotation.LayoutRes
import androidx.databinding.BaseObservable

abstract class RowViewModel(
    @LayoutRes
    val resId: Int
) : BaseObservable() {
    companion object {
        const val POSITION_NOT_SET = -1
    }

    var bindingPosition: Int = POSITION_NOT_SET
}
