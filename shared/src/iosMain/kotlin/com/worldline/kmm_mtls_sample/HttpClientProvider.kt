package com.worldline.kmm_mtls_sample

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.ChallengeHandler
import io.ktor.client.engine.darwin.Darwin
import platform.Foundation.NSURLAuthenticationChallenge
import platform.Foundation.NSURLCredential
import platform.Foundation.NSURLSession
import platform.Foundation.NSURLSessionAuthChallengeDisposition
import platform.Foundation.NSURLSessionAuthChallengeUseCredential
import platform.Foundation.NSURLSessionTask

actual class HttpClientProvider(private val urlCredential: NSURLCredential) {
    actual fun clientWithMtls(block: HttpClientConfig<*>.() -> Unit) = HttpClient(Darwin) {
        engine {
            handleChallenge(GemmaChallengerHandler(urlCredential))
        }
        block(this)
    }

    actual fun client(block: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Darwin) {
        block(this)
    }
}

class GemmaChallengerHandler(private val urlCredential: NSURLCredential) : ChallengeHandler {

    override fun invoke(
        session: NSURLSession,
        task: NSURLSessionTask,
        challenge: NSURLAuthenticationChallenge,
        completionHandler: (NSURLSessionAuthChallengeDisposition, NSURLCredential?) -> Unit
    ) {
        completionHandler(NSURLSessionAuthChallengeUseCredential, urlCredential)
    }

}