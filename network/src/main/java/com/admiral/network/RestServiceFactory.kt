package com.admiral.network

open class RestServiceFactory<T : Any>(
    retrofitFactory: RetrofitFactory,
    clazz: Class<T>
) {
    inner class ServiceHolder {
        lateinit var service: T
    }

    val serviceHolder = ServiceHolder()

    init {
        serviceHolder.service = retrofitFactory.retrofit.create(clazz)
    }
}