package com.doniapr.footballupdate.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.favorite.Favorite
import com.doniapr.footballupdate.utility.formatTo
import com.doniapr.footballupdate.utility.toDate
import com.doniapr.footballupdate.utility.toDateAndHour
import com.doniapr.footballupdate.utility.toHour
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

class FavoriteNextMatchAdapter(
    private val favorites: List<Favorite>,
    private val listener: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteNextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteNextViewHolder {
        return FavoriteNextViewHolder(
            FavoriteNextUI().createView(
                AnkoContext.Companion.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: FavoriteNextViewHolder, position: Int) {
        holder.bindItem(favorites[position], listener)
    }
}

class FavoriteNextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val txtRound: TextView = view.find(R.id.txt_match_week)
    private val homeTeam: TextView = view.find(R.id.txt_home_team)
    private val awayTeam: TextView = view.find(R.id.txt_away_team)
    private val txtMatchDate: TextView = view.find(R.id.txt_match_date)
    private val txtMatchTime: TextView = view.find(R.id.txt_match_time)
    private val imgHomeBadge: ImageView = view.find(R.id.img_match_home_team_badge)
    private val imgAwayBadge: ImageView = view.find(R.id.img_match_away_team_badge)

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {

        itemView.setOnClickListener { listener(favorite) }

        val round = "${favorite.leagueName} Round ${favorite.round}"
        homeTeam.text = favorite.homeTeamName
        awayTeam.text = favorite.awayTeamName
        txtRound.text = round
        Picasso.get().load(favorite.homeTeamBadge).into(imgHomeBadge)
        Picasso.get().load(favorite.awayTeamBadge).into(imgAwayBadge)

        if (!favorite.date.isNullOrEmpty() && !favorite.time.isNullOrEmpty()) {
            val utcDate = favorite.date.toString() + " " + favorite.time.toString()
            val wibDate = utcDate.toDateAndHour()
            txtMatchDate.text = wibDate.formatTo("dd MMMM yyyy")
            txtMatchTime.text = wibDate.formatTo("HH:mm:ss")
        } else if (!favorite.date.isNullOrEmpty() && favorite.time.isNullOrEmpty()) {
            val utcDate = favorite.date.toString()
            val wibDate = utcDate.toDate()
            txtMatchDate.text = wibDate.formatTo("dd MMMM yyyy")
            txtMatchTime.text = "-"
        } else if (favorite.date.isNullOrEmpty() && !favorite.time.isNullOrEmpty()) {
            val utcDate = favorite.time.toString()
            val wibDate = utcDate.toHour()
            txtMatchDate.text = "-"
            txtMatchTime.text = wibDate.formatTo("HH:mm:ss")
        } else {
            txtMatchDate.text = "-"
            txtMatchTime.text = "-"
        }

    }
}

class FavoriteNextUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(8)

                cardView {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER

                        textView {
                            id = R.id.txt_match_week
                            textSize = 12f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }

                        constraintLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(8)
                            id = R.id.cv_match_info

                            imageView {
                                id = R.id.img_match_home_team_badge
                            }.lparams {
                                width = dip(50)
                                height = dip(50)
                                topToTop = R.id.cv_match_info
                                bottomToBottom = R.id.cv_match_info
                                startToStart = R.id.cv_match_info
                            }

                            textView {
                                id = R.id.txt_home_team
                                textSize = 16f
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                topToTop = R.id.img_match_home_team_badge
                                bottomToBottom = R.id.img_match_home_team_badge
                                startToEnd = R.id.img_match_home_team_badge
                                endToStart = R.id.txt_versus
                            }

                            textView {
                                id = R.id.txt_versus
                                textSize = 14f
                                text = resources.getString(R.string.versus)
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams {
                                topToTop = R.id.cv_match_info
                                startToStart = R.id.cv_match_info
                                endToEnd = R.id.cv_match_info
                            }

                            textView {
                                id = R.id.txt_away_team
                                textSize = 16f
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams {
                                width = dip(0)
                                height = wrapContent
                                topToTop = R.id.img_match_away_team_badge
                                bottomToBottom = R.id.img_match_away_team_badge
                                startToEnd = R.id.txt_versus
                                endToStart = R.id.img_match_away_team_badge
                            }
                            imageView {
                                id = R.id.img_match_away_team_badge
                            }.lparams {
                                width = dip(50)
                                height = dip(50)
                                topToTop = R.id.cv_match_info
                                bottomToBottom = R.id.cv_match_info
                                endToEnd = R.id.cv_match_info
                            }
                        }

                        textView {
                            id = R.id.txt_match_date
                            textSize = 14f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                        textView {
                            id = R.id.txt_match_time
                            textSize = 12f
                            gravity = Gravity.CENTER_HORIZONTAL
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                    }
                }
            }
        }
    }

}