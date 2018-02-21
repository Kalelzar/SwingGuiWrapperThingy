package core.shape

import java.awt.geom.{AffineTransform, GeneralPath, Rectangle2D}
import java.awt.Graphics2D

import core.component.utils.ShapeBuilder
import core.util.{AttributeRegister, Point}

trait AbstractShape extends AttributeRegister{

  provide[Float]("x", 0f)
  provide[Float]("y", 0f)

  def clear(): Unit
  def transform(transform: AffineTransform): Unit
  def draw(graphics2D: Graphics2D)
  def getCenter: Point
  def getBaseShapes: Seq[GeneralPath]
  def getBounds: Rectangle2D
  def build(x: Float, y: Float): Unit = {
    -->[Float]("x", x)
    -->[Float]("y", y)
  }
}

object AbstractShape{
  def createShape[T <: AbstractShape](abstractShape: T): ShapeBuilder[T] = new ShapeBuilder[T](abstractShape)
}
