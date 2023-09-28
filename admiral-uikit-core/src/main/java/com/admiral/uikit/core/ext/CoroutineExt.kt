package com.admiral.uikit.core.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

val CoroutineScope.job: Job
    get() = requireNotNull(coroutineContext[Job])