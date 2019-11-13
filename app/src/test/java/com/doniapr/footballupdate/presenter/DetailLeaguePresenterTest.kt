package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.LeagueDetail
import com.doniapr.footballupdate.model.LeagueDetailResponse
import com.doniapr.footballupdate.view.DetailLeagueView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class DetailLeaguePresenterTest {
    @Mock
    private lateinit var view: DetailLeagueView

    private lateinit var presenter: DetailLeaguePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLeaguePresenter(view)
    }

    @Test
    fun testGetLeagueDetail() {
        val leagueId = "4328"
        var result: Response<LeagueDetailResponse>
        var responses: List<LeagueDetail>?

        GlobalScope.launch {
            result = MainApi().services.getDetailLeague(leagueId)
            responses = result.body()?.league

            presenter.getLeagueDetail(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueDetail(responses)
            Mockito.verify(view).hideLoading()
        }
    }
}