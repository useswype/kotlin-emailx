import com.swypex.emailx.Direction
import com.swypex.emailx.EmailTemplate
import com.swypex.emailx.components.*
import com.swypex.emailx.components.link
import com.swypex.emailx.components.section
import com.swypex.emailx.components.table
import com.swypex.emailx.email
import java.nio.file.Files
import java.nio.file.Path
import kotlinx.html.*

private const val SWYPEX_BLUE = "#0021cc"

private val swypexHeader: TD.() -> Unit = {
    section("py-10", "px-2.5", style = "background-color:$SWYPEX_BLUE;") {
        attributes["align"] = "center"
        link(href = "https://app.swypex.com") {
            image(
                "mx-auto",
                src = "https://d1sgr3u4kblo66.cloudfront.net/swypex_logo_white_2x.png",
                alt = "Swypex Logo",
                width = 146,
            )
        }
    }
}

private val swypexFooter: TD.() -> Unit = {
    section(
        "py-14",
        "px-8",
        "text-center",
        style = "background-color:#dee2f8;color:$SWYPEX_BLUE;",
    ) {
        attributes["align"] = "center"
        table(presentation = true, width = null) {
            attributes["align"] = "center"
            tr {
                listOf(
                        Triple("https://www.swypex.com", "web_icon.png", "Swypex Website"),
                        Triple(
                            "https://www.linkedin.com/company/swypex/",
                            "li_icon.png",
                            "Swypex LinkedIn",
                        ),
                        Triple(
                            "https://www.instagram.com/swypexaccount/",
                            "ig_icon.png",
                            "Swypex Instagram",
                        ),
                        Triple(
                            "https://www.facebook.com/swypexaccount",
                            "fb_icon.png",
                            "Swypex Facebook",
                        ),
                    )
                    .forEach { (url, icon, label) ->
                        column("p-2.5") {
                            link(href = url) {
                                image(
                                    src = "https://d1sgr3u4kblo66.cloudfront.net/$icon",
                                    alt = label,
                                    width = 24,
                                )
                            }
                        }
                    }
            }
        }

        paragraph(
            "mt-6",
            "mb-0",
            "text-sm",
            "text-center",
            "mx-auto",
            "w-1/2",
            style = "color:$SWYPEX_BLUE;",
        ) {
            +"Linx Business Park (F5), Unit #A02, 1st Floor, Building #B214 Smart Village, Cairo-Alex Desert Road"
        }
    }
}

fun swypexPasswordReset(userName: String, resetLink: String): EmailTemplate =
    email("m-0", "py-20", "bg-neutral-100", "font-sans") {
        container(width = 600) {
            attributes["emailx-in-text"] = ""
            swypexHeader()

            section("bg-white", "pt-10", "px-20", "pb-5", "text-base", lang = "en") {
                paragraph {
                    +"Dear $userName,"
                    br
                    +"To reset your password, please click the link below:"
                    br
                    link("underline", href = resetLink, style = "color:$SWYPEX_BLUE;") {
                        +"Reset Password"
                    }
                }

                paragraph {
                    +"This link will be active for 24 hours from when this email was sent."
                }

                paragraph {
                    +"If you did not request to reset your password, please ignore this email and your account will not be affected."
                }

                paragraph {
                    +"Best regards,"
                    br
                    +"The Swypex Team"
                }
            }

            section("bg-white", "py-5", "px-20") {
                divider("w-1/2", "border-indigo-200", "mx-auto", "my-0")
            }

            section(
                "bg-white",
                "pt-10",
                "px-20",
                "pb-5",
                "text-base",
                direction = Direction.RTL,
                lang = "ar",
            ) {
                paragraph {
                    +"مرحبًا $userName،"
                    br
                    +"لإعادة تعيين كلمة المرور الخاصة بك، يرجى ضغط فوق الرابط أدناه:"
                    br
                    link("underline", href = resetLink, style = "color:$SWYPEX_BLUE;") {
                        +"إعادة تعيين كلمة المرور"
                    }
                }
                paragraph {
                    +"سيكون هذا الرابط نشطًا لمدة 24 ساعة من وقت إرسال هذا البريد الإلكتروني."
                }

                paragraph {
                    +"إذا لم تطلب إعادة ضبط كلمة المرور الخاصة بك، فيرجى تجاهل هذا البريد الإلكتروني ولن يتأثر حسابك."
                }

                paragraph {
                    +"مع أطيب التحيات،"
                    br
                    +"فريق سوايب اكس"
                }
            }

            swypexFooter()
        }
    }

fun main() {
    val template =
        swypexPasswordReset(
            "Alex",
            "https://example.com/en/reset",
        )

    val output = Path.of("/tmp/kotlin-emailx-password-reset.html")

    Files.writeString(output, template.toHtml())

    println("HTML saved to $output")
    println("\nPlain text:\n${template.toText()}")
}
