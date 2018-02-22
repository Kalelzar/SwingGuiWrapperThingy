package core.shape

import java.awt.geom.AffineTransform

import core.animation.Transform

class Rectangle extends BasicShape{



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

    -->[Float]("width", <--[Float]("width")*(w+byW)/w)
    -->[Float]("height", <--[Float]("height")*(h+byH)/h)

    Transform(this).scaleFromCenter((w+byW)/w, (h+byH)/h)()
  }

}
