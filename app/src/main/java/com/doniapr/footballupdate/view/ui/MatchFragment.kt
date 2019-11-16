package com.doniapr.footballupdate.view.ui


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.MatchListAdapter
import com.doniapr.footballupdate.adapter.NextMatchAdapter
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.presenter.MatchPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.ListMatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

/**
 * A simple [Fragment] subclass.
 */
class MatchFragment(private val leagueId: Int) : Fragment(),
    ListMatchView {

    private lateinit var lastMatchList: RecyclerView
    private lateinit var nextMatchList: RecyclerView
    private var lastMatches: MutableList<Match> = mutableListOf()
    private var nextMatches: MutableList<Match> = mutableListOf()
    private lateinit var matchPresenter: MatchPresenter
    private lateinit var lastMatchAdapter: MatchListAdapter
    private lateinit var nextMatchAdapter: NextMatchAdapter
    private lateinit var progressBarLastMatch: ProgressBar
    private lateinit var progressBarNextMatch: ProgressBar
    private lateinit var txtFailedLastMatch: TextView
    private lateinit var txtFailedNextMatch: TextView
    private lateinit var linearLayout: LinearLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return UI {
            swipeRefreshLayout = swipeRefreshLayout {
                nestedScrollView {
                    linearLayout = verticalLayout {
                        gravity = Gravity.CENTER_HORIZONTAL
                        padding = dip(8)

                        textView {
                            text = getString(R.string.last_match)
                            textSize = 16f
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }

                        progressBarLastMatch =
                            progressBar().lparams(width = wrapContent, height = wrapContent)

                        lastMatchList = recyclerView {
                            layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(8)
                        }

                        txtFailedLastMatch = textView {
                            text = resources.getString(R.string.no_data)
                            textSize = 20f
                            visibility = View.GONE
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(16)
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        // Next Match
                        textView {
                            text = getString(R.string.next_match)
                            textSize = 16f
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(10)
                        }

                        progressBarNextMatch =
                            progressBar().lparams(width = wrapContent, height = wrapContent)

                        nextMatchList = recyclerView {
                            layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(8)
                        }

                        txtFailedNextMatch = textView {
                            text = resources.getString(R.string.no_data)
                            textSize = 20f
                            visibility = View.GONE
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            margin = dip(16)
                            gravity = Gravity.CENTER_HORIZONTAL
                        }
                    }
                }
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lastMatchAdapter = MatchListAdapter(lastMatches) {
            context?.startActivity<DetailMatchActivity>(
                DetailMatchActivity.EVENT_ID to it.eventId
            )
        }
        nextMatchAdapter = NextMatchAdapter(nextMatches) {
            context?.startActivity<DetailMatchActivity>(
                DetailMatchActivity.EVENT_ID to it.eventId
            )
        }

        lastMatchList.adapter = lastMatchAdapter
        nextMatchList.adapter = nextMatchAdapter

        matchPresenter = MatchPresenter(this)
        matchPresenter.getLastMatch(leagueId)
        matchPresenter.getNextMatch(leagueId)

        swipeRefreshLayout.onRefresh {
            matchPresenter.getLastMatch(leagueId)
            matchPresenter.getNextMatch(leagueId)
        }
    }

    override fun showLoading(isLastMatch: Boolean) {
        if (isLastMatch) {
            progressBarLastMatch.visible()
            lastMatchList.invisible()
        } else {
            progressBarNextMatch.visible()
            nextMatchList.invisible()
        }
    }

    override fun hideLoading(isLastMatch: Boolean) {
        if (isLastMatch) {
            progressBarLastMatch.invisible()
            lastMatchList.visible()

        } else {
            progressBarNextMatch.invisible()
            nextMatchList.visible()
        }
    }

    override fun showLastMatch(data: List<Match>) {
        runOnUiThread {
            hideLoading(true)
            swipeRefreshLayout.isRefreshing = false
            lastMatches.clear()
            lastMatches.addAll(data)
            lastMatchAdapter.notifyDataSetChanged()
        }
    }

    override fun showNextMatch(data: List<Match>) {
        runOnUiThread {
            hideLoading(false)
            swipeRefreshLayout.isRefreshing = false
            nextMatches.clear()
            nextMatches.addAll(data)
            nextMatchAdapter.notifyDataSetChanged()
        }
    }

    override fun onFailed(type: Int, isLastMatch: Boolean) {
        runOnUiThread {
            val message: String = when (type) {
                1 -> getString(R.string.no_data)
                2 -> getString(R.string.no_internet)
                else -> ""
            }
            swipeRefreshLayout.isRefreshing = false
            if (isLastMatch) {
                txtFailedLastMatch.visible()
                hideLoading(isLastMatch)
            } else {
                txtFailedNextMatch.visible()
                hideLoading(isLastMatch)
            }
            swipeRefreshLayout.snackbar(message).show()
        }
    }


}
