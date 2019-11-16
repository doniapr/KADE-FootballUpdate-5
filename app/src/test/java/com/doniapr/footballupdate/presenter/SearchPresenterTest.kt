package com.doniapr.footballupdate.presenter

import com.doniapr.footballupdate.apiservice.MainApi
import com.doniapr.footballupdate.model.match.Match
import com.doniapr.footballupdate.model.search.SearchResponse
import com.doniapr.footballupdate.view.viewinterface.SearchResultView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class SearchPresenterTest {
    @Mock
    private lateinit var view: SearchResultView

    private lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(view)
    }

    @Test
    fun doSearch() {
        val query = "arsenal"
        var result: Response<SearchResponse>
        var responses: List<Match>?

        GlobalScope.launch {
            result = MainApi().services.searchMatch(query)
            responses = result.body()?.matches?.filter {
                it.sportName == "Soccer"
            }

            presenter.doSearch(query)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(responses!!)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun doSearchInLeague() {
        val query = "arsenal"
        val leagueName = "English Premiere League"
        var result: Response<SearchResponse>
        var responses: List<Match>?

        GlobalScope.launch {
            result = MainApi().services.searchMatch(query)
            responses = result.body()?.matches?.filter {
                it.sportName == "Soccer"
            }?.filter {
                it.leagueName == leagueName
            }

            presenter.doSearchInLeague(query, leagueName)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchList(responses!!)
            Mockito.verify(view).hideLoading()
        }
    }
}