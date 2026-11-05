package com.swypex.emailx.tailcss

/**
 * A very small subset of Tailwind CSS utilities, with a few extra email-friendly utilities, for use
 * in emailx components.
 *
 * This is not a full Tailwind implementation, and is not intended to be. This allows us to build
 * emails with a familiar utility-first approach, while keeping the build pure kotlin and avoiding
 * the complexity of a full Tailwind build process.
 *
 * We also decided not to make this more Kotlin idiomatic by using enums or sealed classes for the
 * utilities, to keep the syntax as close to Tailwind and familiar as possible.
 */
internal fun tailcssDeclarations(classes: List<String>): List<String> = classes.flatMap(::utility)

fun tailcssStyle(classes: List<String>): String =
    tailcssDeclarations(classes).joinToString(";").let { if (it.isEmpty()) it else "$it;" }

private val fixed =
    mapOf(
        // Display
        "block" to listOf("display:block"),
        "inline-block" to listOf("display:inline-block"),
        "inline" to listOf("display:inline"),
        "hidden" to listOf("display:none"),

        // Width
        "w-auto" to listOf("width:auto"),
        "w-full" to listOf("width:100%"),
        "w-1/2" to listOf("width:50%"),
        "w-1/3" to listOf("width:33.33%"),
        "w-2/3" to listOf("width:66.67%"),

        // Margin
        "mx-auto" to listOf("margin-left:auto", "margin-right:auto"),

        // Text
        "text-left" to listOf("text-align:left"),
        "text-center" to listOf("text-align:center"),
        "text-right" to listOf("text-align:right"),
        "font-sans" to listOf("font-family:Arial,Helvetica,sans-serif"),
        "font-normal" to listOf("font-weight:400"),
        "font-medium" to listOf("font-weight:500"),
        "font-semibold" to listOf("font-weight:600"),
        "font-bold" to listOf("font-weight:700"),
        "underline" to listOf("text-decoration:underline"),
        "no-underline" to listOf("text-decoration:none"),
        "text-xs" to listOf("font-size:12px", "line-height:16px"),
        "text-sm" to listOf("font-size:14px", "line-height:20px"),
        "text-base" to listOf("font-size:16px", "line-height:24px"),
        "text-lg" to listOf("font-size:18px", "line-height:28px"),
        "text-xl" to listOf("font-size:20px", "line-height:28px"),
        "text-2xl" to listOf("font-size:24px", "line-height:32px"),
        "text-3xl" to listOf("font-size:30px", "line-height:36px"),
        "leading-4" to listOf("line-height:16px"),
        "leading-5" to listOf("line-height:20px"),
        "leading-6" to listOf("line-height:24px"),

        // Border Radius
        "rounded-none" to listOf("border-radius:0"),
        "rounded" to listOf("border-radius:4px"),
        "rounded-md" to listOf("border-radius:6px"),
        "rounded-lg" to listOf("border-radius:8px"),
        "rounded-xl" to listOf("border-radius:16px"),
        "rounded-full" to listOf("border-radius:9999px"),
    )

private val colorPrefixes =
    listOf("bg-" to "background-color", "text-" to "color", "border-" to "border-color")

private val utilityRegex =
    Regex(
        "^(p|px|py|pt|pr|pb|pl|m|mx|my|mt|mr|mb|ml)-(0|0\\.5|1|1\\.5|2|2\\.5|3|3\\.5|4|5|6|7|8|9|10|11|12|14|16|20|24|28|32|36|40|44|48|52|56|60|64|72|80|96)$",
    )

private fun utility(name: String): List<String> {
    fixed[name]?.let {
        return it
    }

    for ((prefix, property) in colorPrefixes) {
        if (name.startsWith(prefix)) {
            tailcssColors[name.removePrefix(prefix)]?.let {
                return listOf("$property:$it")
            }
        }
    }

    val match = utilityRegex.matchEntire(name)
    if (match != null) {
        val (axis, value) = match.destructured
        val number = value.toDouble() * 4

        val px =
            if (number % 1.0 == 0.0) {
                number.toInt().toString()
            } else {
                number.toString()
            }

        val property =
            if (axis.startsWith('p')) {
                "padding"
            } else {
                "margin"
            }

        val sides =
            when (axis.drop(1)) {
                "x" -> listOf("left", "right")
                "y" -> listOf("top", "bottom")
                "t" -> listOf("top")
                "r" -> listOf("right")
                "b" -> listOf("bottom")
                "l" -> listOf("left")
                else -> emptyList()
            }

        return if (sides.isEmpty()) {
            listOf("$property:${px}px")
        } else {
            sides.map { "$property-$it:${px}px" }
        }
    }

    throw IllegalArgumentException("Unsupported utility: $name")
}
