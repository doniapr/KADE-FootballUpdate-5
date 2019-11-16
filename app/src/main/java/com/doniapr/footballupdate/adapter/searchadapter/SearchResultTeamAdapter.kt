package com.doniapr.footballupdate.adapter.searchadapter

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.team.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint

class SearchResultTeamAdapter(
    private val teams: List<Team>,
    private val listener: (Team) -> Unit
) : RecyclerView.Adapter<ResultTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultTeamViewHolder {
        return ResultTeamViewHolder(
            SearchTeamUI().createView(
                AnkoContext.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ResultTeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }
}

class ResultTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val teamName: TextView = view.find(R.id.txt_team_name)
    private val teamLeague: TextView = view.find(R.id.txt_team_league)
    private val teamBadge: ImageView = view.find(R.id.img_team_badge)

    fun bindItem(team: Team, listener: (Team) -> Unit) {
        itemView.setOnClickListener { listener(team) }

        teamName.text = team.teamName
        teamLeague.text = team.teamLeague
        if (!team.teamBadge.isNullOrEmpty()) {
            Picasso.get().load(team.teamBadge).into(teamBadge)
        }

    }
}

class SearchTeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(8)

                cardView {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)
                    elevation = 16f
                    backgroundColor = android.R.color.darker_gray

                    constraintLayout {
                        lparams(width = matchParent, height = wrapContent)
                        id = R.id.cv_search_team

                        imageView {
                            id = R.id.img_team_badge
                            setImageResource(R.drawable.team_badge)
                        }.lparams {
                            width = dip(50)
                            height = dip(50)
                            margin = dip(16)
                            startToStart = R.id.cv_search_team
                            topToTop = R.id.cv_search_team
                            bottomToBottom = R.id.cv_search_team
                        }

                        textView {
                            id = R.id.txt_team_name
                            textSize = 16f
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams {
                            width = matchConstraint
                            height = wrapContent
                            marginStart = dip(16)
                            startToEnd = R.id.img_team_badge
                            topToTop = R.id.img_team_badge
                        }

                        textView {
                            id = R.id.txt_team_league
                            textSize = 14f
                        }.lparams {
                            width = matchConstraint
                            height = wrapContent
                            startToStart = R.id.txt_team_name
                            topToBottom = R.id.txt_team_name
                            bottomToBottom = R.id.img_team_badge
                        }
                    }
                }
            }
        }
    }
}