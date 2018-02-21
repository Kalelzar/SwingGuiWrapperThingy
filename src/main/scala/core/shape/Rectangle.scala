package core.shape

import java.awt.geom.AffineTransform

class Rectangle extends BasicShape{

  provide[Float]("width", 0f)
  provide[Float]("height", 0f)

  override def build(x: Float, y: Float): Unit = {
    super.build(x, y)

    val w = <--[Float]("width")
    val h = <--[Float]("height")

    beginAt(x + w, y - h ).lineTo(x + w, y + h)
                          .lineTo(x - w, y + h)
                          .lineTo(x - w, y - h)
                          .close
  }

  def expand(byW: Float, byH: Float): Unit ={
    val w = <--[Float]("width")
    val h = <--[Float]("height")
    val x = <--[Float]("x")
    val y = <--[Float]("y")

    val t = new AffineTransform()
    -->[Float]("width", <--[Float]("width")*(w+byW)/w)
    -->[Float]("height", <--[Float]("height")*(h+byH)/h)

    t.translate(x, y)
    t.scale((w+byW)/w, (h+byH)/h)
    t.translate(-x, -y)
    transform(t)
  }

}
