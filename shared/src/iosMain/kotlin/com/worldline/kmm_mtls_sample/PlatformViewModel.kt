package com.worldline.kmm_mtls_sample

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow

actual open class PlatformViewModel {

    actual val job: Job = SupervisorJob()

    actual val vmScope: CoroutineScope = MainScope()

    fun <T : Any> Flow<T>.adapter() =
        FlowAdapter(vmScope, this)
}
