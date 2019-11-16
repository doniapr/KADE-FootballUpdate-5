package com.doniapr.footballupdate.adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.view.ui.SearchMatchFragment
import com.doniapr.footballupdate.view.ui.SearchTeamFragment

class SearchResultPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    private val query: String?,
    private val leagueName: String?
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles = intArrayOf(R.string.match, R.string.team_name)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = SearchMatchFragment(query, leagueName)
            1 -> fragment = SearchTeamFragment(query, leagueName)
        }
        return fragment as Fragment

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int = tabTitles.size
}