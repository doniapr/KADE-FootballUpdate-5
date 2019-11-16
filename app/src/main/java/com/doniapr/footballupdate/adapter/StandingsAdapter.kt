package com.doniapr.footballupdate.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.Standings
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint

class StandingsAdapter(
    private val standings: List<Standings>,
    private val listener: (Standings) -> Unit
) : RecyclerView.Adapter<StandingsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingsViewHolder {
        return StandingsViewHolder(
            StandingUI().createView(
                AnkoContext.Companion.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun getItemCount(): Int = standings.size

    override fun onBindViewHolder(holder: StandingsViewHolder, position: Int) {
        holder.bindItem(standings[position], listener)
    }
}

class StandingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val pos: TextView = view.find(R.id.txt_standing_pos)
    private val teamName: TextView = view.find(R.id.txt_team_name_standing)
    private val play: TextView = view.find(R.id.txt_standing_play)
    private val win: TextView = view.find(R.id.txt_standing_win)
    private val draw: TextView = view.find(R.id.txt_standing_draw)
    private val loss: TextView = view.find(R.id.txt_standing_loss)
    private val gd: TextView = view.find(R.id.txt_standing_gd)
    private val point: TextView = view.find(R.id.txt_standing_point)

    fun bindItem(standing: Standings, listener: (Standings) -> Unit) {
        itemView.setOnClickListener { listener(standing) }

        pos.text = (adapterPosition + 1).toString()
        teamName.text = standing.teamName
        play.text = standing.played
        win.text = standing.win
        draw.text = standing.draw
        loss.text = standing.loss
        gd.text = standing.goalDifference
        point.text = standing.total
    }
}

class StandingUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {
                lparams(width = matchParent, height = dip(50))
                background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    color = ColorStateList.valueOf(Color.parseColor("#ffffff"))
                    setStroke(2, Color.GRAY)
                }

                constraintLayout {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.layout_standing
                    padding = dip(8)

                    textView {
                        text = "1"
                        id = R.id.txt_standing_pos
                        textSize = 16f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        width = dip(30)
                        height = wrapContent
                        marginEnd = dip(16)
                        startToStart = R.id.layout_standing
                        topToTop = R.id.txt_team_name_standing
                        bottomToBottom = R.id.txt_team_name_standing
                    }

                    textView {
                        id = R.id.txt_team_name_standing
                        text = resources.getString(R.string.team_name)
                        textSize = 16f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        width = matchConstraint
                        height = wrapContent
                        marginStart = dip(8)
                        startToEnd = R.id.txt_standing_pos
                        topToTop = R.id.layout_standing
                        bottomToBottom = R.id.layout_standing
                    }

                    textView {
                        text = "0"
                        id = R.id.txt_standing_play
                    }.lparams {
                        width = dip(20)
                        height = wrapContent
                        marginEnd = dip(10)
                        endToStart = R.id.txt_standing_win
                        topToTop = R.id.txt_team_name_standing
                        bottomToBottom = R.id.txt_team_name_standing
                    }
                    textView {
                        id = R.id.txt_standing_win
                        text = "0"
                    }.lparams {
                        width = dip(20)
                        height = wrapContent
                        marginEnd = dip(10)
                        endToStart = R.id.txt_standing_draw
                        topToTop = R.id.txt_team_name_standing
                        bottomToBottom = R.id.txt_team_name_standing
                    }
                    textView {
                        id = R.id.txt_standing_draw
                        text = "0"
                    }.lparams {
                        width = dip(20)
                        height = wrapContent
                        marginEnd = dip(10)
                        endToStart = R.id.txt_standing_loss
                        topToTop = R.id.txt_team_name_standing
                        bottomToBottom = R.id.txt_team_name_standing
                    }
                    textView {
                        id = R.id.txt_standing_loss
                        text = "0"
                    }.lparams {
                        width = dip(20)
                        height = wrapContent
                        marginEnd = dip(10)
                        endToStart = R.id.txt_standing_gd
                        topToTop = R.id.txt_team_name_standing
                        bottomToBottom = R.id.txt_team_name_standing
                    }
                    textView {
                        id = R.id.txt_standing_gd
                        text = "0"
                    }.lparams {
                        width = dip(20)
                        height = wrapContent
                        marginEnd = dip(10)
                        endToStart = R.id.txt_standing_point
                        topToTop = R.id.txt_team_name_standing
                        bottomToBottom = R.id.txt_team_name_standing
                    }

                    textView {
                        id = R.id.txt_standing_point
                        text = "0"
                        textSize = 16f
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        marginEnd = dip(10)
                        endToEnd = R.id.layout_standing
                        topToTop = R.id.txt_team_name_standing
                        bottomToBottom = R.id.txt_team_name_standing
                    }
                }
            }
        }
    }
}