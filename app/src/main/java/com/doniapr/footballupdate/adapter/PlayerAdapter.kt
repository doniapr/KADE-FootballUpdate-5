package com.doniapr.footballupdate.adapter

import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.constraint.layout.matchConstraint

class PlayerAdapter(
    private val players: List<Player>,
    private val listener: (Player) -> Unit
) : RecyclerView.Adapter<PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(
            PlayerUI().createView(
                AnkoContext.Companion.create(
                    parent.context,
                    parent
                )
            )
        )
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val playerName: TextView = view.find(R.id.txt_team_player_name)
    private val playerImage: ImageView = view.find(R.id.img_team_player)
    private val playerPosition: TextView = view.find(R.id.txt_player_position)

    fun bindItem(player: Player, listener: (Player) -> Unit) {
        itemView.setOnClickListener { listener(player) }

        playerName.text = player.namePlayer
        playerPosition.text = player.position
        if (!player.image.isNullOrEmpty()) {
            Picasso.get().load(player.image).into(playerImage)
        } else if (!player.imageThumb.isNullOrEmpty()) {
            Picasso.get().load(player.imageThumb).into(playerImage)
        }
    }
}

class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            constraintLayout {
                lparams(width = matchParent, height = wrapContent)
                id = R.id.layout_list_player
                padding = dip(16)

                imageView {
                    id = R.id.img_team_player
                    setImageResource(R.drawable.player)
                }.lparams {
                    width = dip(50)
                    height = dip(50)
                    startToStart = R.id.layout_list_player
                    topToTop = R.id.layout_list_player
                    bottomToBottom = R.id.layout_list_player
                }

                textView {
                    id = R.id.txt_team_player_name
                    textSize = 16f
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams {
                    width = matchConstraint
                    height = wrapContent
                    marginStart = dip(16)
                    marginEnd = dip(8)
                    startToEnd = R.id.img_team_player
                    topToTop = R.id.img_team_player
                    bottomToBottom = R.id.img_team_player
                }

                textView {
                    id = R.id.txt_position
                    text = context.getString(R.string.position)
                    textSize = 14f
                }.lparams {
                    width = wrapContent
                    height = wrapContent
                    marginStart = dip(16)
                    startToEnd = R.id.img_team_player
                    topToBottom = R.id.txt_team_player_name
                    bottomToBottom = R.id.img_team_player
                }

                textView {
                    id = R.id.txt_player_position
                    textSize = 14f
                }.lparams {
                    width = matchConstraint
                    height = wrapContent
                    marginStart = dip(8)
                    startToEnd = R.id.txt_position
                    topToTop = R.id.txt_position
                    bottomToBottom = R.id.txt_position
                }
            }
        }
    }
}