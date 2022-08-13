package me.xtrm.skybot.config

import gg.essential.vigilance.Vigilant
import gg.essential.vigilance.data.Property
import gg.essential.vigilance.data.PropertyType
import me.uwu.skybot.Consts.*
import java.io.File

object SkyBotConfig : Vigilant(File("./config/skybot.toml"), "$NAME ($VERSION)") {
    @JvmStatic
    @Property(
        type = PropertyType.SWITCH,
        name = "Active",
        description = "Whether the bot is active or not.",
        category = "Main"
    )
    val active: Boolean = true

    init {
        initialize()
    }
}