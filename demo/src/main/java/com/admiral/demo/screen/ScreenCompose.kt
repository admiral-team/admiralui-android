@file:Suppress("MatchingDeclarationName")

package com.admiral.demo.screen

import com.admiral.demo.compose.home.ComposeHomeFragment
import kotlinx.parcelize.Parcelize

@Parcelize
class HomeScreenCompose : Screen() {
    override fun getFragment() = ComposeHomeFragment()
}