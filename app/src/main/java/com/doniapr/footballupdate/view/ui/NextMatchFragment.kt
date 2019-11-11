package com.doniapr.footballupdate.view.ui


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.NextMatchAdapter
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.presenter.NextMatchPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.NextMatchView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment(private val leagueId: Int) : Fragment(),
    NextMatchView {

    private lateinit var nextMatchList: RecyclerView
    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: NextMatchPresenter
    private lateinit var adapter: NextMatchAdapter
    private lateinit var progressBarNextMatch: ProgressBar
    private lateinit var txtFailed: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return UI {
            swipeRefreshLayout = swipeRefreshLayout {
                verticalLayout {
                    gravity = Gravity.CENTER_HORIZONTAL

                    nextMatchList = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }
                    progressBarNextMatch =
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

        adapter = NextMatchAdapter(matches) {
            context?.startActivity<DetailMatchActivity>(
                DetailMatchActivity.EVENT_ID to it.eventId
            )
        }
        nextMatchList.adapter = adapter

        presenter = NextMatchPresenter(this, context)
        presenter.getNextMatch(leagueId)

        swipeRefreshLayout.onRefresh {
            presenter.getNextMatch(leagueId)
        }
    }

    override fun showLoading() {
        progressBarNextMatch.visible()
    }

    override fun hideLoading() {
        progressBarNextMatch.invisible()
    }

    override fun onFailed(message: String?) {
        swipeRefreshLayout.isRefreshing = false
        txtFailed.visible()

        swipeRefreshLayout.snackbar(message.toString()).show()
    }

    override fun showMatchList(data: List<Match>) {
        swipeRefreshLayout.isRefreshing = false
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }
}
