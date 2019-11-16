package com.doniapr.footballupdate.view.ui.detailleague


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
import com.doniapr.footballupdate.adapter.leaguedetailadapter.StandingsAdapter
import com.doniapr.footballupdate.model.standing.Standings
import com.doniapr.footballupdate.presenter.StandingPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity.Companion.TEAM_DETAIL_ID
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity.Companion.TEAM_DETAIL_NAME
import com.doniapr.footballupdate.view.viewinterface.StandingsView
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class StandingsFragment(private val leagueId: Int) : Fragment(),
    StandingsView {

    private lateinit var standingList: RecyclerView
    private var standings: MutableList<Standings> = mutableListOf()
    private lateinit var presenter: StandingPresenter
    private lateinit var adapter: StandingsAdapter
    private lateinit var progressBar: ProgressBar
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

                    constraintLayout {
                        lparams(width = matchParent, height = wrapContent)
                        id = R.id.layout_standing_title
                        padding = dip(8)
                        backgroundColor = android.R.color.holo_blue_light

                        textView {
                            id = R.id.txt_standing_title_pos
                            text = context.getString(R.string.pos)
                        }.lparams {
                            width = dip(30)
                            height = wrapContent
                            marginEnd = dip(16)
                            startToStart = R.id.layout_standing_title
                            topToTop = R.id.layout_standing_title
                            bottomToBottom = R.id.layout_standing_title
                        }

                        textView {
                            id = R.id.txt_standing_title_name
                            text = context.getString(R.string.team)
                        }.lparams {
                            width = matchConstraintMaxWidth
                            height = wrapContent
                            startToEnd = R.id.txt_standing_title_pos
                            topToTop = R.id.layout_standing_title
                            bottomToBottom = R.id.layout_standing_title
                            endToStart = R.id.txt_standing_title_play
                        }

                        textView {
                            id = R.id.txt_standing_title_play
                            text = context.getString(R.string.play)
                        }.lparams {
                            width = dip(20)
                            height = wrapContent
                            marginEnd = dip(10)
                            topToTop = R.id.txt_standing_title_name
                            bottomToBottom = R.id.txt_standing_title_name
                            endToStart = R.id.txt_standing_title_win
                        }
                        textView {
                            id = R.id.txt_standing_title_win
                            text = context.getString(R.string.win)
                        }.lparams {
                            width = dip(20)
                            height = wrapContent
                            marginEnd = dip(10)
                            topToTop = R.id.txt_standing_title_name
                            bottomToBottom = R.id.txt_standing_title_name
                            endToStart = R.id.txt_standing_title_draw
                        }
                        textView {
                            id = R.id.txt_standing_title_draw
                            text = context.getString(R.string.draw)
                        }.lparams {
                            width = dip(20)
                            height = wrapContent
                            marginEnd = dip(10)
                            topToTop = R.id.txt_standing_title_name
                            bottomToBottom = R.id.txt_standing_title_name
                            endToStart = R.id.txt_standing_title_loss
                        }
                        textView {
                            id = R.id.txt_standing_title_loss
                            text = context.getString(R.string.loss)
                        }.lparams {
                            width = dip(20)
                            height = wrapContent
                            marginEnd = dip(10)
                            topToTop = R.id.txt_standing_title_name
                            bottomToBottom = R.id.txt_standing_title_name
                            endToStart = R.id.txt_standing_title_gd
                        }
                        textView {
                            id = R.id.txt_standing_title_gd
                            text = context.getString(R.string.goal_different)
                        }.lparams {
                            width = dip(20)
                            height = wrapContent
                            marginEnd = dip(10)
                            topToTop = R.id.txt_standing_title_name
                            bottomToBottom = R.id.txt_standing_title_name
                            endToStart = R.id.txt_standing_title_pts
                        }

                        textView {
                            id = R.id.txt_standing_title_pts
                            text = context.getString(R.string.points)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            marginEnd = dip(10)
                            topToTop = R.id.txt_standing_title_name
                            bottomToBottom = R.id.txt_standing_title_name
                            endToEnd = R.id.layout_standing_title
                        }
                    }
                    progressBar =
                        progressBar().lparams(width = wrapContent, height = wrapContent)

                    standingList = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

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

        adapter =
            StandingsAdapter(standings) {
                context?.startActivity<DetailTeamActivity>(
                    TEAM_DETAIL_ID to it.teamId?.toInt(),
                    TEAM_DETAIL_NAME to it.teamName
                )
            }

        standingList.adapter = adapter

        presenter = StandingPresenter(this)
        presenter.getStanding(leagueId.toString())

        swipeRefreshLayout.onRefresh {
            presenter.getStanding(leagueId.toString())
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

    override fun showStandingList(data: List<Standings>) {
        runOnUiThread {
            hideLoading()
            swipeRefreshLayout.isRefreshing = false
            standings.clear()
            standings.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }
}
