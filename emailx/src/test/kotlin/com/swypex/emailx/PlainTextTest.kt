package com.swypex.emailx

import com.swypex.emailx.components.preview
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.html.*

class PlainTextTest {
    @Test
    fun onlyMarkedContentAndItsDescendantsAreIncludedOnce() {
        val template = email {
            preview("Hidden preview")
            p { +"Excluded" }
            div {
                attributes["emailx-in-text"] = ""
                p {
                    +"Hello "
                    strong {
                        attributes["emailx-in-text"] = ""
                        +"world"
                    }
                    +"!"
                }
                a(href = "https://example.com") { +"Visit" }
                br
                img(src = "x", alt = "Logo")
            }
            p {
                attributes["emailx-in-text"] = ""
                +"Another"
            }
        }
        assertEquals("Hello world!Visit\n\nAnother", template.toText())
    }

    @Test
    fun emptyAndExplicitTextAreRespected() {
        assertEquals("", email { p { +"Unmarked" } }.toText())
        assertEquals(
            "Explicit",
            email(text = "Explicit") {
                    p {
                        attributes["emailx-in-text"] = ""
                        +"Marked"
                    }
                }
                .toText(),
        )
    }
}
