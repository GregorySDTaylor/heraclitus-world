package com.gregtaylor.heraclitusworld

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.math.Vector2
import kdotjpg.opensimplex2.OpenSimplex2S

import ktx.app.KtxScreen
import ktx.graphics.use
import kotlin.random.Random

class World(private val application: HeraclitusWorldApp) : KtxScreen {

	private val font = BitmapFont()
	private val mapWidth = 161
	private val mapHeight = 101
	private val mapResolution = 10
	private val maxHeight = 20f
	private val marchingSquaresTerrain: MarchingSquaresTerrain
	private val seaLevel = 10.9f
	private val simplexNoise = OpenSimplex2S(1L)
	private var simplexZ = 0.0f
	private val simplexScale = 0.018
	private val miniSimplexNoise = OpenSimplex2S(2L)
	private val miniSimplexScale = 0.08

	init {
		// in Km, where 0 is lowest point of ocean
		val heightGrid = generateRandomNoiseGrid()
		marchingSquaresTerrain = MarchingSquaresTerrain(heightGrid)
//		marchingSquaresTerrain.update(generateRandomNoiseGrid(), seaLevel, mapResolution.toFloat(), Vector2(0.toFloat(),0.toFloat()))
//		marchingSquaresTerrain.update(generateSimplexNoiseGrid(0f), seaLevel, mapResolution.toFloat(), Vector2(0.toFloat(),0.toFloat()))
	}

	private fun generateRandomNoiseGrid(): MutableList<MutableList<Float>> {
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

	private fun generateSimplexNoiseGrid(z: Float): MutableList<MutableList<Float>> {
		val simplexNoiseGrid = mutableListOf<MutableList<Float>>()
		for (y in 0 .. mapHeight) {
			val row = mutableListOf<Float>()
			for (x in 0 .. mapWidth) {
				val value = (simplexNoise.noise3_Classic(x.toDouble() * simplexScale, y.toDouble() * simplexScale, z.toDouble() * simplexScale) + 1) * 0.4
				val miniValue = (miniSimplexNoise.noise3_Classic(x.toDouble() * miniSimplexScale, y.toDouble() * miniSimplexScale, z.toDouble() * miniSimplexScale) + 1) * 0.1
				row.add(((value + miniValue) * maxHeight).toFloat())
			}
			simplexNoiseGrid.add(row)
		}
		return simplexNoiseGrid
	}

	override fun render(delta: Float) {
		simplexZ += 0.01f
		marchingSquaresTerrain.update(generateSimplexNoiseGrid(simplexZ), seaLevel, mapResolution.toFloat(), Vector2(0.toFloat(),0.toFloat()))
//		marchingSquaresTerrain.update(generateRandomNoiseGrid(), seaLevel, mapResolution.toFloat(), Vector2(0.toFloat(),0.toFloat()))
		Gdx.gl.glClearColor(0f, 0f, 1f, 1f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
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