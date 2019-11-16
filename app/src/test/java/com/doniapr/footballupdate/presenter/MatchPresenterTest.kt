package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.match.Match
import com.doniapr.footballupdate.model.match.MatchResponse
import com.doniapr.footballupdate.view.viewinterface.ListMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class MatchPresenterTest {
    @Mock
    private lateinit var view: ListMatchView

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view)
    }

    @Test
    fun getLastMatch() {
        val leagueId = 4328
        var result: Response<MatchResponse>
        var responses: List<Match>?

        GlobalScope.launch {
            result = MainApi().services.getLastMatch(leagueId.toString())
            responses = result.body()?.matches

            presenter.getLastMatch(leagueId)

            Mockito.verify(view).showLoading(true)
            Mockito.verify(view).showLastMatch(responses!!)
            Mockito.verify(view).hideLoading(true)
        }
    }

    @Test
    fun getNextMatch() {
        val leagueId = 4328
        var result: Response<MatchResponse>
        var responses: List<Match>?

        GlobalScope.launch {
            result = MainApi().services.getNextMatch(leagueId.toString())
            responses = result.body()?.matches

            presenter.getNextMatch(leagueId)

            Mockito.verify(view).showLoading(false)
            Mockito.verify(view).showNextMatch(responses!!)
            Mockito.verify(view).hideLoading(false)
        }
    }
}