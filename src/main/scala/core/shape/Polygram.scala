package core.shape

import scala.collection.mutable.ListBuffer

class Polygram() extends BasicShape {

  def build(): Unit ={
    val sides = <--[Int](sides)
    val angle: Double = Math.floor((1-(2/sides.toFloat))*180 + 0.0001)

    val offset: Double = angle/2

    var vertices = ListBuffer[(Float, Float)]()

    val jump = Math.ceil(sides.toDouble/2.0-1).toInt

    (0 until sides).foreach(n=>{

      val x = sideLength * Math.cos(2*Math.PI*n/sides + Math.toRadians(rotation) + Math.toRadians(offset)) + (sideLength+startX)
      val y = sideLength * Math.sin(2*Math.PI*n/sides + Math.toRadians(rotation) + Math.toRadians(offset)) + (sideLength+startY)

      vertices += ((x.toFloat, y.toFloat))

    })

    var added = 1
    var ind = jump
    beginAt(vertices.head._1, vertices.head._2)

    while(added<sides){

      lineTo(vertices(ind)._1, vertices(ind)._2)

      ind+= jump
      if(ind>=sides) ind -= sides
      added+=1
    }

    close
  }

}

