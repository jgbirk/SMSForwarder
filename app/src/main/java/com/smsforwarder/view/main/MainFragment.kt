package com.smsforwarder.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.smsforwarder.R
import com.smsforwarder.adapter.BaseAdapterImpl
import com.smsforwarder.databinding.FragmentMainBinding
import com.smsforwarder.fragment.BasePresenterFragment
import com.smsforwarder.view.main.model.SMSViewModel

class MainFragment : BasePresenterFragment<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override var presenter: MainContract.Presenter = MainPresenter()
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: BaseAdapterImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        adapter = BaseAdapterImpl(requireContext())

        with(binding) {
            list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            list.adapter = adapter

            swipeLayout.setOnRefreshListener {
                presenter.loadSMS()
            }
        }

        presenter.loadSMS()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as MainActivity).showSendButton()
    }

    override fun showSMS(sms: List<SMSViewModel>) {
        adapter.apply { items = sms }.also { it.notifyDataSetChanged() }
    }

    override fun showSuccessMessage() {
        Snackbar.make(binding.root, R.string.sms_sent, Snackbar.LENGTH_LONG)
            .show()
    }

    override fun showLoading(message: String) {
        binding.swipeLayout.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeLayout.isRefreshing = false
    }

    override fun showError(throwable: Throwable, retry: (() -> Unit)?) {
        Snackbar.make(binding.root, R.string.unexpected_error, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry) { retry?.invoke() }
            .show()
    }

    fun sendSMS() = presenter.sendSMS()
}
