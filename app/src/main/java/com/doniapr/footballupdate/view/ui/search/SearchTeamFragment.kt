package com.doniapr.footballupdate.view.ui.search


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
import com.doniapr.footballupdate.adapter.searchadapter.SearchResultTeamAdapter
import com.doniapr.footballupdate.model.match.Match
import com.doniapr.footballupdate.model.team.Team
import com.doniapr.footballupdate.presenter.SearchPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity.Companion.TEAM_DETAIL_ID
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity.Companion.TEAM_DETAIL_NAME
import com.doniapr.footballupdate.view.viewinterface.SearchResultView
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class SearchTeamFragment(private val query: String?, private val leagueName: String?) : Fragment(),
    SearchResultView {

    private lateinit var rvSearchResult: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: SearchPresenter
    private lateinit var adapter: SearchResultTeamAdapter
    private lateinit var txtFailed: TextView
    private lateinit var txtQuery: TextView

    private var teams: MutableList<Team> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return UI {
            swipeRefreshLayout = swipeRefreshLayout {
                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.CENTER_HORIZONTAL

                    txtQuery = textView {
                        id = R.id.txt_query
                        textSize = 16f
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        margin = dip(16)
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    progressBar =
                        progressBar().lparams(width = wrapContent, height = wrapContent)

                    txtFailed = textView {
                        id = R.id.txt_failed_team
                        text = resources.getString(R.string.no_data)
                        textSize = 20f
                        visibility = View.GONE
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                        margin = dip(16)
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                    rvSearchResult = recyclerView {
                        id = R.id.rv_search_result_team
                        layoutManager = LinearLayoutManager(context)
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }

            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SearchResultTeamAdapter(teams) {
            context?.startActivity<DetailTeamActivity>(
                TEAM_DETAIL_ID to it.teamId,
                TEAM_DETAIL_NAME to it.teamName
            )

        }
        presenter = SearchPresenter(this)

        val textQuery = if (leagueName == null) {
            presenter.doSearchTeam(query)
            getString(R.string.search_result_for) + " '$query'"
        } else {
            presenter.doSearchTeamInLeague(query, leagueName)
            getString(R.string.search_result_for) + " '$query' dalam liga '$leagueName'"
        }

        txtQuery.text = textQuery

        rvSearchResult.adapter = adapter
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun onFailed(type: Int) {
        runOnUiThread {
            val message: String = when (type) {
                1 -> getString(R.string.no_data)
                2 -> getString(R.string.no_internet)
                else -> ""
            }
            hideLoading()
            txtFailed.text = message
            txtFailed.visible()

            swipeRefreshLayout.snackbar(message).show()
        }
    }

    override fun showMatchList(data: List<Match>) {}

    override fun showTeamList(data: List<Team>) {
        runOnUiThread {
            hideLoading()
            teams.clear()
            teams.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }


}
