plugins {
    kotlin("jvm") version "2.0.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.jetbrains.dokka") version "1.9.0"
}

group = "io.github.sobixn.project_vir"
version = "1.21.1-R0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.json:json:20090211")
    implementation("org.xerial.snappy:snappy-java:1.1.10.5")
    implementation("io.ktor:ktor-client-core:2.3.4")
    implementation("io.ktor:ktor-client-serialization:2.0.0")
    implementation("io.ktor:ktor-client-cio:2.3.4") // Ktor CIO 엔진
    implementation("io.ktor:ktor-client-content-negotiation:2.3.4") // Content Negotiation 플러그인
    implementation("io.ktor:ktor-client-websockets:2.3.4") // WebSocket 플러그인
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4") // JSON 직렬화




    implementation("io.socket:socket.io-client:2.1.0") {
      exclude(group = "org.json", module = "json")
   }
    implementation("org.json:json:20090211")
    implementation("org.xerial.snappy:snappy-java:1.1.10.5")

        implementation("io.socket:socket.io-client:2.1.0") {
         exclude(group = "org.json", module = "json")
      }

    }


val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}
