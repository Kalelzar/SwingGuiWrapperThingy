package core.shape
import scala.collection.mutable.ListBuffer


class RegularPolygon extends BasicShape {

  provide[Int]("sides", 4)
  provide[Float]("sideLength", 100f)

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



    beginAt(vertices.head._1, vertices.head._2)
    vertices = vertices.drop(1)
    vertices.foreach(x=>lineTo(x._1, x._2))

    close
    restore
  }

}
