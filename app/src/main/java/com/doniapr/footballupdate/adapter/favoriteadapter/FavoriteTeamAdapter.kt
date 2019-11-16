package com.doniapr.footballupdate.adapter.favoriteadapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.adapter.leaguedetailadapter.TeamUI
import com.doniapr.footballupdate.model.favorite.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class FavoriteTeamAdapter(
    private val teams: List<FavoriteTeam>,
    private val listener: (FavoriteTeam) -> Unit
) : RecyclerView.Adapter<FavoriteTeamViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamViewHolder {
        return FavoriteTeamViewHolder(
            TeamUI().createView(
                AnkoContext.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bindItem(teams[position], listener)
    }
}

class FavoriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val teamName: TextView = view.find(R.id.txt_team_name)
    private val teamBadge: ImageView = view.find(R.id.img_team_badge)

    fun bindItem(team: FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
        itemView.setOnClickListener { listener(team) }

        teamName.text = team.teamName
        Picasso.get().load(team.teamBadge).into(teamBadge)
    }
}