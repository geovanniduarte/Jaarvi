package com.jaarvi.ui.screens.createtrip

/**
 * Maps any [Throwable] surfaced by a wizard commit action to a user-friendly,
 * non-technical error string that is safe to show directly in the UI.
 *
 * Ktor exceptions are not directly importable in `shared-ui` (Ktor is an
 * `implementation` dependency of `:shared`, not exposed transitively). Classification
 * is therefore done via class-name and message inspection on the full cause chain.
 */
internal fun Throwable.toFriendlyMessage(): String {
    val classNames = chainClassNames()
    val msg        = chainMessage()

    return when {

        this is IllegalArgumentException ->
            msg.ifBlank { "We couldn't validate your data. Please review your information and try again." }

        isServerUnreachable(classNames, msg) ->
            "Can't reach the server. Make sure the backend is running and the API URL is correct for your device."

        isOffline(classNames, msg) ->
            "No internet connection. Check your connection and try again."

        classNames.any { it.contains("ServerResponse", ignoreCase = true) } ||
            msg.contains("Server error", ignoreCase = true) ->
            "Something went wrong on our end. Please try again in a moment."

        classNames.any { it.contains("ClientRequest", ignoreCase = true) } &&
            (msg.contains("422", ignoreCase = true) || msg.contains("Unprocessable", ignoreCase = true)) ->
            "We couldn't validate your data. Please review your information and try again."

        classNames.any { it.contains("ClientRequest", ignoreCase = true) } ->
            "We couldn't process your request. Please review your data and try again."

        classNames.any {
            it.contains("Serialization", ignoreCase = true) ||
                it.contains("MissingField", ignoreCase = true) ||
                it.contains("Json", ignoreCase = true) ||
                it.contains("DateTime", ignoreCase = true)
        } ->
            "We couldn't read the server response. Check that the API is reachable and try again."

        else -> {
            val detail = msg.trim()
            when {
                detail.isNotEmpty() -> detail.take(200)
                else -> "An unexpected error occurred. Please try again."
            }
        }
    }
}

/** Backend up but device cannot open a TCP connection (wrong host/port, port-forward down, etc.). */
private fun isServerUnreachable(classNames: List<String>, msg: String): Boolean {
    if (classNames.any { it.contains("ConnectTimeout", ignoreCase = true) }) return true
    if (classNames.any { it.contains("ConnectException", ignoreCase = true) }) return true
    if (msg.contains("Connect timeout", ignoreCase = true)) return true
    if (msg.contains("Connection refused", ignoreCase = true)) return true
    if (msg.contains("Failed to connect", ignoreCase = true)) return true
    if (msg.contains("ECONNREFUSED", ignoreCase = true)) return true
    return false
}

/** Device has no route to the network or DNS cannot resolve a hostname. */
private fun isOffline(classNames: List<String>, msg: String): Boolean {
    if (classNames.any { it.contains("UnknownHost", ignoreCase = true) }) return true
    if (classNames.any { it.contains("NoRoute", ignoreCase = true) }) return true
    if (msg.contains("Unable to resolve", ignoreCase = true)) return true
    if (msg.contains("Network is unreachable", ignoreCase = true)) return true
    if (msg.contains("No address associated", ignoreCase = true)) return true
    return false
}

private fun Throwable.chainClassNames(): List<String> =
    generateSequence(this) { it.cause }
        .mapNotNull { it::class.simpleName }
        .toList()

private fun Throwable.chainMessage(): String {
    var current: Throwable? = this
    while (current != null) {
        val text = current.message?.trim().orEmpty()
        if (text.isNotEmpty()) return text
        current = current.cause
    }
    return message.orEmpty()
}
