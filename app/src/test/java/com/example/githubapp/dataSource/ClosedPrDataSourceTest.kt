package com.example.githubapp.dataSource

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import com.example.githubapp.models.GitHubApiResponse
import com.example.githubapp.models.GitHubApiResponseItem
import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.io.InputStream


@ExperimentalCoroutinesApi
class ClosedPrDataSourceTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var gitHubService: GitHubService
    lateinit var closedPrDataSource: ClosedPrDataSource
    companion object {
        val closedPrResponse : GitHubApiResponse = createJsonObject()

        private fun createJsonObject(): GitHubApiResponse {
            val inputStream: InputStream =
                javaClass.classLoader!!.getResourceAsStream("apiResponse.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val myJson = String(buffer, Charsets.UTF_8)

            val jsonObject = JSONObject(myJson)
            val gson = Gson()
            return gson.fromJson(jsonObject.toString(), GitHubApiResponse::class.java)
        }

    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        closedPrDataSource = ClosedPrDataSource(gitHubService)
    }

    @Test
    fun `reviews paging source load - failure - http error`() = runBlockingTest {
        val error = RuntimeException("404", Throwable())
        given(gitHubService.getResults(Constants.API_PATH,
            Constants.API_SUBPATH1,
            Constants.API_SUBPATH2,
            Constants.API_SUBPATH3,
            Constants.QUERY_PARAM_CLOSED,
            1,
            Constants.PAGE_SIZE,
            Constants.GITHUB_AUTH_TOKEN)).willThrow(error)
        val expectedResult = PagingSource.LoadResult.Error<Int, GitHubApiResponseItem>(error)
        assertEquals(
            expectedResult, closedPrDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }
    @Test
    fun `reviews paging source load - failure - received null`() = runBlockingTest {
        given(gitHubService.getResults(Constants.API_PATH,
            Constants.API_SUBPATH1,
            Constants.API_SUBPATH2,
            Constants.API_SUBPATH3,
            Constants.QUERY_PARAM_CLOSED,
            1,
            Constants.PAGE_SIZE,
            Constants.GITHUB_AUTH_TOKEN)).willReturn(null)
        val expectedResult = PagingSource.LoadResult.Error<Int, GitHubApiResponseItem>(NullPointerException())
        assertEquals(
            expectedResult.toString(), closedPrDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            ).toString()
        )
    }

    @Test
    fun `reviews paging source refresh - success`() = runBlockingTest {
        given(gitHubService.getResults(Constants.API_PATH,
            Constants.API_SUBPATH1,
            Constants.API_SUBPATH2,
            Constants.API_SUBPATH3,
            Constants.QUERY_PARAM_CLOSED,
            1,
            Constants.PAGE_SIZE,
            Constants.GITHUB_AUTH_TOKEN).body()).willReturn(closedPrResponse)
        val expectedResult = PagingSource.LoadResult.Page(
            data = closedPrResponse,
            prevKey = null,
            nextKey = 1
        )
        assertEquals(
            expectedResult, closedPrDataSource.load(
                PagingSource.LoadParams.Refresh(
                    key = 0,
                    loadSize = 1,
                    placeholdersEnabled = false
                )
            )
        )
    }

}