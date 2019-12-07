package com.anangkur.kotlinexpertsubmission.feature.leagueDetail.nextMatch

import android.view.View
import com.anangkur.kotlinexpertsubmission.R
import com.anangkur.kotlinexpertsubmission.base.BaseAdapter
import com.anangkur.kotlinexpertsubmission.data.model.Event
import com.anangkur.kotlinexpertsubmission.feature.leagueDetail.MatchActionListener
import com.anangkur.kotlinexpertsubmission.util.convertStringToDate
import com.anangkur.kotlinexpertsubmission.util.convertStringToTime
import kotlinx.android.synthetic.main.item_match_next.view.*

class NextMatchAdapter(private val actionListener: MatchActionListener): BaseAdapter<Event>(){
    override val layout: Int
        get() = R.layout.item_match_next

    override fun bind(data: Event, itemView: View, position: Int) {
        itemView.tv_title_match.text = data.strEvent
        itemView.tv_date_match.text = convertStringToDate(data.dateEvent?:"", data.strTime?:"")
        itemView.tv_time_match.text = convertStringToTime(data.dateEvent?:"", data.strTime?:"")
        itemView.setOnClickListener { actionListener.onClickMatch(data) }
    }

}