package com.worldline.kmm_mtls_sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob

actual open class PlatformViewModel {
    actual val job: Job = SupervisorJob()

    actual val vmScope: CoroutineScope = CoroutineScope(job)
}