package com.admiral.uikit.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

internal val CoroutineScope.job: Job
    get() = requireNotNull(coroutineContext[Job])