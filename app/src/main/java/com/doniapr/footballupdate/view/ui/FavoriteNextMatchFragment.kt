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
import com.doniapr.footballupdate.adapter.FavoriteNextMatchAdapter
import com.doniapr.footballupdate.favorite.Favorite
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
class FavoriteNextMatchFragment : Fragment(), FavoriteView {

    private lateinit var linearLayout: LinearLayout
    private lateinit var nextMatchList: RecyclerView
    private lateinit var txtFailed: TextView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private var favorites: MutableList<Favorite> = mutableListOf()
    private lateinit var adapter: FavoriteNextMatchAdapter
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

                        nextMatchList = recyclerView {
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

        adapter = FavoriteNextMatchAdapter(favorites)

        nextMatchList.adapter = adapter
        presenter = FavoritePresenter(this, context)

        swipeRefresh.onRefresh {
            favorites.clear()
            presenter.getFavorite(false)
        }
    }

    override fun onResume() {
        super.onResume()
        favorites.clear()
        presenter.getFavorite(false)
    }

    override fun onFailed() {
        swipeRefresh.isRefreshing = false
        txtFailed.visible()
    }

    override fun showFavorite(data: List<Favorite>) {
        favorites.clear()
        swipeRefresh.isRefreshing = false
        favorites.addAll(data)
        adapter.notifyDataSetChanged()
        txtFailed.invisible()
    }
}
