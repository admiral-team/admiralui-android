package com.admiral.demo.features.home.gun.data

import com.admiral.network.IApiConfig

object GunApiConfig : IApiConfig {
    override val baseUrl: String
        get() = "http://10.0.2.2:3000/"
}