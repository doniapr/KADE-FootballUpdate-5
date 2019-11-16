package com.doniapr.footballupdate.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.doniapr.footballupdate.R
import com.doniapr.footballupdate.model.player.Player
import com.doniapr.footballupdate.presenter.PlayerPresenter
import com.doniapr.footballupdate.utility.formatTo
import com.doniapr.footballupdate.utility.invisible
import com.doniapr.footballupdate.utility.toDate
import com.doniapr.footballupdate.utility.visible
import com.doniapr.footballupdate.view.viewinterface.PlayerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player.*
import org.jetbrains.anko.support.v4.onRefresh

class PlayerActivity : AppCompatActivity(),
    PlayerView {
    private lateinit var presenter: PlayerPresenter

    companion object {
        const val PLAYER_ID = "player_id"
        const val PLAYER_NAME = "player_name"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        setSupportActionBar(toolbar_detail_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val playerId = intent.getIntExtra(PLAYER_ID, 0)
        val playerName = intent.getStringExtra(PLAYER_NAME)

        supportActionBar?.title = playerName
        presenter = PlayerPresenter(this)
        presenter.getPlayersDetail(playerId)

        swipe_refresh_detail_player.onRefresh {
            presenter.getPlayersDetail(playerId)
        }

    }

    override fun showLoading() {
        progress_bar_detail_player.visible()
    }

    override fun hideLoading() {
        progress_bar_detail_player.invisible()
    }

    override fun onFailed(type: Int) {
        runOnUiThread {
            val message: String = when (type) {
                1 -> getString(R.string.no_data)
                2 -> getString(R.string.no_internet)
                3 -> getString(R.string.failed_load_team_badge)
                else -> ""
            }

            hideLoading()
            swipe_refresh_detail_player.isRefreshing = false
            Toast.makeText(this@PlayerActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showPlayerList(data: List<Player>) {
        runOnUiThread {
            hideLoading()
            if (!data[0].image.isNullOrEmpty()) {
                Picasso.get().load(data[0].image).into(img_detail_player)
            } else if (!data[0].imageThumb.isNullOrEmpty()) {
                Picasso.get().load(data[0].imageThumb).into(img_detail_player)
            }

            txt_player_number.text = data[0].number
            txt_player_name.text = data[0].namePlayer
            txt_player_team.text = data[0].team
            txt_detail_player_position.text = data[0].position

            if (!data[0].desc.isNullOrEmpty()) {
                txt_player_name_desc.text = data[0].namePlayer
                txt_player_desc.text = data[0].desc
            } else {
                cv_player_desc.invisible()
            }

            txt_player_nationality.text = data[0].nationality
//            txt_player_birth.text = data[0].dateBorn
//            txt_player_signed.text = data[0].dateSigned
            txt_player_height.text = data[0].height
            txt_player_weight.text = data[0].weight

            if (!data[0].dateBorn.isNullOrEmpty()) {
                val date = data[0].dateBorn.toString().toDate()
                txt_player_birth.text = date.formatTo("dd MMMM yyyy")
            }
            if (!data[0].dateSigned.isNullOrEmpty()) {
                val date = data[0].dateSigned.toString().toDate()
                txt_player_signed.text = date.formatTo("dd MMMM yyyy")
            }
        }
    }
}
