package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.*
import com.doniapr.footballupdate.view.DetailTeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class DetailTeamPresenterTest {
    @Mock
    private lateinit var view: DetailTeamView

    private lateinit var presenter: DetailTeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailTeamPresenter(view)
    }

    @Test
    fun getTeamInfo() {
        val teamId = "133604"
        var result: Response<TeamResponse>
        var responses: Team?

        GlobalScope.launch {
            result = MainApi().services.getTeamInfo(teamId)
            responses = result.body()?.teams?.get(0)

            presenter.getTeamInfo(teamId)

            Mockito.verify(view).showLoading(1)
            Mockito.verify(view).showTeamDetail(responses!!)
            Mockito.verify(view).hideLoading(1)
        }
    }

    @Test
    fun getLastMatch() {
        val teamId = "133604"
        var result: Response<TeamLastMatchResponse>
        var responses: List<Match>?

        GlobalScope.launch {
            result = MainApi().services.getTeamLastMatch(teamId)
            responses = result.body()?.matches

            presenter.getTeamInfo(teamId)

            Mockito.verify(view).showLoading(2)
            Mockito.verify(view).showLastMatch(responses!!)
            Mockito.verify(view).hideLoading(2)
        }
    }

    @Test
    fun getNextMatch() {
        val teamId = "133604"
        var result: Response<MatchResponse>
        var responses: List<Match>?

        GlobalScope.launch {
            result = MainApi().services.getTeamNextMatch(teamId)
            responses = result.body()?.matches

            presenter.getTeamInfo(teamId)

            Mockito.verify(view).showLoading(3)
            Mockito.verify(view).showNextMatch(responses!!)
            Mockito.verify(view).hideLoading(3)
        }
    }
}