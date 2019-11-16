package com.doniapr.footballupdate.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class TeamAdapter(
    private val teams: List<Team>,
    private val listener: (Team) -> Unit
) : RecyclerView.Adapter<TeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            TeamUI().createView(
                AnkoContext.Companion.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }

}

class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val teamName: TextView = view.find(R.id.txt_team_name)
    private val teamBadge: ImageView = view.find(R.id.img_team_badge)

    fun bindItem(team: Team, listener: (Team) -> Unit) {
        itemView.setOnClickListener { listener(team) }

        teamName.text = team.teamName
        Picasso.get().load(team.teamBadge).into(teamBadge)

    }
}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(8)

                cardView {
                    lparams(width = matchParent, height = wrapContent)

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER_VERTICAL
                        padding = dip(8)

                        imageView {
                            id = R.id.img_team_badge
                            setImageResource(R.drawable.team_badge)
                        }.lparams {
                            width = dip(50)
                            height = dip(50)
                            margin = dip(8)
                        }

                        textView {
                            id = R.id.txt_team_name
                            textSize = 16f
                        }.lparams {
                            margin = dip(8)
                        }
                    }
                }
            }
        }
    }
}