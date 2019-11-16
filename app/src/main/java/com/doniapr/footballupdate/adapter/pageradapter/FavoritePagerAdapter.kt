package com.doniapr.footballupdate.adapter.pageradapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.view.ui.favorite.FavoriteLastMatchFragment
import com.doniapr.footballupdate.view.ui.favorite.FavoriteNextMatchFragment
import com.doniapr.footballupdate.view.ui.favorite.FavoriteTeamFragment

class FavoritePagerAdapter(
    private val context: Context,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val tabTitles =
        intArrayOf(R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FavoriteLastMatchFragment()
            1 -> fragment = FavoriteNextMatchFragment()
            2 -> fragment = FavoriteTeamFragment()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(tabTitles[position])
    }

    override fun getCount(): Int = tabTitles.size

}