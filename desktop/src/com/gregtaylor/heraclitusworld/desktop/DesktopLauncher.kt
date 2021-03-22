package com.gregtaylor.heraclitusworld.desktop

import kotlin.jvm.JvmStatic
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.gregtaylor.heraclitusworld.HeraclitusWorldApp

object DesktopLauncher {

	@JvmStatic
	fun main(arg: Array<String>) {
		val config = LwjglApplicationConfiguration()
		config.title = "Heraclitus' World"
		config.width = 1600
		config.height = 1000
		LwjglApplication(HeraclitusWorldApp(), config)
	}

}