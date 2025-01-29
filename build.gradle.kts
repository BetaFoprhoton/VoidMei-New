/* Enable when SSL error occurs.
buildscript {
    dependencies {
        classpath(files("gradle/gradle-trust-all.jar"))
    }
}
*/
plugins {
    kotlin("jvm") version "2.0.20"
}

//Enable when SSL error occurs.
//apply(plugin = "trust-all")

group = "org.matrixsukhoi.voidmei"
version = "1.0.0"

repositories {
    maven {
        url = uri("https://jitpack.io")
    }
    mavenCentral()
}

dependencies {
    implementation("com.weblookandfeel:weblaf-ui:1.2.13") //weblaf 1.29's JDK version is below 9, and the weblaf version for this line is above JDK 9. There is a serious conflict between the two APIs
    //implementation(files("lib/weblaf-complete-1.29.jar"))
    implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.6")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest.attributes["Main-Class"] = "org.matrixsukhoi.voidmei.VoidMeiMainKt"
    from(configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) })
    exclude("LICENSE.txt", "NOTICE.txt", "rootdoc.txt")
    exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
}