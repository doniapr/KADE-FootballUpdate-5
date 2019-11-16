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
import com.doniapr.footballupdate.adapter.TeamAdapter
import com.doniapr.footballupdate.model.Team
import com.doniapr.footballupdate.presenter.TeamPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.TeamView
import com.doniapr.footballupdate.view.ui.DetailTeamActivity.Companion.TEAM_DETAIL_ID
import com.doniapr.footballupdate.view.ui.DetailTeamActivity.Companion.TEAM_DETAIL_NAME
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
class TeamsFragment(private val leagueId: Int) : Fragment(), TeamView {

    private lateinit var teamList: RecyclerView
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var txtFailed: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


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

                    progressBar =
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

                    teamList = recyclerView {
                        id = R.id.rv_list_league
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

        adapter = TeamAdapter(teams) {
            context?.startActivity<DetailTeamActivity>(
                TEAM_DETAIL_ID to it.teamId,
                TEAM_DETAIL_NAME to it.teamName
            )
        }

        teamList.adapter = adapter

        presenter = TeamPresenter(this)
        presenter.getTeams(leagueId)

        swipeRefreshLayout.onRefresh {
            presenter.getTeams(leagueId)
        }
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
            swipeRefreshLayout.isRefreshing = false
            hideLoading()
            swipeRefreshLayout.snackbar(message).show()
        }
    }

    override fun showTeamList(data: List<Team>) {
        runOnUiThread {
            hideLoading()
            swipeRefreshLayout.isRefreshing = false
            teams.clear()
            teams.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

}
