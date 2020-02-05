package com.smsforwarder.view.log

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.smsforwarder.R
import com.smsforwarder.adapter.BaseAdapterImpl
import com.smsforwarder.databinding.FragmentLogsBinding
import com.smsforwarder.fragment.BasePresenterFragment
import com.smsforwarder.view.log.model.LogViewModel
import com.smsforwarder.view.main.MainActivity

class LogsFragment : BasePresenterFragment<LogContract.View, LogContract.Presenter>(), LogContract.View {

    override var presenter: LogContract.Presenter = LogPresenter()
    private lateinit var binding: FragmentLogsBinding
    private lateinit var adapter: BaseAdapterImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_logs, container, false)

        (requireActivity() as MainActivity).hideSendButton()

        setHasOptionsMenu(true)

        adapter = BaseAdapterImpl(requireContext())

        with(binding) {
            list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            list.adapter = adapter

            swipeLayout.setOnRefreshListener {
                presenter.loadLogs()
            }
        }

        presenter.loadLogs()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()

        inflater.inflate(R.menu.menu_logs, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when(item.itemId) {
            R.id.action_clear -> {
                presenter.clearLogs()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun showLogs(logs: List<LogViewModel>) {
        adapter.apply { items = logs }.also { it.notifyDataSetChanged() }
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
}
