package com.worldline.kmm_mtls_sample.android

import android.app.Application
import com.worldline.kmm_mtls_sample.initKoin
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // only because of tests
        val inputStream = assets.open("badssl.p12")

        initKoin(
            appModule = module {
                single { inputStream }
                single { "badssl.com" }
            }
        )
    }
}
