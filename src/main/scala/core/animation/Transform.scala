package core.animation

import java.awt.geom.AffineTransform

import core.shape.AbstractShape

object Transform {

  def apply(shape: AbstractShape): Transform = new Transform(shape)

}

private final class Transform(shape: AbstractShape){

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
    this
  }

  def rotate(velX: Float, velY: Float): Transform = {
    transform.rotate(velX, velY)
    this
  }

  def apply(): Unit = {
    shape.transform(transform)
  }

}

private sealed trait TransformOperation

private final case class ScaleTransformOp(sx: Float, sy: Float) extends TransformOperation
private final case class TranslationOp(tx: Float, ty: Float) extends TransformOperation
private final case class RotationOp(angle: Float) extends TransformOperation