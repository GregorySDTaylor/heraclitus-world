package com.gregtaylor.heraclitusworld

import com.badlogic.gdx.graphics.Color.*
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.glutils.ShapeRenderer

import ktx.app.KtxScreen
import ktx.graphics.use
import kotlin.random.Random

class World(private val application: HeraclitusWorldApp) : KtxScreen {

	private val font = BitmapFont()
	private val shapeRenderer = ShapeRenderer()
	private val mapWidth = 40
	private val mapHeight = 25
	private val mapResolution = 40

	// in Km, where 0 is lowest point of ocean
	private val heightMap = mutableListOf<MutableList<Float>>()
	private val maxHeight = 20f
	init {
		repeat(mapHeight) {
			val row = mutableListOf<Float>()
			repeat(mapWidth) {
				row.add(Random.nextFloat() * maxHeight)
			}
			heightMap.add(row)
		}
	}

	private val seaLevel = 10.9

	override fun render(delta: Float) {
		shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
			it.color = DARK_GRAY
			for (y in 0 until mapHeight) {
				for (x in 0 until mapWidth) {
					val pointX = (x * mapResolution).toFloat()
					val pointY = (y * mapResolution).toFloat()
				}
			}
		}
		application.spriteBatch.use {
			font.color = GRAY
			for (y in 0 until mapHeight) {
				for (x in 0 until mapWidth) {
					val pointX = (x * mapResolution).toFloat()
					val pointY = (y * mapResolution).toFloat()
					val pointText = heightMap.get(y).get(x).toInt().toString()
					font.draw(it, pointText, pointX - 6, pointY + 6)
				}
			}
		}
	}

	override fun dispose() {
		font.dispose()
	}
}