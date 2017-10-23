package core.shape
import java.awt.Dimension

import core.shape.Helper.AngleHelper


class RegularPolygon(startX: Float, startY: Float, sides: Int, sideLength: Int, rotation: Double = 0 ) extends Shape {

  private val angle: Double = Math.floor((1-(2/sides.toFloat))*180 + 0.0001)
  private val offset: Double = angle/2

  (0 until sides).foreach(n=>{

    val x = sideLength * Math.cos(2*Math.PI*n/sides + Math.toRadians(rotation) + Math.toRadians(offset)) + (sideLength+startX)
    val y = sideLength * Math.sin(2*Math.PI*n/sides + Math.toRadians(rotation) + Math.toRadians(offset)) + (sideLength+startY)

    addVertex(x.toFloat, y.toFloat)


  })

  pack()

  override def centerOnX: Float = ???
  override def centerOnY: Float = ???

  override def getDimension: Dimension = new Dimension(sideLength, sideLength)
}
