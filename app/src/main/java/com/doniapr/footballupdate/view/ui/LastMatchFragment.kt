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
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.presenter.LastMatchPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.LastMatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class LastMatchFragment(private val leagueId: Int) : Fragment(),
    LastMatchView {

    private lateinit var lastMatchList: RecyclerView
    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: LastMatchPresenter
    private lateinit var adapter: MatchListAdapter
    private lateinit var progressBarLastMatch: ProgressBar
    private lateinit var txtFailed: TextView
    private lateinit var linearLayout: LinearLayout
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return UI {
            swipeRefreshLayout = swipeRefreshLayout {
                linearLayout = verticalLayout {
                    gravity = Gravity.CENTER_HORIZONTAL
                    padding = dip(8)

                    lastMatchList = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                    progressBarLastMatch =
                        progressBar().lparams(width = wrapContent, height = wrapContent)

                    txtFailed = textView {
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

        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MatchListAdapter(matches) {
            context?.startActivity<DetailMatchActivity>(
                DetailMatchActivity.EVENT_ID to it.eventId
            )
        }
        lastMatchList.adapter = adapter

        presenter = LastMatchPresenter(this)
        presenter.getLastMatch(leagueId)

        swipeRefreshLayout.onRefresh {
            presenter.getLastMatch(leagueId)
        }
    }

    override fun showLoading() {
        progressBarLastMatch.visible()
    }

    override fun hideLoading() {
        progressBarLastMatch.invisible()
    }

    override fun showMatchList(data: List<Match>) {
        runOnUiThread {
            hideLoading()
            swipeRefreshLayout.isRefreshing = false
            matches.clear()
            matches.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onFailed(type: Int) {
        runOnUiThread {
            val message: String = when (type) {
                1 -> getString(R.string.no_data)
                2 -> getString(R.string.no_internet)
                else -> ""
            }
            hideLoading()
            swipeRefreshLayout.isRefreshing = false
            txtFailed.visible()
            swipeRefreshLayout.snackbar(message).show()
        }
    }
}
