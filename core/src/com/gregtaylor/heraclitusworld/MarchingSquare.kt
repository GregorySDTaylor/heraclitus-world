package com.gregtaylor.heraclitusworld

import com.badlogic.gdx.graphics.Color.*
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.math.Vector3
import ktx.math.vec3
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.*
import com.badlogic.gdx.math.EarClippingTriangulator
import ktx.graphics.center


class MarchingSquare(
	var bottomLeft: Vector3 = vec3(0f, 0f, 0f),
	var bottomRight: Vector3 = vec3(0f, 0f, 0f),
	var topLeft: Vector3 = vec3(0f, 0f, 0f),
	var topRight: Vector3 = vec3(0f, 0f, 0f),
	var scale: Float = 0f
) {

	private val whitePixMap = Pixmap(1, 1, Pixmap.Format.RGBA8888)
	private val whiteTexture: Texture
	private val whiteTextureRegion: TextureRegion

	init {
		whitePixMap.setColor(WHITE)
		whitePixMap.fill()
		whiteTexture = Texture(whitePixMap)
		whiteTextureRegion = TextureRegion(whiteTexture)
	}

	private val triangulator = EarClippingTriangulator()
	private var vertices = floatArrayOf()
	private var textureRegion = whiteTextureRegion
	private val font = BitmapFont()

	fun update() {
		val half = scale/2
		var cellType = 0
		if (bottomLeft.z > 0) {
			cellType += 1
		}
		if (bottomRight.z > 0) {
			cellType += 2
		}
		if (topRight.z > 0) {
			cellType += 4
		}
		if (topLeft.z > 0) {
			cellType += 8
		}
		when (cellType) {
			0 -> {
				vertices = floatArrayOf()
			}
			1 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y,
					bottomLeft.x + half, bottomLeft.y,
					bottomLeft.x, bottomLeft.y + half)
			}
			2 -> {
				vertices = floatArrayOf(
					bottomRight.x, bottomRight.y,
					bottomRight.x - half, bottomRight.y,
					bottomRight.x, bottomRight.y + half)
			}
			3 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y,
					bottomRight.x, bottomRight.y,
					bottomRight.x, bottomRight.y+ half,
					bottomLeft.x, bottomLeft.y+ half)
			}
			4 -> {
				vertices = floatArrayOf(
					topRight.x, topRight.y,
					topRight.x - half, topRight.y,
					topRight.x, topRight.y - half)
			}
			5 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y,
					bottomLeft.x + half, bottomLeft.y,
					topRight.x, topRight.y - half,
					topRight.x, topRight.y,
					topRight.x - half, topRight.y,
					bottomLeft.x, bottomLeft.y + half)
			}
			6 -> {
				vertices = floatArrayOf(
					topRight.x - half, topRight.y,
					topRight.x, topRight.y,
					bottomRight.x, bottomRight.y,
					bottomRight.x - half, bottomRight.y)
			}
			7 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y,
					bottomRight.x, bottomRight.y,
					topRight.x, topRight.y,
					topLeft.x + half, topLeft.y,
					topLeft.x, topLeft.y - half)
			}
			8 -> {
				vertices = floatArrayOf(
					topLeft.x, topLeft.y,
					topLeft.x + half, topLeft.y,
					topLeft.x, topLeft.y - half)
			}
			9 -> {
				vertices = floatArrayOf(
					topLeft.x, topLeft.y,
					topLeft.x + half, topLeft.y,
					bottomLeft.x + half, bottomLeft.y,
					bottomLeft.x, bottomLeft.y)
			}
			10 -> {
				vertices = floatArrayOf(
					topLeft.x, topLeft.y,
					topLeft.x + half, topLeft.y,
					bottomRight.x, bottomRight.y + half,
					bottomRight.x, bottomRight.y,
					bottomRight.x - half, bottomRight.y,
					topLeft.x, topLeft.y - half)
			}
			11 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y,
					bottomRight.x, bottomRight.y,
					topRight.x, topRight.y - half,
					topRight.x - half, topRight.y,
					topLeft.x, topLeft.y)
			}
			12 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y + half,
					bottomRight.x, bottomRight.y + half,
					topRight.x, topRight.y,
					topLeft.x, topLeft.y)
			}
			13 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y,
					bottomRight.x - half, bottomRight.y,
					bottomRight.x, bottomRight.y + half,
					topRight.x, topRight.y,
					topLeft.x, topLeft.y)
			}
			14 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y + half,
					bottomLeft.x + half, bottomLeft.y,
					bottomRight.x, bottomRight.y,
					topRight.x, topRight.y,
					topLeft.x, topLeft.y)
			}
			15 -> {
				vertices = floatArrayOf(
					bottomLeft.x, bottomLeft.y,
					bottomRight.x, bottomRight.y,
					topRight.x, topRight.y,
					topLeft.x, topLeft.y)
			}
		}
	}

	fun drawPolygons(polygonBatch: PolygonSpriteBatch) {
		val triangles = triangulator.computeTriangles(vertices).toArray()
		val polygonRegion = PolygonRegion(textureRegion, vertices, triangles)
		val polygonSprite = PolygonSprite(polygonRegion)
		polygonSprite.setColor(0f,1f,0f,1f)
		polygonSprite.draw(polygonBatch)
	}

	fun drawSprites(spriteBatch: SpriteBatch) {
		font.setColor(0.7f, 0.7f, 0.7f, 1f)
		val blPointText = bottomLeft.z.toInt().toString()
		val blTextPosition = font.center(blPointText, 0f, 0f, bottomLeft.x, bottomLeft.y)
		font.draw(spriteBatch, blPointText, blTextPosition.x, blTextPosition.y)
		val brPointText = bottomRight.z.toInt().toString()
		val brTextPosition = font.center(brPointText, 0f, 0f, bottomRight.x, bottomRight.y)
		font.draw(spriteBatch, brPointText, brTextPosition.x, brTextPosition.y)
		val trPointText = topRight.z.toInt().toString()
		val trTextPosition = font.center(trPointText, 0f, 0f, topRight.x, topRight.y)
		font.draw(spriteBatch, trPointText, trTextPosition.x, trTextPosition.y)
		val tlPointText = topLeft.z.toInt().toString()
		val tlTextPosition = font.center(tlPointText, 0f, 0f, topLeft.x, topLeft.y)
		font.draw(spriteBatch, tlPointText, tlTextPosition.x, tlTextPosition.y)
	}

	fun dispose() {
		whitePixMap.dispose()
		whiteTexture.dispose()
		font.dispose()
	}
}