package core.animation

import java.awt.geom.AffineTransform

import core.shape.AbstractShape

import scala.collection.mutable.ListBuffer

object Transform {

  def apply(shape: AbstractShape): Transform = new Transform(shape)

}

final class Transform(shape: AbstractShape){

  private val transform = new AffineTransform()
  private val x = shape.<--[Float]("x")
  private val y = shape.<--[Float]("x")

  def translate(mx: Float, my: Float): Transform ={
    transform.translate(mx, my)
    this
  }

  def scaleFromCenter(sx: Float, sy: Float): Transform = {
    transform.translate(x, y)
    transform.scale(sx, sy)
    transform.translate(-x, -y)
    this
  }

  def scale(sx: Float, sy: Float): Transform = {
    transform.scale(sx, sy)
    this
  }

  def rotate(angle: Float): Transform = {
    transform.rotate(angle)
    TransformOperation(RotationOp(angle))
    this
  }

  def rotateInPlace(angle: Float): Transform = {
    transform.rotate(angle, shape.<--[Float]("x"), shape.<--[Float]("y"))
    TransformOperation(RotationOp(angle))
    this
  }


  def apply(): Unit = {
    shape.transform(transform)
    shape.-->("width", shape.getBounds.getWidth.toFloat)
    shape.-->("height", shape.getBounds.getHeight.toFloat)
    shape.-->("x", shape.getBounds.getCenterX.toFloat)
    shape.-->("y", shape.getBounds.getCenterY.toFloat)
    TransformOperation.transform(shape)
  }

}

private object TransformOperation{
  private val operations = ListBuffer[RotationOp]()
  def apply(transformOperation: RotationOp): Unit = operations += transformOperation
  def transform(abstractShape: AbstractShape): Unit = {
    operations.reverseIterator.foreach(applyOperation(_, abstractShape))
  }
  def applyOperation(transformOperation: RotationOp, shape: AbstractShape): Unit ={
    val rotation = shape.<--[Float]("rotation")

    shape.-->[Float]("rotation", rotation + transformOperation.angle)
  }
}

private final case class RotationOp(angle: Float)