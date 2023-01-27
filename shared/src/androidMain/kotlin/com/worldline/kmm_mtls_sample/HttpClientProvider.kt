package com.worldline.kmm_mtls_sample

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import nl.altindag.ssl.SSLFactory
import java.io.InputStream
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

actual class HttpClientProvider(keystore: InputStream, keystorePassword: String) {

    private val trustManager: X509TrustManager

    private val sslContext: SSLContext

    init {
        val factory = SSLFactory.builder()
            .withIdentityMaterial(keystore, keystorePassword.toCharArray(), "PKCS12")
            .withDefaultTrustMaterial()
            .build()

        sslContext = factory.sslContext
        trustManager = factory.trustManagerFactory.get()
            .trustManagers?.first { it is X509TrustManager } as X509TrustManager
    }

    actual fun clientWithMtls(block: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
        engine {
            config {
                sslSocketFactory(
                    sslContext.socketFactory,
                    trustManager
                )
            }
        }
        block(this)
    }

    actual fun client(block: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
        block(this)
    }
}