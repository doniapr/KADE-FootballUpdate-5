package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.Standings
import com.doniapr.footballupdate.model.StandingsResponse
import com.doniapr.footballupdate.view.StandingsView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class StandingPresenterTest {

    @Mock
    private lateinit var view: StandingsView

    private lateinit var presenter: StandingPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = StandingPresenter(view)
    }

    @Test
    fun getStanding() {
        val leagueId = "4328"
        var result: Response<StandingsResponse>
        var responses: List<Standings>?

        GlobalScope.launch {
            result = MainApi().services.getStanding(leagueId, "1920")
            responses = result.body()?.standing

            presenter.getStanding(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showStandingList(responses!!)
            Mockito.verify(view).hideLoading()
        }

    }
}