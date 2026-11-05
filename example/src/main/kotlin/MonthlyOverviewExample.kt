import com.swypex.emailx.ColorScheme
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

private const val BRAND_BLUE = "#0021cc"

internal data class Activity(
    val merchant: String,
    val category: String,
    val date: String,
    val amount: String,
)

internal data class Overview(
    val name: String,
    val company: String,
    val period: String,
    val total: String,
    val available: String,
    val cards: Int,
    val activity: List<Activity>,
)

private fun FlowContent.metric(label: String, value: String) {
    section("bg-indigo-50", "p-4", "rounded-lg") {
        paragraph("text-xs", "text-gray-500", "mb-2") { +label }
        paragraph("text-xl", "font-bold", "text-gray-900", "m-0") { +value }
    }
}

private fun FlowContent.activityTable(activity: List<Activity>) {
    table {
        thead {
            tr {
                th {
                    attributes["align"] = "left"
                    style =
                        "padding:12px 0;border-bottom:1px solid #e5e7eb;color:#6b7280;font-size:12px;"
                    +"TRANSACTION"
                }
                th {
                    attributes["align"] = "right"
                    style =
                        "padding:12px 0;border-bottom:1px solid #e5e7eb;color:#6b7280;font-size:12px;"
                    +"AMOUNT"
                }
            }
        }

        tbody {
            activity.forEach { item ->
                tr {
                    td {
                        style = "padding:14px 0;border-bottom:1px solid #f3f4f6;"
                        strong { +item.merchant }
                        br
                        span {
                            style = "font-size:14px;color:#6b7280;"
                            +"${item.category} · ${item.date}"
                        }
                    }
                    td {
                        attributes["align"] = "right"
                        style = "padding:14px 0;border-bottom:1px solid #f3f4f6;white-space:nowrap;"
                        +item.amount
                    }
                }
            }
        }
    }
}

internal fun monthlyOverview(data: Overview): EmailTemplate =
    email("m-0", "bg-slate-100", "font-sans", colorScheme = ColorScheme.LIGHT_ONLY) {
        preview("Your ${data.period} overview is ready for ${data.company}.")

        container("bg-transparent", "py-6", width = 640) {
            attributes["emailx-in-text"] = ""
            section("px-4", "py-6", style = "background-color:$BRAND_BLUE;") {
                image(
                    src = "https://d1sgr3u4kblo66.cloudfront.net/swypex_logo_white_2x.png",
                    alt = "Swypex",
                    width = 146,
                )
            }

            section("px-4", "pt-10", "pb-8", "bg-white") {
                paragraph("text-blue-700", "text-xs", "font-bold", "mb-3") {
                    +"YOUR MONTHLY OVERVIEW · ${data.period.uppercase()}"
                }

                paragraph("text-base", "text-gray-700") {
                    +"Hi ${data.name}, here’s what happened at ${data.company} this month."
                }

                spacer(12)

                metric("TOTAL CARD SPEND", data.total)
                spacer(12)
                metric("AVAILABLE BALANCE", data.available)

                spacer(24)

                section("bg-gray-100", "p-5", "rounded-xl") {
                    paragraph("text-sm", "font-bold", "text-gray-900", "mb-2") {
                        +"Card Status"
                    }

                    paragraph("text-sm", "text-gray-700", "m-0") {
                        +"${data.cards} active cards."
                    }
                }

                spacer(32)

                heading("text-xl", "font-bold", "text-gray-900", "mb-2", level = 2) {
                    +"Recent card activity"
                }

                paragraph("text-sm", "text-gray-500") { +"A snapshot of your latest transactions." }

                activityTable(data.activity)

                spacer(28)

                button(
                    "text-white",
                    "px-6",
                    "py-4",
                    "rounded-xl",
                    "font-bold",
                    href = "https://example.com/dashboard",
                    backgroundColor = BRAND_BLUE,
                ) {
                    +"View your dashboard →"
                }

                spacer(32)
                divider("my-0")
                spacer(24)

                section(direction = Direction.RTL, lang = "ar") {
                    heading("text-xl", "font-bold", "text-gray-900", "mb-3", level = 2) {
                        +"نظرة على نشاطك الشهري"
                    }
                    paragraph("text-sm", "text-gray-700", "mb-4") {
                        +"مرحباً ${data.name}، ملخص نشاط ${data.company} لشهر ${data.period} جاهز للاطلاع. يمكنك متابعة المعاملات وإدارة البطاقات من لوحة التحكم."
                    }
                    button(
                        "text-white",
                        "px-6",
                        "py-3",
                        "rounded-xl",
                        href = "https://example.com/dashboard",
                        backgroundColor = BRAND_BLUE,
                        direction = Direction.RTL,
                    ) {
                        +"عرض لوحة التحكم ←"
                    }
                }
            }
            section("bg-indigo-50", "px-4", "py-8") {
                paragraph("text-gray-900", "font-bold", "text-sm", "mb-2") { +"Need a hand?" }
                paragraph("text-gray-700", "text-sm", "mb-4") {
                    +"Our team is here if you have questions about your account or this overview."
                }
                link("text-blue-700", "underline", href = "https://example.com/support") {
                    +"Visit the help center"
                }
            }
            section("px-4", "py-8", "bg-white") {
                paragraph("text-gray-500", "text-xs", "mb-2") {
                    +"This is a sample account summary. Amounts and transactions are illustrative."
                }
                paragraph("text-gray-500", "text-xs", "m-0") { +"Swypex · Cairo, Egypt" }
            }
        }
    }

fun main() {
    val data =
        Overview(
            name = "Sara Amr",
            company = "Self",
            period = "October 2026",
            total = "EGP 128,450.00",
            available = "EGP 245,800.00",
            cards = 12,
            activity =
                listOf(
                    Activity("Hyperone", "Office", "Oct 27", "EGP 3,200.00"),
                    Activity("Cairo Workspace", "Office", "Oct 21", "EGP 18,500.00"),
                    Activity("Cloud Services", "Technology", "Oct 14", "EGP 6,750.00"),
                    Activity("Team Travel", "Travel", "Oct 08", "EGP 12,400.00"),
                ),
        )
    val template = monthlyOverview(data)
    val output = Path.of("/tmp/kotlin-emailx-monthly-overview.html")

    Files.writeString(output, template.toHtml())
    println("HTML saved to $output")
}
