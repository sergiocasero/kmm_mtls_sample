package com.worldline.kmm_mtls_sample

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.encodedPath
import io.ktor.utils.io.core.use
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class SampleViewModel(initialState: SampleViewState) : RootViewModel<SampleViewState, SampleActions>(initialState) {

    private val httpClientProvider: HttpClientProvider by inject()

    fun onAction(action: SampleActions) {
        when (action) {
            SampleActions.OnLoadWithMtls -> executeMtlsRequest()
            SampleActions.OnLoadWithoutMtls -> executeRequest()
        }
    }

    private fun executeRequest() {
        testWithClient(
            httpClientProvider.client {
                defaultRequest { url("https://client.badssl.com") }
            }
        )
    }

    private fun executeMtlsRequest() {
        testWithClient(
            httpClientProvider.clientWithMtls {
                defaultRequest { url("https://client.badssl.com") }
            }
        )
    }

    private fun testWithClient(client: HttpClient) {
        // obviously, request calls shouldn't be done in the view model
        vmScope.launch {
            _uiState.value = SampleViewState.Loading
            client.use {
                val result = it.get {
                    url.encodedPath = "/"
                }
                _uiState.value = if (result.status == HttpStatusCode.OK) {
                    SampleViewState.Success(result.body())
                } else {
                    SampleViewState.Error(result.body())
                }
            }
        }
    }
}

sealed class SampleViewState : ViewState() {
    object Idle : SampleViewState()
    object Loading : SampleViewState()
    data class Success(val data: String) : SampleViewState()
    data class Error(val error: String) : SampleViewState()
}

sealed class SampleActions {
    object OnLoadWithMtls : SampleActions()
    object OnLoadWithoutMtls : SampleActions()
}