package com.gregtaylor.heraclitusworld

import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import ktx.math.vec3

class MarchingSquaresTerrain(var valueGrid: MutableList<MutableList<Float>>) {

	private val marchingSquares: MutableList<MutableList<MarchingSquare>> = mutableListOf()

	init {
		repeat (valueGrid.size - 1) {
			val row = mutableListOf<MarchingSquare>()
			repeat (valueGrid[0].size - 1) {
				row.add(MarchingSquare())
			}
			marchingSquares.add(row)
		}
	}

	fun update(valueGrid: MutableList<MutableList<Float>>, seaLevel: Float, scale: Float, position: Vector2) {
		for (y in 0 until marchingSquares.size) {
			for (x in 0 until marchingSquares[0].size) {
				marchingSquares[y][x].bottomLeft = vec3(x * scale + position.x,
					y * scale + position.y,
					valueGrid[y][x] - seaLevel
				)
				marchingSquares[y][x].bottomRight = vec3((x+1) * scale + position.x,
					y * scale + position.y,
					valueGrid[y][x+1] - seaLevel
				)
				marchingSquares[y][x].topLeft = vec3(x * scale + position.x,
					(y+1) * scale + position.y,
					valueGrid[y+1][x] - seaLevel
				)
				marchingSquares[y][x].topRight = vec3((x+1) * scale + position.x,
					(y+1) * scale + position.y,
					valueGrid[y+1][x+1] - seaLevel
				)
				marchingSquares[y][x].scale = scale
				marchingSquares[y][x].update()
			}
		}
	}

	fun drawPolygons(polygonBatch: PolygonSpriteBatch) {
		for (row in marchingSquares) {
			for (marchingSquare in row) {
				marchingSquare.drawPolygons(polygonBatch)
			}
		}
	}

	fun drawSprites(spriteBatch: SpriteBatch) {
		for (row in marchingSquares) {
			for (marchingSquare in row) {
				marchingSquare.drawSprites(spriteBatch)
			}
		}
	}

	fun dispose() {
		for (row in marchingSquares) {
			for (marchingSquare in row) {
				marchingSquare.dispose()
			}
		}
	}
}