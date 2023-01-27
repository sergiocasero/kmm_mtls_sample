package com.worldline.kmm_mtls_sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

expect open class PlatformViewModel() {
    val job: Job
    val vmScope: CoroutineScope
}