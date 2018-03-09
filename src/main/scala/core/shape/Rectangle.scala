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

    val sx = (w+byW)/w
    val sy = (h+byH)/h

    Transform(this).scaleFromCenter(sx, sy)()
  }

  override def toString: String = {
    StringBuilder.newBuilder
      .append("Rectangle[")
      .append("x=")
      .append(<--[Float]("x"))
      .append(" y=")
      .append(<--[Float]("y"))
      .append(" w=")
      .append(<--[Float]("width"))
      .append(" h=")
      .append(<--[Float]("height"))
      .append("]")
      .mkString
  }
}
