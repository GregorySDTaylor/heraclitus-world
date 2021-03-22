package com.gregtaylor.heraclitusworld

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.StretchViewport
import ktx.app.KtxGame

class HeraclitusWorldApp : KtxGame<Screen>() {

	var width = 0f
	var height = 0f

	lateinit var spriteBatch: SpriteBatch
	lateinit var camera: OrthographicCamera
	lateinit var view: StretchViewport
	lateinit var world: World

	override fun create() {

		width = Gdx.graphics.width.toFloat()
		height = Gdx.graphics.height.toFloat()

		spriteBatch = SpriteBatch()
		camera = OrthographicCamera(this.width, this.height)
		view = StretchViewport(480f, 360f, this.camera)

		world = World(this)
		addScreen(world)
		setScreen<World>()
	}

	override fun dispose() {
		spriteBatch.dispose()
		world.dispose()
	}

}