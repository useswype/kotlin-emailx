plugins {
    kotlin("jvm")
    `java-library`
    `maven-publish`
    id("com.gradleup.nmcp")
    signing
}

kotlin { jvmToolchain(19) }

java {
    withSourcesJar()
    withJavadocJar()
}

// The library is written in Kotlin, so Java's Javadoc task has no API to document.
tasks.named<Jar>("javadocJar") { from(rootProject.file("README.md")) }

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-html-jvm:0.12.0")
    testImplementation(kotlin("test"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name = "Swypex Emailx"
                description =
                    "A Kotlin DSL for building emails with reusable, tested, components for best rendering results."
                url = "https://github.com/useswype/kotlin-emailx"
                developers {
                    developer {
                        name = "Swypex Engineering"
                        email = "eng@swypex.com"
                        url = "https://github.com/useswype/kotlin-emailx/graphs/contributors"
                        organization = "Swypex"
                        organizationUrl = "https://www.swypex.com"
                    }
                }
                organization {
                    name = "Swypex"
                    url = "https://www.swypex.com"
                }
                scm {
                    connection = "scm:git:https://github.com/useswype/kotlin-emailx.git"
                    developerConnection = "scm:git:ssh://git@github.com/useswype/kotlin-emailx.git"
                    url = "https://github.com/useswype/kotlin-emailx"
                }
                licenses {
                    license {
                        name = "Apache License, Version 2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                        distribution = "repo"
                    }
                }
            }
        }
    }
}

val unsignedLocal =
    providers.gradleProperty("unsignedLocal").map(String::toBoolean).getOrElse(false)

tasks
    .matching {
        it.name.startsWith("publishAllPublicationsToCentral") ||
            it.name.startsWith("nmcpPublishAllPublicationsToCentral")
    }
    .configureEach {
        doFirst {
            require(!unsignedLocal) { "unsignedLocal cannot be used for Central Portal uploads" }
        }
    }

tasks.withType<PublishToMavenRepository>().configureEach {
    doFirst { require(!unsignedLocal) { "unsignedLocal is only for publishToMavenLocal" } }
}

signing {
    val key = providers.gradleProperty("signingKey").orNull
    val password = providers.gradleProperty("signingPassword").orNull
    val keyId = providers.gradleProperty("signingKeyId").orNull?.takeIf { it.isNotBlank() }

    if (key != null) {
        if (keyId != null) useInMemoryPgpKeys(keyId, key, password)
        else useInMemoryPgpKeys(key, password)
    }

    if (!unsignedLocal) {
        sign(publishing.publications["maven"])
    }
}
