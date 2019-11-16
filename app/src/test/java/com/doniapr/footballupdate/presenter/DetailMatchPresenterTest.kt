package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.match.Match
import com.doniapr.footballupdate.model.match.MatchResponse
import com.doniapr.footballupdate.model.team.Team
import com.doniapr.footballupdate.model.team.TeamResponse
import com.doniapr.footballupdate.view.viewinterface.DetailMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class DetailMatchPresenterTest {
    @Mock
    private lateinit var view: DetailMatchView

    private lateinit var presenter: DetailMatchPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(view)
    }

    @Test
    fun testGetMatchDetail() {
        val eventId = "602246"
        var result: Response<MatchResponse>
        var responses: Match?

        GlobalScope.launch {
            result = MainApi().services.getMatchDetail(eventId)
            responses = result.body()?.matches?.get(0)

            presenter.getMatchDetail(eventId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchDetail(responses!!)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun getTeamInfo() {
        val teamId = "133602"
        var result: Response<TeamResponse>
        var responses: Team?

        GlobalScope.launch {
            result = MainApi().services.getTeamInfo(teamId)
            responses = result.body()?.teams?.get(0)

            presenter.getTeamInfo(teamId, true)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showTeam(responses!!, true)
            Mockito.verify(view).hideLoading()
        }
    }
}