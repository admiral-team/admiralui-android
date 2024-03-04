package com.admiral.uikit.components.chat

enum class MessageStatus {
    NONE,
    LOAD,
    SENDING,
    SEND,
    READ;

    companion object {
        fun from(index: Int) = values()[index]
    }
}