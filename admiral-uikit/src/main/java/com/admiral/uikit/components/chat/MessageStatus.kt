package com.admiral.uikit.components.chat

enum class MessageStatus {
    NONE,

    @Deprecated("Use LOAD instead.")
    LOADING,
    LOAD,
    SENDING,
    SEND,
    READ,

    @Deprecated("We don't show error status at the message.")
    ERROR,

    @Deprecated("Use SEND instead.")
    DONE;

    companion object {
        fun from(index: Int) = values()[index]
    }
}