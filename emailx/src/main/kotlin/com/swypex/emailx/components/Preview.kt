package com.swypex.emailx.components

import kotlinx.html.*

// Hidden inbox preview; leave it outside marked text content to omit it from the text body.
fun FlowContent.preview(text: String) {
    div {
        attributes["data-skip-in-text"] = "true"
        style =
            """
            display:none!important;
            visibility:hidden;
            opacity:0;
            color:transparent;
            height:0;
            width:0;
            overflow:hidden;
            mso-hide:all;
            """
                .trimIndent()

        +text
    }
}
