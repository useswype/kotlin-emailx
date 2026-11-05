package com.swypex.emailx

import com.swypex.emailx.tailcss.tailcssStyle
import kotlinx.html.*
import kotlinx.html.stream.createHTML

// Text direction for a block
// LTR -> Left To Right
// RTL -> Right To Left
// AUTO -> Inherited from surrounding blocks
enum class Direction(internal val htmlValue: String) {
    LTR("ltr"),
    RTL("rtl"),
    AUTO("auto"),
}

// Optional color-scheme hint for email clients.
// Gmail apps, for example, renders dark mode and automatically
// change your colors for you. Using the LIGHT_ONLY option here
// hints to Gmail to not change colors and keep the light mode
// (original colors) regardless of device preferences.
//
// More info can be found here: https://emailmarkup.org/en/blog/2026/gmail-app-dark-mode-update/
enum class ColorScheme(internal val metaValue: String) {
    LIGHT_ONLY("light only"),
}

// An immutable, rendered email body, reusable with any delivery provider.
class EmailTemplate internal constructor(private val html: String, private val text: String) {
    fun toHtml(): String = html

    fun toText(): String = text
}

/**
 * Build a complete HTML document
 *
 * @param lang the language of the document, for accessibility and SEO
 * @param text optional plain-text fallback; if null, marked HTML elements are used
 * @param className optional Tailcss style classes to apply to the body
 * @param colorScheme optional color-scheme hint; omitted by default
 * @param content the HTML body content
 */
fun email(
    vararg className: String,
    lang: String = "en",
    text: String? = null,
    direction: Direction? = null,
    colorScheme: ColorScheme? = null,
    prettyPrint: Boolean? = false,
    content: BODY.() -> Unit,
): EmailTemplate {
    val consumer =
        TextCollectingConsumer(
            createHTML(prettyPrint = prettyPrint == true, xhtmlCompatible = true),
        )
    val html =
        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
            consumer.html {
                attributes["lang"] = lang

                head {
                    meta {
                        httpEquiv = "Content-Type"
                        this.content = "text/html; charset=UTF-8"
                    }

                    meta { name = "x-apple-disable-message-reformatting" }

                    colorScheme?.let { scheme ->
                        meta {
                            name = "color-scheme"
                            this.content = scheme.metaValue
                        }
                    }

                    meta {
                        name = "viewport"
                        this.content = "width=device-width, initial-scale=1.0"
                    }
                }

                body {
                    style = "margin:0;padding:0;" + tailcssStyle(className.toList())
                    direction?.let { attributes["dir"] = it.htmlValue }
                    content()
                }
            }

    return EmailTemplate(html, text ?: consumer.text())
}

/** Collect text directly from marked tags as kotlinx.html emits the document. */
private class TextCollectingConsumer(
    private val delegate: TagConsumer<String>,
) : TagConsumer<String> by delegate {
    private val tags = mutableListOf<Tag>()
    private val result = StringBuilder()

    override fun onTagStart(tag: Tag) {
        tags.add(tag)
        delegate.onTagStart(tag)
    }

    override fun onTagEnd(tag: Tag) {
        val wasIncluded = tags.any { it.attributes.containsKey("emailx-in-text") }
        delegate.onTagEnd(tag)
        tags.removeAt(tags.lastIndex)
        if (wasIncluded && tags.none { it.attributes.containsKey("emailx-in-text") }) {
            result.append("\n\n")
        }
    }

    override fun onTagContent(content: CharSequence) {
        if (tags.any { it.attributes.containsKey("emailx-in-text") }) result.append(content)
        delegate.onTagContent(content)
    }

    override fun onTagContentEntity(entity: Entities) {
        if (tags.any { it.attributes.containsKey("emailx-in-text") }) result.append(entity.text)
        delegate.onTagContentEntity(entity)
    }

    fun text(): String = result.toString().trim()
}
