package core.shape

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Polygram extends BasicShape {

  provide[Int]("sides", 4)
  provide[Float]("sideLength", 100f)
  provide[Float]("rotation", 0f)

  override def build(x: Float, y: Float): Unit ={
    super.build(x, y)
    val sides = <--[Int]("sides")
    val angle: Double = Math.floor((1-(2/sides.toFloat))*180 + 0.0001)

    val offset: Double = angle/2

    var vertices = ListBuffer[(Float, Float)]()

    val jump = Math.ceil(sides.toDouble/2.0-1).toInt

    (0 until sides).foreach(n=>{

      val xx = <--[Float]("sideLength") * Math.cos(2*Math.PI*n/sides + Math.toRadians(<--[Float]("rotation")) + Math.toRadians(offset))+ (x)
      val yy = <--[Float]("sideLength") * Math.sin(2*Math.PI*n/sides + Math.toRadians(<--[Float]("rotation")) + Math.toRadians(offset))+ (y)
      vertices += ((xx.toFloat, yy.toFloat))

    })

    var added = 1
    var ind = jump

    val passed = new mutable.ListBuffer[(Float, Float)]()
    beginAt(vertices.head._1, vertices.head._2)
    passed += ((vertices.head._1, vertices.head._2))

    while(added<sides){
      if(passed.contains((vertices(ind)._1, vertices(ind)._2))){
        close
        store
        ind+=1
        beginAt(vertices(ind)._1, vertices(ind)._2)
        passed += ((vertices(ind)._1, vertices(ind)._2))
        ind+=jump
      }else{
        lineTo(vertices(ind)._1, vertices(ind)._2)

        passed += ((vertices(ind)._1, vertices(ind)._2))

        ind+= jump
      }
      if(ind>=sides) ind -= sides
      added+=1
    }
    close
    restore
  }

}

