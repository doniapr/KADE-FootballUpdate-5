package com.doniapr.footballupdate.view.ui.detailteam


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
import com.doniapr.footballupdate.adapter.PlayerAdapter
import com.doniapr.footballupdate.model.player.Player
import com.doniapr.footballupdate.presenter.PlayerPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.ui.PlayerActivity
import com.doniapr.footballupdate.view.ui.PlayerActivity.Companion.PLAYER_ID
import com.doniapr.footballupdate.view.ui.PlayerActivity.Companion.PLAYER_NAME
import com.doniapr.footballupdate.view.viewinterface.PlayerView
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
class TeamPlayerFragment(private val teamId: Int) : Fragment(),
    PlayerView {

    private lateinit var playerList: RecyclerView
    private var players: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
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

                    progressBar = progressBar().lparams(width = wrapContent, height = wrapContent)

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

                    playerList = recyclerView {
                        id = R.id.rv_list_player
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

        adapter = PlayerAdapter(players)
        {
            context?.startActivity<PlayerActivity>(
                PLAYER_ID to it.idPlayer?.toInt(),
                PLAYER_NAME to it.namePlayer
            )
        }

        playerList.adapter = adapter

        presenter = PlayerPresenter(this)
        presenter.getTeamPlayers(teamId)

        swipeRefreshLayout.onRefresh {
            presenter.getTeamPlayers(teamId)
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
            txtFailed.visible()
            swipeRefreshLayout.snackbar(message).show()
        }
    }

    override fun showPlayerList(data: List<Player>) {
        runOnUiThread {
            hideLoading()
            swipeRefreshLayout.isRefreshing = false
            players.clear()
            players.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

}
