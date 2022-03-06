package com.admiral.demo.features.home.gun.data.service

import com.admiral.network.RestServiceFactory
import com.admiral.network.RetrofitFactory

class GunServiceFactory(retrofitFactory: RetrofitFactory) : RestServiceFactory<GunService>(
    retrofitFactory,
    GunService::class.java
)