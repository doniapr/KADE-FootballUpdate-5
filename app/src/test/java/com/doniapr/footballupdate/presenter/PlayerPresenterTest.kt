package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.Player
import com.doniapr.footballupdate.model.PlayerDetailResponse
import com.doniapr.footballupdate.model.PlayerResponse
import com.doniapr.footballupdate.view.PlayerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class PlayerPresenterTest {
    @Mock
    private lateinit var view: PlayerView

    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view)
    }

    @Test
    fun getTeamPlayers() {
        val teamId = "133604"
        var result: Response<PlayerResponse>
        var responses: List<Player>?

        GlobalScope.launch {
            result = MainApi().services.getTeamPlayer(teamId)
            responses = result.body()?.player

            presenter.getTeamPlayers(teamId.toInt())

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showPlayerList(responses!!)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getPlayersDetail() {
        val playerId = "34145937"
        var result: Response<PlayerDetailResponse>
        var responses: List<Player>?

        GlobalScope.launch {
            result = MainApi().services.getPlayerDetail(playerId.toInt())
            responses = result.body()?.player

            presenter.getPlayersDetail(playerId.toInt())

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showPlayerList(responses!!)
            Mockito.verify(view).hideLoading()
        }
    }
}