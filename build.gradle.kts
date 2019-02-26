import org.eclipse.jgit.util.RawCharUtil.trimTrailingWhitespace

plugins {
    antlr
    application
    eclipse
    id("com.diffplug.gradle.spotless") version "3.18.0"
}

group = "net.rptools.maptool.dice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.7.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.0")
    compile("org.reflections", "reflections", "0.9.11")
    compile("org.apache.commons", "commons-text", "1.6")
    compile("com.diffplug.spotless", "spotless-plugin-gradle", "3.18.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

application {
    mainClassName = "net.rptools.maptool.dice.DiceTest"
}


tasks.generateGrammarSource {
    maxHeapSize = "64m"
    arguments = arguments + listOf("-visitor", "-long-messages")
}

spotless {
    val javaFormatterConfigFile = rootProject.file("build-resources/eclipse.prefs.formatter.xml");

    java {
        //licenseHeaderFile(file("build-resources/spotless.license.java"))
        trimTrailingWhitespace()
        paddedCell()
        eclipse().configFile(javaFormatterConfigFile)
    }
}

tasks.jar {
    manifest {
        attributes(
                "Implementation-Title" to "Dice",
                "Implementation-Version" to version,
                "Main-Class" to "net.rptools.maptool.dice.DiceTest"
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

task<Jar>("uberJar") {
    appendix = "uber"

    manifest {
        attributes(
                "Implementation-Title" to "Dice",
                "Implementation-Version" to version,
                "Main-Class" to "net.rptools.maptool.dice.DiceTest"
        )
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from(Callable {
        configurations.runtimeClasspath.filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
