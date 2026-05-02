package com.jaarvi.ui.linda.components.level0.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// Action icons
//   • Add         — plus / increment
//   • Close       — ✕ dismiss / remove
//   • Clear       — ✕ clear input (alias for Close)
//   • Search      — magnifying glass
//   • Check       — ✓ confirmation / selection
//   • Share       — share / export
//   • DragHandle  — three horizontal lines for reordering
// ─────────────────────────────────────────────────────────────

private fun parsePathData(s: String) = PathParser().parsePathString(s).toNodes()

// ── Add ───────────────────────────────────────────────────────

public val LindaIcons.Filled.Add: ImageVector
    get() {
        if (_add != null) return _add!!
        _add = ImageVector.Builder(
            name          = "LindaIcons.Filled.Add",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData("M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"),
                fill     = SolidColor(Color.Black),
            )
        }.build()
        return _add!!
    }
private var _add: ImageVector? = null

// ── Close ─────────────────────────────────────────────────────

public val LindaIcons.Filled.Close: ImageVector
    get() {
        if (_close != null) return _close!!
        _close = ImageVector.Builder(
            name          = "LindaIcons.Filled.Close",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59" +
                    " 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _close!!
    }
private var _close: ImageVector? = null

// ── Clear (same path as Close) ────────────────────────────────

public val LindaIcons.Filled.Clear: ImageVector
    get() {
        if (_clear != null) return _clear!!
        _clear = ImageVector.Builder(
            name          = "LindaIcons.Filled.Clear",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59" +
                    " 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _clear!!
    }
private var _clear: ImageVector? = null

// ── Search ────────────────────────────────────────────────────

public val LindaIcons.Filled.Search: ImageVector
    get() {
        if (_search != null) return _search!!
        _search = ImageVector.Builder(
            name          = "LindaIcons.Filled.Search",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5" +
                    " 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16" +
                    "c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5z" +
                    "m-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _search!!
    }
private var _search: ImageVector? = null

// ── Check ─────────────────────────────────────────────────────

public val LindaIcons.Filled.Check: ImageVector
    get() {
        if (_check != null) return _check!!
        _check = ImageVector.Builder(
            name          = "LindaIcons.Filled.Check",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData("M9 16.17L4.83 12l-1.42 1.41L9 19 21 7l-1.41-1.41z"),
                fill     = SolidColor(Color.Black),
            )
        }.build()
        return _check!!
    }
private var _check: ImageVector? = null

// ── Share ─────────────────────────────────────────────────────

public val LindaIcons.Filled.Share: ImageVector
    get() {
        if (_share != null) return _share!!
        _share = ImageVector.Builder(
            name          = "LindaIcons.Filled.Share",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M18 16.08c-.76 0-1.44.3-1.96.77L8.91 12.7c.05-.23.09-.46.09-.7" +
                    "s-.04-.47-.09-.7l7.05-4.11c.54.5 1.25.81 2.04.81 1.66 0 3-1.34 3-3" +
                    "s-1.34-3-3-3-3 1.34-3 3c0 .24.04.47.09.7L8.04 9.81C7.5 9.31 6.79 9 6 9" +
                    "c-1.66 0-3 1.34-3 3s1.34 3 3 3c.79 0 1.5-.31 2.04-.81l7.12 4.16" +
                    "c-.05.21-.08.43-.08.65 0 1.61 1.31 2.92 2.92 2.92 1.61 0 2.92-1.31" +
                    " 2.92-2.92s-1.31-2.92-2.92-2.92z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _share!!
    }
private var _share: ImageVector? = null

// ── Info ──────────────────────────────────────────────────────

public val LindaIcons.Filled.Info: ImageVector
    get() {
        if (_info != null) return _info!!
        _info = ImageVector.Builder(
            name          = "LindaIcons.Filled.Info",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2z" +
                    "m1 15h-2v-6h2v6zm0-8h-2V7h2v2z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _info!!
    }
private var _info: ImageVector? = null

// ── Warning ───────────────────────────────────────────────────

public val LindaIcons.Filled.Warning: ImageVector
    get() {
        if (_warning != null) return _warning!!
        _warning = ImageVector.Builder(
            name          = "LindaIcons.Filled.Warning",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData("M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z"),
                fill     = SolidColor(Color.Black),
            )
        }.build()
        return _warning!!
    }
private var _warning: ImageVector? = null

// ── DragHandle ────────────────────────────────────────────────

public val LindaIcons.Filled.DragHandle: ImageVector
    get() {
        if (_dragHandle != null) return _dragHandle!!
        _dragHandle = ImageVector.Builder(
            name          = "LindaIcons.Filled.DragHandle",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData("M20 9H4v2h16V9zM4 15h16v-2H4v2z"),
                fill     = SolidColor(Color.Black),
            )
        }.build()
        return _dragHandle!!
    }
private var _dragHandle: ImageVector? = null
