package com.anangkur.footbalku.feature.leagueDetail.prevMatch

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.anangkur.footbalku.R
import com.anangkur.footbalku.base.BaseErrorView
import com.anangkur.footbalku.base.BaseFragment
import com.anangkur.footbalku.data.model.Event
import com.anangkur.footbalku.data.model.League
import kotlinx.android.synthetic.main.fragment_match.*
import com.anangkur.footbalku.data.model.Result
import com.anangkur.footbalku.feature.leagueDetail.MatchActionListener
import com.anangkur.footbalku.feature.matchDetail.MatchDetailActivity
import com.anangkur.footbalku.util.*
import com.anangkur.footbalku.util.Const.ARGS_LEAGUE

class PrevMatchFragment: BaseFragment<PrevMatchViewModel>(), MatchActionListener{

    override val mLayout: Int
        get() = R.layout.fragment_match
    override val mViewModel: PrevMatchViewModel
        get() = obtainViewModel(PrevMatchViewModel::class.java)

    private lateinit var mAdapter: PrevMatchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.dataFromArgs = arguments?.getParcelable(ARGS_LEAGUE)
        setupAdapter()
        observeViewModel()
        mViewModel.refreshData()
        swipe_match.setOnRefreshListener {
            mViewModel.refreshData()
        }
    }

    private fun observeViewModel(){
        mViewModel.getPrevMatch().observe(this@PrevMatchFragment, Observer {result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    swipe_match.isRefreshing = false
                    error_match.endProgress()
                    error_match.gone()
                    swipe_match.visible()
                    val data = result.data?.events
                    data?.let { mAdapter.setRecyclerData(it) }
                }
                Result.Status.LOADING -> {
                    error_match.showProgress()
                    error_match.visible()
                    swipe_match.gone()
                }
                Result.Status.ERROR -> {
                    swipe_match.isRefreshing = false
                    error_match.showError(result.message?:"", errorType = BaseErrorView.ERROR_GENERAL)
                    error_match.setRetryClickListener { mViewModel.refreshData() }
                }
            }
        })
    }

    private fun setupAdapter(){
        mAdapter = PrevMatchAdapter(this)
        recycler_match.adapter = mAdapter
        recycler_match.setupRecyclerViewLinear(requireContext())
    }

    override fun onClickMatch(data: Event) {
        MatchDetailActivity.startActivity(requireContext(), data)
    }

    companion object{

        fun newInstance(data: League?) = PrevMatchFragment().apply {
            arguments = Bundle(1).apply {
                putParcelable(ARGS_LEAGUE, data)
            }
        }
    }
}