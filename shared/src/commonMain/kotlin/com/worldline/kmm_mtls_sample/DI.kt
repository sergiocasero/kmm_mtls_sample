package com.worldline.kmm_mtls_sample

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun Module.platformModule()

val sharedModule = module {
    platformModule()
}

fun initKoin(appModule: Module): KoinApplication {
    val koinApplication = startKoin {
        modules(
            appModule,
            sharedModule
        )
    }

    return koinApplication
}