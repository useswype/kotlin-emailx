import com.swypex.emailx.*
import com.swypex.emailx.components.*
import com.swypex.emailx.components.link
import com.swypex.emailx.components.section
import java.nio.file.Files
import java.nio.file.Path
import kotlinx.html.*

/** A personalized welcome email built from ordinary Kotlin functions and emailx components. */
fun welcomeEmail(name: String) =
    email("m-0", "bg-stone-300", "font-sans") {
        preview("Welcome to Swypex.")

        container("px-4", "py-10", width = 600) {
            attributes["emailx-in-text"] = ""
            paragraph("text-gray-900", "font-bold", "text-lg", "mb-6") {
                +"✳  SWYPEX"
            }

            section("bg-white", "rounded-lg", "p-10") {
                section {
                    paragraph("text-gray-500", "text-xs", "font-bold", "mb-4") {
                        +"A FRESH START"
                    }

                    heading("text-3xl", "text-gray-900", "font-normal", "mb-5") {
                        +"Make room for "
                        br
                        +"your best work."
                    }

                    paragraph("text-gray-700", "text-base", "mb-4") {
                        +"Hi $name,"
                    }

                    paragraph("text-gray-700", "text-base", "mb-7") {
                        +"Welcome to Northstar. Your workspace is ready for the ideas, projects, and people that matter most."
                    }

                    button(
                        "bg-blue-600",
                        "text-white",
                        "px-6",
                        "py-4",
                        "rounded-md",
                        "font-bold",
                        "text-sm",
                        href = "https://example.com/start",
                    ) {
                        +"Explore your workspace  →"
                    }

                    section("bg-gray-100", "rounded", "p-5", "mt-8") {
                        paragraph("text-gray-900", "text-sm", "font-bold", "mb-2") {
                            +"A good place to begin"
                        }

                        paragraph("text-gray-700", "text-sm", "m-0") {
                            +"Create your first project, invite your team, and make the space your own."
                        }
                    }

                    paragraph("text-gray-700", "text-sm", "m-0") {
                        +"Questions? We're always happy to "
                        link("text-blue-600", "underline", href = "https://example.com/help") {
                            +"lend a hand"
                        }
                        +"."
                    }
                }

                divider("mt-8", "mb-6")

                section(
                    direction = Direction.RTL,
                    lang = "ar",
                ) {
                    paragraph("text-gray-500", "text-xs", "font-bold", "mb-4") {
                        +"بداية جديدة"
                    }

                    heading("text-3xl", "text-gray-900", "font-normal", "mb-5") {
                        +"أفسح المجال"
                        br
                        +"لأفضل أعمالك."
                    }

                    paragraph("text-gray-700", "text-base", "mb-4") {
                        +"مرحباً $name،"
                    }

                    paragraph("text-gray-700", "text-base", "mb-7") {
                        +"مرحباً بك في نورث ستار. مساحة عملك جاهزة للأفكار والمشاريع والأشخاص الأكثر أهمية بالنسبة لك."
                    }

                    button(
                        "bg-blue-600",
                        "text-white",
                        "px-6",
                        "py-4",
                        "rounded-md",
                        "font-bold",
                        "text-sm",
                        href = "https://example.com/start",
                        direction = Direction.RTL,
                    ) {
                        +"استكشف مساحة عملك ←"
                    }

                    section("bg-gray-100", "rounded", "p-5", "mt-8") {
                        paragraph("text-gray-900", "text-sm", "font-bold", "mb-2") {
                            +"مكان مناسب للبدء"
                        }

                        paragraph("text-gray-700", "text-sm", "m-0") {
                            +"أنشئ مشروعك الأول، وادعُ فريقك، واجعل المساحة تناسب احتياجاتك."
                        }
                    }

                    paragraph("text-gray-700", "text-sm", "m-0") {
                        +"هل لديك أسئلة؟ يسعدنا دائماً "
                        link("text-blue-600", "underline", href = "https://example.com/help") {
                            +"مساعدتك"
                        }
                        +"."
                    }
                }
            }

            paragraph("text-gray-500", "text-xs", "mt-6", "mb-2") {
                +"Northstar · A calmer way to work"
            }

            paragraph("text-gray-500", "text-xs", "m-0") {
                +"You're receiving this email because you created a Northstar account."
            }
        }
    }

fun main() {
    val body = welcomeEmail("Alex")

    val output = Path.of("/tmp/kotlin-emailx-example.html")
    Files.writeString(output, body.toHtml())

    println("HTML saved to $output")
    println("\nPlain text:\n${body.toText()}")
}
