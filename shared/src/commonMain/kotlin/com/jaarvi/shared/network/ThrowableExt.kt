package com.jaarvi.shared.network

/**
 * Best-effort message from this throwable or its cause chain (for UI / logging).
 */
fun Throwable.bestMessage(): String {
    var current: Throwable? = this
    while (current != null) {
        val text = current.message?.trim().orEmpty()
        if (text.isNotEmpty()) return text.take(240)
        current = current.cause
    }
    val name = generateSequence(this) { it.cause }.last()::class.simpleName
    return name?.takeIf { it.isNotEmpty() } ?: "Unknown error"
}
