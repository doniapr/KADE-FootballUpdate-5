package com.doniapr.footballupdate.view.ui.favorite


import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.favoriteadapter.FavoriteTeamAdapter
import com.doniapr.footballupdate.model.favorite.FavoriteMatch
import com.doniapr.footballupdate.model.favorite.FavoriteTeam
import com.doniapr.footballupdate.presenter.FavoritePresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity.Companion.TEAM_DETAIL_ID
import com.doniapr.footballupdate.view.ui.detailteam.DetailTeamActivity.Companion.TEAM_DETAIL_NAME
import com.doniapr.footballupdate.view.viewinterface.FavoriteView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamFragment : Fragment(),
    FavoriteView {
    private lateinit var linearLayout: LinearLayout
    private lateinit var favTeamList: RecyclerView
    private lateinit var txtFailed: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var favoriteTeam: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var presenter: FavoritePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return UI {
            linearLayout = verticalLayout {
                lparams(width = matchParent, height = wrapContent)
                gravity = Gravity.CENTER_HORIZONTAL
                padding = dip(8)

                swipeRefresh = swipeRefreshLayout {

                    verticalLayout {
                        txtFailed = textView {
                            text = resources.getString(R.string.no_data)
                            textSize = 20f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }

                        favTeamList = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(context)
                        }
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }
        }.view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter =
            FavoriteTeamAdapter(favoriteTeam) {
                context?.startActivity<DetailTeamActivity>(
                    TEAM_DETAIL_ID to it.teamId?.toInt(),
                    TEAM_DETAIL_NAME to it.teamName
                )
            }

        favTeamList.adapter = adapter
        presenter = FavoritePresenter(this, context)

        swipeRefresh.onRefresh {
            favoriteTeam.clear()
            presenter.getFavoriteTeam()
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteTeam.clear()
        presenter.getFavoriteTeam()
    }

    override fun onFailed() {
        swipeRefresh.isRefreshing = false
        txtFailed.visible()
    }

    override fun showFavorite(data: List<FavoriteMatch>) {}

    override fun showFavoriteTeam(data: List<FavoriteTeam>) {
        favoriteTeam.clear()
        swipeRefresh.isRefreshing = false
        favoriteTeam.addAll(data)
        adapter.notifyDataSetChanged()
        txtFailed.invisible()
    }


}
