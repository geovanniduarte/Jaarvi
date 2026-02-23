package com.jaarvi.ui.linda.theme

// ─────────────────────────────────────────────────────────────
// Typography.kt — legacy compatibility shim.
//
// The full typography system has been migrated to LindaTypographyScheme.
// PlusJakartaSans FontFamily is now defined in LindaTypographyScheme.kt.
//
// Components should read text styles via LindaTheme.typography.*
// instead of the deprecated TravelTypography object below.
// ─────────────────────────────────────────────────────────────

/**
 * @deprecated Use LindaTheme.typography inside @Composable functions.
 * This alias is kept only for non-composable helpers that reference
 * type styles at top-level scope; remove once all usages are migrated.
 */
@Deprecated(
    message = "Use LindaTheme.typography inside @Composable functions.",
    replaceWith = ReplaceWith("LindaTheme.typography", "com.jaarvi.ui.linda.theme.LindaTheme"),
)
val TravelTypography get() = LindaDefaultTypographyScheme
