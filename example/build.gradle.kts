plugins {
    kotlin("jvm")
    application
}

kotlin { jvmToolchain(19) }

dependencies {
    implementation(project(":emailx"))
    implementation("at.quickme.kotlinmailer:core:2.0.0")
    implementation("at.quickme.kotlinmailer:smtp:2.0.0")
}

application { mainClass = "ExampleKt" }

tasks.register<JavaExec>("passwordReset") {
    group = "application"
    description = "Render the Swypex password-reset email example"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "PasswordResetExampleKt"
}

tasks.register<JavaExec>("monthlyOverview") {
    group = "application"
    description = "Render the complex monthly overview email"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "MonthlyOverviewExampleKt"
}

tasks.register<JavaExec>("sendEmail") {
    group = "application"
    description = "Send the welcome email through Gmail using an app password"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "GmailSenderKt"
}
