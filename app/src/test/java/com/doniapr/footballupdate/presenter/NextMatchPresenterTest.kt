package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.Match
import com.doniapr.footballupdate.model.MatchResponse
import com.doniapr.footballupdate.view.NextMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class NextMatchPresenterTest {
    @Mock
    private lateinit var view: NextMatchView

    private lateinit var presenter: NextMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view)
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

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(responses!!)
            Mockito.verify(view).hideLoading()
        }
    }
}