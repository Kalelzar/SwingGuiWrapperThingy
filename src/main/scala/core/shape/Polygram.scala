package core.shape

import java.awt.Dimension

import scala.collection.mutable.ListBuffer
import scala.math.Numeric.FloatAsIfIntegral

class Polygram(startX: Float, startY: Float, sides: Int, sideLength: Int, rotation: Double = 0 ) extends Shape {



  override def centerOnX: Float = ???
  override def centerOnY: Float = ???

  private val angle: Double = Math.floor((1-(2/sides.toFloat))*180 + 0.0001)

  private val offset: Double = angle/2

  private var vertices = ListBuffer[(Float, Float)]()

  private val jump = Math.ceil(sides.toDouble/2.0-1).toInt

  (0 until sides).foreach(n=>{

    val x = sideLength * Math.cos(2*Math.PI*n/sides + Math.toRadians(rotation) + Math.toRadians(offset)) + (sideLength+startX)
    val y = sideLength * Math.sin(2*Math.PI*n/sides + Math.toRadians(rotation) + Math.toRadians(offset)) + (sideLength+startY)

    vertices += ((x.toFloat, y.toFloat))

  })

  private var added = 0
  private var ind = 0

  while(added<sides){

    addVertex(vertices(ind)._1, vertices(ind)._2)

    ind+= jump
    if(ind>=sides) ind -= sides
    added+=1
  }

  pack()

  override def getDimension: Dimension = new Dimension(sideLength, sideLength)
}

