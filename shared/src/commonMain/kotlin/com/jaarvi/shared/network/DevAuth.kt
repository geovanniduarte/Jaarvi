package com.jaarvi.shared.network

/**
 * Development auth token for the backend JWT stub ([authenticateJWT]).
 *
 * The stub accepts any non-empty Bearer value and assigns a fixed test user.
 * Replace with real token storage when MVP auth is implemented.
 */
object DevAuth {
    const val BEARER_TOKEN: String = "dev-local"
}
