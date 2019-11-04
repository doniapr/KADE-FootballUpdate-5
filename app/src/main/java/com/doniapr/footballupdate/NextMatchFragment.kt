package com.doniapr.footballupdate


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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.adapter.MatchListAdapter
import com.doniapr.footballupdate.adapter.NextMatchAdapter
import com.doniapr.footballupdate.model.LeagueDetail
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.Team
import com.doniapr.footballupdate.presenter.MainPresenter
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.MainView
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

/**
 * A simple [Fragment] subclass.
 */
class NextMatchFragment(private val leagueId: Int) : Fragment(), MainView {

    private lateinit var nextMatchList: RecyclerView
    private var matches: MutableList<Match> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: NextMatchAdapter
    private lateinit var progress_bar_next_match: ProgressBar
    private lateinit var txt_failed: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return UI {
            linearLayout {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER

                nextMatchList = recyclerView{
                    lparams(width= matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(context)
                }
                progress_bar_next_match = progressBar().lparams(width= wrapContent, height = wrapContent)

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NextMatchAdapter(matches)
        nextMatchList.adapter = adapter

        presenter = MainPresenter(this)
        presenter.getNextMatch(leagueId)
    }

    override fun showLoading() {
        progress_bar_next_match.visible()
    }

    override fun hideLoading() {
        progress_bar_next_match.invisible()
    }

    override fun onFailed(message: String?) {
        txt_failed.visible()

        if (message == "timeout"){
            Snackbar.make(txt_failed, "Mohon Periksa koneksi internet anda", Snackbar.LENGTH_SHORT)
        }else{
            Snackbar.make(txt_failed, message.toString(), Snackbar.LENGTH_SHORT)
        }

    }

    override fun showLeagueDetail(data: List<LeagueDetail>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMatchList(data: List<Match>) {
        matches.clear()
        matches.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showMatchDetail(data: Match) {

    }

    override fun showTeam(data: Team, isHome: Boolean) {

    }
}
