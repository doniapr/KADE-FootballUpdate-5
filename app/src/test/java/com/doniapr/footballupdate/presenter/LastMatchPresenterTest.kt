package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.view.LastMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class LastMatchPresenterTest {
    @Mock
    private lateinit var view: LastMatchView

    private lateinit var presenter: LastMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LastMatchPresenter(view)
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

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(responses!!)
            Mockito.verify(view).hideLoading()
        }
    }
}