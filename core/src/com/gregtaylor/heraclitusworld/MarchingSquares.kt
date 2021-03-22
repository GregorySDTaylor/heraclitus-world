package com.gregtaylor.heraclitusworld

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2

class MarchingSquares(var valueGrid: MutableList<MutableList<Float>>, scale: Float, position: Vector2) {

	private fun squares(): MutableList<MutableList<MarchingSquare>> {
		val squares = mutableListOf<MutableList<MarchingSquare>>()
		for (y in 0 until valueGrid.size) {
			val squareRow = mutableListOf<MarchingSquare>()
			val valueRow = valueGrid[y]
			for (x in 0 until valueRow.size) {
//				val marchingSquare =
//				val pointX = (x * mapResolution).toFloat()
//				val pointY = (y * mapResolution).toFloat()
			}
		}
		return squares
	}

	fun draw(shapeRenderer: ShapeRenderer) {

	}
}