package com.doniapr.footballupdate.view.ui


import android.graphics.Typeface
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
import com.doniapr.footballupdate.model.Team
import com.doniapr.footballupdate.presenter.DetailTeamPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.DetailTeamView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

/**
 * A simple [Fragment] subclass.
 */
class TeamInfoFragment(private val teamId: Int) : Fragment(), DetailTeamView {

    private lateinit var txtFailed: TextView
    private lateinit var txtName: TextView
    private lateinit var txtCountry: TextView
    private lateinit var txtLeague: TextView
    private lateinit var txtDesc: TextView
    private lateinit var txtStadium: TextView
    private lateinit var txtStadiumDesc: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var containerLayout: LinearLayout
    private lateinit var presenter: DetailTeamPresenter

    private lateinit var lastMatchList: RecyclerView
    private lateinit var nextMatchList: RecyclerView
    private var lastMatches: MutableList<Match> = mutableListOf()
    private var nextMatches: MutableList<Match> = mutableListOf()
    private lateinit var lastMatchAdapter: MatchListAdapter
    private lateinit var nextMatchAdapter: NextMatchAdapter
    private lateinit var progressBarLastMatch: ProgressBar
    private lateinit var progressBarNextMatch: ProgressBar
    private lateinit var txtFailedLastMatch: TextView
    private lateinit var txtFailedNextMatch: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return UI {
            swipeRefreshLayout = swipeRefreshLayout {
                nestedScrollView {
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
                        containerLayout = verticalLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(16)
                            gravity = Gravity.CENTER_HORIZONTAL

                            cardView {
                                padding = dip(8)
                                radius = 10f

                                verticalLayout {
                                    padding = dip(8)

                                    textView {
                                        text = context.getString(R.string.team_name)
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 14f
                                    }

                                    txtName = textView {
                                        text = context.getString(R.string.team_name)
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 18f
                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                        marginStart = dip(16)
                                    }

                                    textView {
                                        text = context.getString(R.string.country)
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 14f
                                    }

                                    txtCountry = textView {
                                        text = context.getString(R.string.country)
                                        textSize = 14f
                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                        marginStart = dip(16)
                                    }

                                    textView {
                                        text = context.getString(R.string.league)
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 14f
                                    }

                                    txtLeague = textView {
                                        text = context.getString(R.string.league)
                                        textSize = 14f
                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                        marginStart = dip(16)
                                    }
                                }

                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                margin = dip(8)
                            }

                            cardView {
                                radius = 10f
                                padding = dip(8)

                                verticalLayout {
                                    padding = dip(8)

                                    textView {
                                        text = context.getString(R.string.description)
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 14f
                                    }

                                    txtDesc = textView {
                                        text = context.getString(R.string.description)
                                        textSize = 14f
                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                        marginStart = dip(16)

                                    }
                                }
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                margin = dip(8)
                            }

                            cardView {
                                radius = 10f
                                padding = dip(8)

                                verticalLayout {
                                    padding = dip(8)

                                    textView {
                                        text = context.getString(R.string.stadium)
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 14f
                                    }

                                    txtStadium = textView {
                                        text = context.getString(R.string.stadium)
                                        textSize = 14f
                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                        marginStart = dip(16)
                                    }

                                    textView {
                                        text = context.getString(R.string.stadium_desc)
                                        typeface = Typeface.DEFAULT_BOLD
                                        textSize = 14f
                                    }

                                    txtStadiumDesc = textView {
                                        text = context.getString(R.string.stadium)
                                        textSize = 14f
                                    }.lparams {
                                        width = matchParent
                                        height = wrapContent
                                        marginStart = dip(16)
                                    }
                                }
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                margin = dip(8)
                            }

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
                                layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
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
                                layoutManager = LinearLayoutManager(
                                    context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
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

        presenter = DetailTeamPresenter(this)
        presenter.getTeamInfo(teamId.toString())
//        presenter.getLastMatch(teamId.toString())
//        presenter.getNextMatch(teamId.toString())

        swipeRefreshLayout.onRefresh {
            presenter.getTeamInfo(teamId.toString())
        }
    }

    override fun showLoading(type: Int) {
        when (type) {
            1 -> {
                progressBar.visible()
                containerLayout.invisible()
            }
            2 -> progressBarLastMatch.visible()
            3 -> progressBarNextMatch.visible()
        }
    }

    override fun hideLoading(type: Int) {
        runOnUiThread {
            when (type) {
                1 -> {
                    progressBar.invisible()
                }
                2 -> {
                    progressBarLastMatch.invisible()
                    if (lastMatches.size == 0) {
                        txtFailedLastMatch.visible()
                    }
                }
                3 -> {
                    progressBarNextMatch.invisible()
                    if (nextMatches.size == 0) {
                        txtFailedNextMatch.visible()
                    }
                }
            }
        }
    }

    override fun onFailed(type: Int) {
        runOnUiThread {
            val message: String = when (type) {
                1 -> getString(R.string.no_data)
                2 -> getString(R.string.no_internet)
                else -> ""
            }
            swipeRefreshLayout.isRefreshing = false
            containerLayout.invisible()
            swipeRefreshLayout.snackbar(message).show()
        }
    }

    override fun showTeamDetail(data: Team) {
        runOnUiThread {
            containerLayout.visible()
            swipeRefreshLayout.isRefreshing = false

            txtName.text = data.teamName
            txtCountry.text = data.teamCountry
            txtDesc.text = data.teamDescription
            txtLeague.text = data.teamLeague
            txtStadium.text = data.teamStadium
            txtStadiumDesc.text = data.teamStadiumDesc

            presenter.getLastMatch(teamId.toString())
            presenter.getNextMatch(teamId.toString())
        }
    }

    override fun showLastMatch(data: List<Match>) {
        runOnUiThread {
            lastMatches.clear()
            lastMatches.addAll(data)
            lastMatchAdapter.notifyDataSetChanged()
            hideLoading(2)
        }
    }

    override fun showNextMatch(data: List<Match>) {
        runOnUiThread {
            nextMatches.clear()
            nextMatches.addAll(data)
            nextMatchAdapter.notifyDataSetChanged()
            hideLoading(3)
        }
    }
}
