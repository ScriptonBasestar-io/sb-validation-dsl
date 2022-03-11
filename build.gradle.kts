import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.6.10"
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jlleitschuh.gradle.ktlint-idea")
    id("com.adarshr.test-logger")
    id("com.jfrog.bintray")
    id("maven-publish")
    id("jacoco")
    id("se.patrikerdes.use-latest-versions")
    id("com.github.ben-manes.versions")
    id("org.jetbrains.dokka")
    signing
}

repositories {
    mavenCentral()
}

group = "io.scriptonbasestar.validation"
version = "0.1.0"

extra["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")

kotlin {
    jvm()
    js(BOTH) {
        browser {
            testTask {
                useKarma {
                    usePhantomJS()
                }
            }
        }
        nodejs {
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-stdlib:${extra["kotlin_version"]}")
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common:${extra["kotlin_version"]}")
                implementation("org.jetbrains.kotlin:kotlin-reflect:${extra["kotlin_version"]}")
                implementation("org.jetbrains.kotlin:kotlin-serialization:${extra["kotlin_version"]}")
                implementation("org.jetbrains.kotlin:kotlin-native-utils:${extra["kotlin_version"]}")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${extra["coroutine_version"]}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {}
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {}
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
//        named("linuxX64Main") {
//            dependencies {
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${extra["CoroutineVersion"]}")
//                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${extra["CoroutineVersion"]}")
//                implementation("design.animus.kotlin.mp.functional.datatypes:multiplatform-functional-datatypes-linuxx64:${extra["MPDataTypes"]}")
//                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${extra["SerializationVersion"]}")
//            }
//        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = project.extra["java_version"] as String
    }
}

tasks.register("stubSources", Jar::class) {
    classifier = "sources"
}
tasks.register("stubJavadoc", Jar::class) {
    classifier = "javadoc"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Wrapper> {
    gradleVersion = project.extra["gradle_version"] as String
}

jacoco {
    toolVersion = project.extra["jacoco_version"] as String
}

// tasks {
//    val sourcesJar by creating(Jar::class) {
//        archiveClassifier.set("sources")
//        println(kotlin.sourceSets.commonMain)
//        from(kotlin.sourceSets.commonMain.get()["stubSources"])
//    }
//
//    val javadocJar by creating(Jar::class) {
//        dependsOn.add(javadoc)
//        archiveClassifier.set("javadoc")
//        from(javadoc)
//    }
//
//    artifacts {
//        archives(sourcesJar)
//        archives(javadocJar)
//        archives(jar)
//    }
// }

tasks.register<Jar>("dokkaJar") {
    archiveClassifier.set("javadoc")
    dependsOn("dokkaJavadoc")
    from("$buildDir/dokka/javadoc/")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("binary") {
//                from(components["java"])
            }
            create<MavenPublication>("binaryAndSources") {
//                from(components["java"])
                artifact(tasks["sourcesJar"])
            }
        }
//        publications.all {
// //            if (name == "kotlinMultiplatform") {
// //                artifact(stubJavadoc)
// //            } else {
// //                artifact(stubJavadoc)
// //            }
// //            logger.info("Artifact id = ${it.artifactId}")
//            pom {
//                name = rootProject.name
//                description = "description"
//                url = "https://github.com/scriptonbasestar-io/sb-validation-dsl"
//                licenses {
//                    license {
//                        name = "MIT"
//                        url = "http://opensource.org/licenses/MIT"
//                        distribution = "repo"
//                    }
//                }
//                developers {
//                    developer {
//                        id = projectDevelNick
//                        name = projectDevelName
//                    }
//                }
//
//                scm {
//                    url = "https://github.com/scriptonbasestar-io/sb-validation-dsl.git"
//                }
//            }
//        }
    }
}

// signing {
//    setRequired({
//        (project.extra["isReleaseVersion"] as Boolean) && gradle.taskGraph.hasTask("publish")
//    })
//    useGpgCmd()
//    sign(publishing.publications["main"])
// }
