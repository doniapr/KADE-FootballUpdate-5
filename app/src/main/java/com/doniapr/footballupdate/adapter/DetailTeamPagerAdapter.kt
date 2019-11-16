package com.doniapr.footballupdate.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.view.ui.TeamInfoFragment
import com.doniapr.footballupdate.view.ui.TeamPlayerFragment

class DetailTeamPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val teamId: Int
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.tab_team_info, R.string.tab_team_player)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = TeamInfoFragment(teamId)
            1 -> fragment = TeamPlayerFragment(teamId)
        }

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int = tabTitles.size
}