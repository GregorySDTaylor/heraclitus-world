package com.gregtaylor.heraclitusworld

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2

import ktx.app.KtxScreen
import ktx.graphics.use
import kotlin.random.Random

class World(private val application: HeraclitusWorldApp) : KtxScreen {

	private val font = BitmapFont()
	private val mapWidth = 41
	private val mapHeight = 26
	private val mapResolution = 40
	private val maxHeight = 20f
	private val marchingSquaresTerrain: MarchingSquaresTerrain
	private val seaLevel = 10.9f

	init {
		// in Km, where 0 is lowest point of ocean
		val heightGrid = generateRandomNoiseGrid()
		marchingSquaresTerrain = MarchingSquaresTerrain(heightGrid)
	}

	private fun generateRandomNoiseGrid() : MutableList<MutableList<Float>> {
		val randomNoiseGrid = mutableListOf<MutableList<Float>>()
		repeat(mapHeight) {
			val row = mutableListOf<Float>()
			repeat(mapWidth) {
				row.add(Random.nextFloat() * maxHeight)
			}
			randomNoiseGrid.add(row)
		}
		return randomNoiseGrid
	}

	override fun render(delta: Float) {
		marchingSquaresTerrain.update(generateRandomNoiseGrid(), seaLevel, mapResolution.toFloat(), Vector2(0.toFloat(),0.toFloat()))
		application.polygonBatch.use {
			marchingSquaresTerrain.drawPolygons(it)
		}
		application.spriteBatch.use {
			marchingSquaresTerrain.drawSprites(it)
		}
	}

	override fun dispose() {
		font.dispose()
		marchingSquaresTerrain.dispose()
	}
}