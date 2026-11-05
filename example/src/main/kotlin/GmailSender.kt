import at.quickme.kotlinmailer.delivery.send
import at.quickme.kotlinmailer.email.emailBuilder
import at.quickme.kotlinmailer.smtp.SmtpSecurity
import at.quickme.kotlinmailer.smtp.smtpMailer

/**
 * Send the welcome example through Gmail:
 * 1. Enable 2-Step Verification and create an app password at
 *    https://myaccount.google.com/apppasswords.
 * 2. Run `GMAIL_ADDRESS=you@gmail.com GMAIL_APP_PASSWORD="pass word" GMAIL_TO=to@gmail.com
 *    ./gradlew :example:sendEmail`. Optionally set GMAIL_TO to a different recipient.
 */
suspend fun main() {
    val address =
        System.getenv("GMAIL_ADDRESS") ?: throw RuntimeException("From address is required.")
    val password =
        System.getenv("GMAIL_APP_PASSWORD")?.replace(" ", "")
            ?: throw RuntimeException("Gmail app password is required.")
    val recipient = System.getenv("GMAIL_TO")?.takeIf { it.isNotBlank() } ?: address

    val body =
        monthlyOverview(
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
            ),
        )

    val mailer =
        smtpMailer("smtp.gmail.com") {
            security = SmtpSecurity.STARTTLS
            credentials(address, password)
        }

    try {
        emailBuilder {
                from(address, "Swypex Emailx Sender")
                to(recipient, "Swypex Emailx Receiver")
                subject = "Emailx: Test Email"
                html = body.toHtml()
                text = body.toText()
            }
            .send(mailer)

        println("Sent the welcome example to $recipient")
    } finally {
        mailer.close()
    }
}
