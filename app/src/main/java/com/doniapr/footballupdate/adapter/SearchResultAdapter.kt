package com.doniapr.footballupdate.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.utility.formatTo
import com.doniapr.footballupdate.utility.toDate
import com.doniapr.footballupdate.utility.toDateAndHour
import com.doniapr.footballupdate.utility.toHour
import com.doniapr.footballupdate.view.DetailMatchActivity
import com.doniapr.footballupdate.view.DetailMatchActivity.Companion.EVENT_ID
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

class SearchResultAdapter(private val match: List<Match>) :
    RecyclerView.Adapter<ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            SearchUI().createView(
                AnkoContext.Companion.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun getItemCount(): Int = match.size

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bindItem(match[position])

        holder.itemView.setOnClickListener {
            holder.itemView.context.startActivity<DetailMatchActivity>(
               EVENT_ID to match[position].eventId
            )
        }
    }

}

class ResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val txtName: TextView = view.find(R.id.txt_search_result_name)
    private val txtHomeTeam: TextView = view.find(R.id.txt_home_team)
    private val txtAwayTeam: TextView = view.find(R.id.txt_away_team)
    private val txtHomeScore: TextView = view.find(R.id.txt_home_score)
    private val txtAwayScore: TextView = view.find(R.id.txt_away_score)
    private val txtMatchDate: TextView = view.find(R.id.txt_match_date)
    private val txtMatchTime: TextView = view.find(R.id.txt_match_time)

    fun bindItem(match: Match) {
        val leagueName = match.leagueName + " Round " + match.round
        txtName.text = leagueName
        txtHomeTeam.text = match.homeTeam
        txtAwayTeam.text = match.awayTeam
        if (match.homeScore == null && match.awayScore == null) {
            txtHomeScore.text = "-"
            txtAwayScore.text = "-"
        } else {
            txtHomeScore.text = match.homeScore.toString()
            txtAwayScore.text = match.awayScore.toString()
        }

        if (match.dateEvent != "" && match.time != "") {
            val utcDate = match.dateEvent.toString() + " " + match.time.toString()
            val wibDate = utcDate.toDateAndHour()
            txtMatchDate.text = wibDate.formatTo("dd MMMM yyyy")
            txtMatchTime.text = wibDate.formatTo("HH:mm:ss")
        } else if (match.dateEvent != "" && match.time == "") {
            val utcDate = match.dateEvent.toString()
            val wibDate = utcDate.toDate()
            txtMatchDate.text = wibDate.formatTo("dd MMMM yyyy")
            txtMatchTime.text = "-"
        } else if (match.dateEvent == "" && match.time != "") {
            val utcDate = match.time.toString()
            val wibDate = utcDate.toHour()
            txtMatchDate.text = "-"
            txtMatchTime.text = wibDate.formatTo("HH:mm:ss")
        } else {
            txtMatchDate.text = "-"
            txtMatchTime.text = "-"
        }
    }
}

class SearchUI : AnkoComponent<ViewGroup> {
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
                            id = R.id.txt_search_result_name
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

                            textView {
                                id = R.id.txt_home_team
                                textSize = 16f
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams {
                                topToTop = R.id.cv_match_info
                                startToStart = R.id.cv_match_info
                                endToStart = R.id.txt_home_score
                            }

                            textView {
                                textSize = 20f
                                id = R.id.txt_home_score
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
                                topToTop = R.id.cv_match_info
                                startToEnd = R.id.txt_away_score
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