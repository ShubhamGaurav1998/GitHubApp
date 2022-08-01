package com.example.githubapp.di

import com.example.githubapp.retrofit.GitHubService
import com.example.githubapp.utils.Constants
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceTest {

    private lateinit var service: GitHubService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)


    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }

    @Test
    fun getResults_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("apiResponse.json")
            val responseBody = service.getResults(
                Constants.API_PATH,
                Constants.API_SUBPATH1,
                Constants.API_SUBPATH2,
                Constants.API_SUBPATH3,
                Constants.QUERY_PARAM_CLOSED,
                1,
                Constants.PAGE_SIZE,
                Constants.GITHUB_AUTH_TOKEN
            ).execute().body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/repos/square/retrofit/pulls?state=closed&page=1&per_page=30")


        }
    }

    @Test
    fun getResults_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("apiResponse.json")
            val responseBody = service.getResults(
                Constants.API_PATH,
                Constants.API_SUBPATH1,
                Constants.API_SUBPATH2,
                Constants.API_SUBPATH3,
                Constants.QUERY_PARAM_CLOSED,
                1,
                Constants.PAGE_SIZE,
                Constants.GITHUB_AUTH_TOKEN
            ).execute().body()
            assertThat(responseBody?.size).isEqualTo(30)

        }
    }

    @Test
    fun getResults_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("apiResponse.json")
            val responseBody = service.getResults(
                Constants.API_PATH,
                Constants.API_SUBPATH1,
                Constants.API_SUBPATH2,
                Constants.API_SUBPATH3,
                Constants.QUERY_PARAM_CLOSED,
                1,
                Constants.PAGE_SIZE,
                Constants.GITHUB_AUTH_TOKEN
            ).execute().body()
            val closedPR = responseBody?.get(0)
            assertThat(closedPR?.title).isEqualTo("refact classes ")
            assertThat(closedPR?.created_at).isEqualTo("2022-07-25T11:27:16Z")
            assertThat(closedPR?.closed_at).isEqualTo("2022-07-25T11:29:04Z")
            assertThat(closedPR?.user?.login).isEqualTo("brendalmalves")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}
