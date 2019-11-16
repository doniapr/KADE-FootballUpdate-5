package com.doniapr.footballupdate.view.ui


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
import com.doniapr.footballupdate.adapter.FavoriteLastMatchAdapter
import com.doniapr.footballupdate.model.FavoriteMatch
import com.doniapr.footballupdate.model.FavoriteTeam
import com.doniapr.footballupdate.presenter.FavoritePresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.FavoriteView
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class FavoriteLastMatchFragment : Fragment(), FavoriteView {

    private lateinit var linearLayout: LinearLayout
    private lateinit var lastMatchList: RecyclerView
    private lateinit var txtFailed: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var favoriteMatches: MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter: FavoriteLastMatchAdapter
    private lateinit var presenter: FavoritePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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

                        lastMatchList = recyclerView {
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

        adapter = FavoriteLastMatchAdapter(favoriteMatches) {
            context?.startActivity<DetailMatchActivity>(
                DetailMatchActivity.EVENT_ID to it.eventId?.toInt()
            )
        }

        lastMatchList.adapter = adapter
        presenter = FavoritePresenter(this, context)

        swipeRefresh.onRefresh {
            favoriteMatches.clear()
            presenter.getFavorite(true)
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteMatches.clear()
        presenter.getFavorite(true)
    }

    override fun onFailed() {
        swipeRefresh.isRefreshing = false
        txtFailed.visible()
    }

    override fun showFavorite(data: List<FavoriteMatch>) {
        favoriteMatches.clear()
        swipeRefresh.isRefreshing = false
        favoriteMatches.addAll(data)
        adapter.notifyDataSetChanged()
        txtFailed.invisible()
    }

    override fun showFavoriteTeam(data: List<FavoriteTeam>) {}
}
