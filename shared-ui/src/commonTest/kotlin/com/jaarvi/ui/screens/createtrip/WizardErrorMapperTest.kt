package com.jaarvi.ui.screens.createtrip

import kotlin.test.Test
import kotlin.test.assertTrue

class WizardErrorMapperTest {

    @Test
    fun connectTimeoutMessage_mapsToServerUnreachable() {
        val error = Exception(
            "Connect timeout has expired [url=http://192.168.1.1:30080/api/trips, connect_timeout=unknown ms]",
        )
        val message = error.toFriendlyMessage()
        assertTrue(message.contains("Can't reach the server", ignoreCase = true))
    }

    @Test
    fun connectTimeoutInCauseChain_mapsToServerUnreachable() {
        val cause = object : Exception("Connect timeout has expired") {}
        val error = Exception("Request failed", cause)
        val message = error.toFriendlyMessage()
        assertTrue(message.contains("Can't reach the server", ignoreCase = true))
    }

    @Test
    fun unknownHost_mapsToOffline() {
        val error = Exception("Unable to resolve host \"api.example.com\"")
        val message = error.toFriendlyMessage()
        assertTrue(message.contains("No internet connection", ignoreCase = true))
    }
}
