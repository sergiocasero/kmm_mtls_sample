package com.worldline.kmm_mtls_sample

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect class HttpClientProvider {
    fun clientWithMtls(block: HttpClientConfig<*>.() -> Unit): HttpClient
    fun client(block: HttpClientConfig<*>.() -> Unit): HttpClient
}