
plugins {
    antlr
    application
}

group = "net.rptools.maptool.dice"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    antlr("org.antlr:antlr4:4.5")
    testCompile("junit", "junit", "4.12")
    compile("org.reflections", "reflections", "0.9.11")
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

tasks.jar {
    manifest {
        attributes(
                "Implementation-Title" to "Dice",
                "Implementation-Version" to version,
                "Main-Class" to "net.rptools.maptool.dice.DiceTest"
        )
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
