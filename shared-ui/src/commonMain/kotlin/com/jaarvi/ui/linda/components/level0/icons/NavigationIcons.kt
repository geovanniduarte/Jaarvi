package com.jaarvi.ui.linda.components.level0.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// Navigation icons
//   • Home        — house silhouette
//   • Trips       — location pin (maps / destinations)
//   • Guide       — list / itinerary
//   • Profile     — person silhouette
//   • ArrowBack   — left-pointing chevron arrow
//   • MoreVert    — three vertical dots (contextual actions)
// ─────────────────────────────────────────────────────────────

private fun parsePathData(s: String) = PathParser().parsePathString(s).toNodes()

// ── Home ──────────────────────────────────────────────────────

public val LindaIcons.Filled.Home: ImageVector
    get() {
        if (_home != null) return _home!!
        _home = ImageVector.Builder(
            name          = "LindaIcons.Filled.Home",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData("M10 20v-6h4v6h5v-8h3L12 3 2 12h3v8z"),
                fill     = SolidColor(Color.Black),
            )
        }.build()
        return _home!!
    }
private var _home: ImageVector? = null

// ── Trips (LocationOn) ────────────────────────────────────────

public val LindaIcons.Filled.Trips: ImageVector
    get() {
        if (_trips != null) return _trips!!
        _trips = ImageVector.Builder(
            name          = "LindaIcons.Filled.Trips",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13" +
                    "c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5" +
                    "s1.12-2.5 2.5-2.5 2.5 1.12 2.5 2.5-1.12 2.5-2.5 2.5z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _trips!!
    }
private var _trips: ImageVector? = null

// ── Guide (List) ──────────────────────────────────────────────

public val LindaIcons.Filled.Guide: ImageVector
    get() {
        if (_guide != null) return _guide!!
        _guide = ImageVector.Builder(
            name          = "LindaIcons.Filled.Guide",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M3 13h2v-2H3v2zm0 4h2v-2H3v2zm0-8h2V7H3v2zm4 4h14v-2H7v2z" +
                    "m0 4h14v-2H7v2zM7 7v2h14V7H7z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _guide!!
    }
private var _guide: ImageVector? = null

// ── Profile (Person) ──────────────────────────────────────────

public val LindaIcons.Filled.Profile: ImageVector
    get() {
        if (_profile != null) return _profile!!
        _profile = ImageVector.Builder(
            name          = "LindaIcons.Filled.Profile",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4z" +
                    "m0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _profile!!
    }
private var _profile: ImageVector? = null

// ── ArrowBack ─────────────────────────────────────────────────

public val LindaIcons.Filled.ArrowBack: ImageVector
    get() {
        if (_arrowBack != null) return _arrowBack!!
        _arrowBack = ImageVector.Builder(
            name          = "LindaIcons.Filled.ArrowBack",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M20 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H20v-2z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _arrowBack!!
    }
private var _arrowBack: ImageVector? = null

// ── MoreVert ──────────────────────────────────────────────────

public val LindaIcons.Filled.MoreVert: ImageVector
    get() {
        if (_moreVert != null) return _moreVert!!
        _moreVert = ImageVector.Builder(
            name          = "LindaIcons.Filled.MoreVert",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M12 8c1.1 0 2-.9 2-2s-.9-2-2-2-2 .9-2 2 .9 2 2 2z" +
                    "m0 2c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z" +
                    "m0 6c-1.1 0-2 .9-2 2s.9 2 2 2 2-.9 2-2-.9-2-2-2z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _moreVert!!
    }
private var _moreVert: ImageVector? = null
