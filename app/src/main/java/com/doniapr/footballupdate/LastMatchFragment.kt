package com.doniapr.footballupdate


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.adapter.MatchListAdapter
import com.doniapr.footballupdate.model.LeagueDetail
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team
import com.doniapr.footballupdate.presenter.MainPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.MainView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_last_match.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

/**
 * A simple [Fragment] subclass.
 */
class LastMatchFragment(private val leagueId: Int) : Fragment(), MainView {

    private lateinit var lastMatchList: RecyclerView
    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: MatchListAdapter
    private lateinit var progress_bar_last_match: ProgressBar
    private lateinit var txt_failed: TextView
    private lateinit var linearLayout: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return UI {
            linearLayout = verticalLayout {
                gravity = Gravity.CENTER_HORIZONTAL
                padding = dip(8)

                lastMatchList = recyclerView{
                    lparams(width= matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(context)
                }
                progress_bar_last_match = progressBar().lparams(width= wrapContent, height = wrapContent)

                txt_failed = textView {
                    text = resources.getString(R.string.no_data)
                    textSize = 20f
                    visibility = View.GONE
                }.lparams{
                    width = matchParent
                    height = wrapContent
                    margin = dip(16)
                    gravity = Gravity.CENTER_HORIZONTAL
                }
            }
        }.view
//        inflater.inflate(R.layout.fragment_last_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MatchListAdapter(matches)
        lastMatchList.adapter = adapter

        presenter = MainPresenter(this)
        presenter.getLastMatch(leagueId)
    }

    override fun showLoading() {
        progress_bar_last_match.visible()
    }

    override fun hideLoading() {
        progress_bar_last_match.invisible()
    }

    override fun showLeagueDetail(data: List<LeagueDetail>?) {}

    override fun showMatchList(data: List<Match>) {
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onFailed(message: String?) {
        txt_failed.visible()
        if (message == "timeout"){
            Snackbar.make(linearLayout, "Mohon periksa koneksi internet anda", Snackbar.LENGTH_SHORT).show()
        }else{
            Snackbar.make(linearLayout, "Mohon periksa koneksi internet anda", Snackbar.LENGTH_SHORT).show()
        }

    }

    override fun showMatchDetail(data: Match) {

    }

    override fun showTeam(data: Team, isHome: Boolean) {

    }

}
