package com.jaarvi.ui.linda.components.level0.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────
// Content / thematic icons
//   • WbSunny    — sun with rays  (path from Figma, 43.5×43.5)
//   • Clock      — analogue clock face (path from Figma, 25×25)
//   • Restaurant — fork + knife (path from Figma, 18.75×25)
//   • Lightbulb  — lightbulb / tips
//   • Calendar   — month-grid calendar for date pickers
// ─────────────────────────────────────────────────────────────

private fun parsePathData(s: String) = PathParser().parsePathString(s).toNodes()

// ── WbSunny — Figma path, 43.5×43.5 viewport ─────────────────

public val LindaIcons.Filled.WbSunny: ImageVector
    get() {
        if (_wbSunny != null) return _wbSunny!!
        _wbSunny = ImageVector.Builder(
            name          = "LindaIcons.Filled.WbSunny",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 43.5f,
            viewportHeight = 43.5f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M20.5 11.75V8H23V11.75H20.5V11.75" +
                    "M20.5 35.5V31.75H23V35.5H20.5V35.5" +
                    "M31.75 23V20.5H35.5V23H31.75V23" +
                    "M8 23V20.5H11.75V23H8V23" +
                    "M30.125 15.125L28.375 13.375L30.5625 11.125L32.375 12.9375L30.125 15.125V15.125" +
                    "M12.9375 32.375L11.125 30.5625L13.375 28.375L15.125 30.125L12.9375 32.375V32.375" +
                    "M30.5625 32.375L28.375 30.125L30.125 28.375L32.375 30.5625L30.5625 32.375V32.375" +
                    "M13.375 15.125L11.125 12.9375L12.9375 11.125L15.125 13.375L13.375 15.125V15.125" +
                    "M21.75 29.25C19.6667 29.25 17.8958 28.5208 16.4375 27.0625" +
                    "C14.9792 25.6042 14.25 23.8333 14.25 21.75" +
                    "C14.25 19.6667 14.9792 17.8958 16.4375 16.4375" +
                    "C17.8958 14.9792 19.6667 14.25 21.75 14.25" +
                    "C23.8333 14.25 25.6042 14.9792 27.0625 16.4375" +
                    "C28.5208 17.8958 29.25 19.6667 29.25 21.75" +
                    "C29.25 23.8333 28.5208 25.6042 27.0625 27.0625" +
                    "C25.6042 28.5208 23.8333 29.25 21.75 29.25V29.25" +
                    "M21.75 26.75C23.1458 26.75 24.3281 26.2656 25.2969 25.2969" +
                    "C26.2656 24.3281 26.75 23.1458 26.75 21.75" +
                    "C26.75 20.3542 26.2656 19.1719 25.2969 18.2031" +
                    "C24.3281 17.2344 23.1458 16.75 21.75 16.75" +
                    "C20.3542 16.75 19.1719 17.2344 18.2031 18.2031" +
                    "C17.2344 19.1719 16.75 20.3542 16.75 21.75" +
                    "C16.75 23.1458 17.2344 24.3281 18.2031 25.2969" +
                    "C19.1719 26.2656 20.3542 26.75 21.75 26.75V26.75"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _wbSunny!!
    }
private var _wbSunny: ImageVector? = null

// ── Clock — Figma path, 25×25 viewport ───────────────────────

public val LindaIcons.Filled.Clock: ImageVector
    get() {
        if (_clock != null) return _clock!!
        _clock = ImageVector.Builder(
            name          = "LindaIcons.Filled.Clock",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 25f,
            viewportHeight = 25f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M16.625 18.375L18.375 16.625L13.75 12V6.25H11.25V13L16.625 18.375V18.375" +
                    "M12.5 25C10.7708 25 9.14583 24.6719 7.625 24.0156" +
                    "C6.10417 23.3594 4.78125 22.4688 3.65625 21.3438" +
                    "C2.53125 20.2188 1.64062 18.8958 0.984375 17.375" +
                    "C0.328125 15.8542 0 14.2292 0 12.5" +
                    "C0 10.7708 0.328125 9.14583 0.984375 7.625" +
                    "C1.64062 6.10417 2.53125 4.78125 3.65625 3.65625" +
                    "C4.78125 2.53125 6.10417 1.64062 7.625 0.984375" +
                    "C9.14583 0.328125 10.7708 0 12.5 0" +
                    "C14.2292 0 15.8542 0.328125 17.375 0.984375" +
                    "C18.8958 1.64062 20.2188 2.53125 21.3438 3.65625" +
                    "C22.4688 4.78125 23.3594 6.10417 24.0156 7.625" +
                    "C24.6719 9.14583 25 10.7708 25 12.5" +
                    "C25 14.2292 24.6719 15.8542 24.0156 17.375" +
                    "C23.3594 18.8958 22.4688 20.2188 21.3438 21.3438" +
                    "C20.2188 22.4688 18.8958 23.3594 17.375 24.0156" +
                    "C15.8542 24.6719 14.2292 25 12.5 25V25" +
                    "M12.5 22.5C15.2708 22.5 17.6302 21.526 19.5781 19.5781" +
                    "C21.526 17.6302 22.5 15.2708 22.5 12.5" +
                    "C22.5 9.72917 21.526 7.36979 19.5781 5.42188" +
                    "C17.6302 3.47396 15.2708 2.5 12.5 2.5" +
                    "C9.72917 2.5 7.36979 3.47396 5.42188 5.42188" +
                    "C3.47396 7.36979 2.5 9.72917 2.5 12.5" +
                    "C2.5 15.2708 3.47396 17.6302 5.42188 19.5781" +
                    "C7.36979 21.526 9.72917 22.5 12.5 22.5V22.5"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _clock!!
    }
private var _clock: ImageVector? = null

// ── Restaurant — Figma path, 18.75×25 viewport ────────────────

public val LindaIcons.Filled.Restaurant: ImageVector
    get() {
        if (_restaurant != null) return _restaurant!!
        _restaurant = ImageVector.Builder(
            name          = "LindaIcons.Filled.Restaurant",
            defaultWidth  = 18.75.dp,
            defaultHeight = 25.dp,
            viewportWidth  = 18.75f,
            viewportHeight = 25f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M3.75 25V13.5625C2.6875 13.2708 1.79688 12.6875 1.07812 11.8125" +
                    "C0.359375 10.9375 0 9.91667 0 8.75V0H2.5V8.75H3.75V0H6.25V8.75" +
                    "H7.5V0H10V8.75C10 9.91667 9.64062 10.9375 8.92188 11.8125" +
                    "C8.20312 12.6875 7.3125 13.2708 6.25 13.5625V25H3.75V25" +
                    "M16.25 25V15H12.5V6.25C12.5 4.52083 13.1094 3.04688 14.3281 1.82812" +
                    "C15.5469 0.609375 17.0208 0 18.75 0V25H16.25V25"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _restaurant!!
    }
private var _restaurant: ImageVector? = null

// ── Lightbulb ─────────────────────────────────────────────────

public val LindaIcons.Filled.Lightbulb: ImageVector
    get() {
        if (_lightbulb != null) return _lightbulb!!
        _lightbulb = ImageVector.Builder(
            name          = "LindaIcons.Filled.Lightbulb",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M9 21c0 .55.45 1 1 1h4c.55 0 1-.45 1-1v-1H9v1z" +
                    "m3-19C8.14 2 5 5.14 5 9c0 2.38 1.19 4.47 3 5.74V17" +
                    "c0 .55.45 1 1 1h6c.55 0 1-.45 1-1v-2.26" +
                    "c1.81-1.27 3-3.36 3-5.74 0-3.86-3.14-7-7-7z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _lightbulb!!
    }
private var _lightbulb: ImageVector? = null

// ── Calendar (date range / date picker) ───────────────────────

public val LindaIcons.Filled.Calendar: ImageVector
    get() {
        if (_calendar != null) return _calendar!!
        _calendar = ImageVector.Builder(
            name          = "LindaIcons.Filled.Calendar",
            defaultWidth  = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth  = 24f,
            viewportHeight = 24f,
        ).apply {
            addPath(
                pathData = parsePathData(
                    "M9 11H7v2h2v-2zm4 0h-2v2h2v-2zm4 0h-2v2h2v-2z" +
                    "m2-7h-1V2h-2v2H8V2H6v2H5c-1.11 0-1.99.9-1.99 2L3 20" +
                    "c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2z" +
                    "m0 16H5V9h14v11z"
                ),
                fill = SolidColor(Color.Black),
            )
        }.build()
        return _calendar!!
    }
private var _calendar: ImageVector? = null
