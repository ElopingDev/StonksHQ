@file:Suppress("UnstableApiUsage")

import gg.essential.gradle.util.noServerRunConfigs

plugins {
    kotlin("jvm")
    id("net.kyori.blossom") version "1.3.1"
    id("gg.essential.multi-version")
    id("gg.essential.defaults")
}

val modGroup: String by project
val modBaseName: String by project
val modId: String by project
group = modGroup
base.archivesName.set(modBaseName)
version = "1.0.0"

val accessTransformerName = "${modId}1${platform.mcMinor}_at.cfg"

loom {
    noServerRunConfigs()
    launchConfigs {
        getByName("client") {
            arg("--tweakClass", "gg.essential.loader.stage0.EssentialSetupTweaker")
            arg("--mixin", "$modId.mixins.json")
        }
    }
    forge {
        accessTransformer(rootProject.file("src/main/resources/$accessTransformerName"))
    }
    mixin {
        defaultRefmapName.set("patcher.mixins.refmap.json")
    }
}

blossom {
    replaceToken("@VERSION@", version)
    replaceToken("@NAME@", modBaseName)
    replaceToken("@ID@", modId)
}

configurations {
    // Creates a configuration called `include` to declare dependencies
    val include: Configuration by creating
    implementation.get().extendsFrom(include)
}

repositories {
    maven("https://repo.essential.gg/repository/maven-public/")
    maven("https://repo.spongepowered.org/repository/maven-public/")
}

dependencies {
    val include: Configuration by configurations

    // With ´include´ you include libraries to be inside your .jar file.
    include("gg.essential:loader-launchwrapper:1.1.3")
    // With ´implementation´ you include libraries NOT to be inside your .jar file.
    implementation("gg.essential:essential-$platform:4276+g845a16235")

    include("com.github.Shyrogan:Post:1.1.2") {
        exclude(group = "org.ow2.asm")
    }

    include("org.reflections:reflections:0.10.2")

    compileOnly("org.spongepowered:mixin:0.8.5-SNAPSHOT")

    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
}

tasks {
    val include: Configuration by configurations

    compileJava {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
        options.encoding = "UTF-8"
    }
    jar {
        from(include.files.map { zipTree(it) }) {
            filesMatching(accessTransformerName) { name = "META-INF/$name" }
        }

        manifest.attributes(
            mapOf(
                "ModSide" to "CLIENT",
                "FMLAT" to accessTransformerName,
                "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker",
                "TweakOrder" to "0",
                "MixinConfigs" to "$modId.mixins.json"
            )
        )
    }
}