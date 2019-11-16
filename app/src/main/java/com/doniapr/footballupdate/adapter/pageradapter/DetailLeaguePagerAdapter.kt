package com.doniapr.footballupdate.adapter.pageradapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.view.ui.detailleague.MatchFragment
import com.doniapr.footballupdate.view.ui.detailleague.StandingsFragment
import com.doniapr.footballupdate.view.ui.detailleague.TeamsFragment

class DetailLeaguePagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val leagueId: Int
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(
        R.string.tab_text_league_1,
        R.string.tab_text_league_2,
        R.string.tab_text_league_3
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MatchFragment(leagueId)
            1 -> fragment =
                StandingsFragment(leagueId)
            2 -> fragment = TeamsFragment(leagueId)
        }

        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int = tabTitles.size

}