package com.doniapr.footballupdate.adapter.favoriteadapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.favorite.FavoriteMatch
import com.doniapr.footballupdate.utility.formatTo
import com.doniapr.footballupdate.utility.toDate
import com.doniapr.footballupdate.utility.toDateAndHour
import com.doniapr.footballupdate.utility.toHour
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

class FavoriteLastMatchAdapter(
    private val favoriteMatches: List<FavoriteMatch>,
    private val listener: (FavoriteMatch) -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            FavoriteLastUI().createView(
                AnkoContext.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun getItemCount(): Int = favoriteMatches.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favoriteMatches[position], listener)
    }
}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val txtRound: TextView = view.find(R.id.txt_match_week)
    private val homeTeam: TextView = view.find(R.id.txt_home_team)
    private val awayTeam: TextView = view.find(R.id.txt_away_team)
    private val homeScore: TextView = view.find(R.id.txt_home_score)
    private val awayScore: TextView = view.find(R.id.txt_away_score)
    private val txtMatchDate: TextView = view.find(R.id.txt_match_date)
    private val txtMatchTime: TextView = view.find(R.id.txt_match_time)
    private val imgHomeBadge: ImageView = view.find(R.id.img_match_home_team_badge)
    private val imgAwayBadge: ImageView = view.find(R.id.img_match_away_team_badge)

    fun bindItem(favoriteMatch: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {

        itemView.setOnClickListener { listener(favoriteMatch) }

        val round = "${favoriteMatch.leagueName} Round ${favoriteMatch.round}"
        homeTeam.text = favoriteMatch.homeTeamName
        awayTeam.text = favoriteMatch.awayTeamName
        Picasso.get().load(favoriteMatch.homeTeamBadge).into(imgHomeBadge)
        Picasso.get().load(favoriteMatch.awayTeamBadge).into(imgAwayBadge)

        txtRound.text = round
        if (favoriteMatch.homeTeamScore != null) {
            homeScore.text = favoriteMatch.homeTeamScore
        }
        if (favoriteMatch.awayTeamScore != null) {
            awayScore.text = favoriteMatch.awayTeamScore
        }

        if (!favoriteMatch.date.isNullOrEmpty() && !favoriteMatch.time.isNullOrEmpty()) {
            val utcDate = favoriteMatch.date.toString() + " " + favoriteMatch.time.toString()
            val wibDate = utcDate.toDateAndHour()
            txtMatchDate.text = wibDate.formatTo("dd MMMM yyyy")
            txtMatchTime.text = wibDate.formatTo("HH:mm:ss")
        } else if (!favoriteMatch.date.isNullOrEmpty() && favoriteMatch.time.isNullOrEmpty()) {
            val utcDate = favoriteMatch.date.toString()
            val wibDate = utcDate.toDate()
            txtMatchDate.text = wibDate.formatTo("dd MMMM yyyy")
            txtMatchTime.text = "-"
        } else if (favoriteMatch.date.isNullOrEmpty() && !favoriteMatch.time.isNullOrEmpty()) {
            val utcDate = favoriteMatch.time.toString()
            val wibDate = utcDate.toHour()
            txtMatchDate.text = "-"
            txtMatchTime.text = wibDate.formatTo("HH:mm:ss")
        } else {
            txtMatchDate.text = "-"
            txtMatchTime.text = "-"
        }

    }
}

class FavoriteLastUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(8)

                cardView {
                    lparams(width = matchParent, height = wrapContent)
                    background = GradientDrawable().apply {
                        shape = GradientDrawable.RECTANGLE
                        color = ColorStateList.valueOf(Color.parseColor("#eceff1"))
                        cornerRadius = 20f
                        setStroke(2, Color.BLACK)
                    }

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER
                        padding = dip(8)

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
                                endToStart = R.id.txt_home_score
                            }

                            textView {
                                textSize = 20f
                                id = R.id.txt_home_score
                                text = resources.getString(R.string._0)
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams {
                                topToTop = R.id.cv_match_info
                                endToStart = R.id.txt_versus
                                marginEnd = dip(8)
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
                                textSize = 20f
                                id = R.id.txt_away_score
                                text = resources.getString(R.string._0)
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams {
                                topToTop = R.id.cv_match_info
                                startToEnd = R.id.txt_versus
                                marginStart = dip(8)
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
                                startToEnd = R.id.txt_away_score
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