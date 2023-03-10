package com.worldline.kmm_mtls_sample

import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSURLCredential

actual fun Module.platformModule() {
}

fun initKoinIos(urlCredential: NSURLCredential) {
    initKoin(
        module {
            single { HttpClientProvider(urlCredential) }
        }
    )
}