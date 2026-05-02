package com.jaarvi.ui.screens.createtrip

/**
 * Maps any [Throwable] surfaced by a wizard commit action to a user-friendly,
 * non-technical error string that is safe to show directly in the UI.
 *
 * Ktor exceptions are not directly importable in `shared-ui` (Ktor is an
 * `implementation` dependency of `:shared`, not exposed transitively). Classification
 * is therefore done via class-name and message inspection, which is reliable on both
 * Android and iOS targets.
 *
 * Classification:
 * - Timeout / connectivity → "No internet connection…"
 * - HTTP 5xx (ServerResponseException) → "Something went wrong on our end…"
 * - HTTP 422 (ClientRequestException + Unprocessable) → "We couldn't validate…"
 * - HTTP 4xx (ClientRequestException) → "We couldn't process your request…"
 * - Unknown → "An unexpected error occurred…"
 */
internal fun Throwable.toFriendlyMessage(): String {
    val className = this::class.simpleName.orEmpty()
    val msg       = message.orEmpty()

    return when {

        // ── Connectivity / timeout ────────────────────────────────────────────
        className.contains("Timeout",         ignoreCase = true) ||
        className.contains("ConnectException", ignoreCase = true) ||
        className.contains("Connect",         ignoreCase = true) ||
        className.contains("Socket",          ignoreCase = true) ||
        className.contains("NoRoute",         ignoreCase = true) ||
        className.contains("UnknownHost",     ignoreCase = true) ||
        msg.contains("timeout",              ignoreCase = true) ||
        msg.contains("Unable to resolve",    ignoreCase = true) ||
        msg.contains("Network is unreachable", ignoreCase = true) ||
        msg.contains("No address associated", ignoreCase = true) ->
            "No internet connection. Check your connection and try again."

        // ── Server errors — Ktor ServerResponseException covers 5xx ──────────
        className.contains("ServerResponse",  ignoreCase = true) ||
        msg.contains("Server error",          ignoreCase = true) ->
            "Something went wrong on our end. Please try again in a moment."

        // ── Validation error — HTTP 422 ───────────────────────────────────────
        className.contains("ClientRequest",   ignoreCase = true) &&
        (msg.contains("422", ignoreCase = true) ||
         msg.contains("Unprocessable", ignoreCase = true)) ->
            "We couldn't validate your data. Please review your information and try again."

        // ── Other client errors — HTTP 4xx ───────────────────────────────────
        className.contains("ClientRequest",   ignoreCase = true) ->
            "We couldn't process your request. Please review your data and try again."

        // ── Fallback ──────────────────────────────────────────────────────────
        else ->
            "An unexpected error occurred. Please try again."
    }
}
