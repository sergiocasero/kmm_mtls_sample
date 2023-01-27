package com.worldline.kmm_mtls_sample

import org.koin.core.module.Module

actual fun Module.platformModule() {
    single { HttpClientProvider(get(), get()) }
}