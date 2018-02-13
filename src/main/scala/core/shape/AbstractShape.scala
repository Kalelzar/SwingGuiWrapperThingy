package core.shape

import java.awt.geom.{GeneralPath, Rectangle2D}
import java.awt.Graphics2D

import core.shape.helper.Point
import core.util.AttributeRegister

trait AbstractShape extends AttributeRegister{
  def draw(graphics2D: Graphics2D)
  def getCenter: Point
  def getBaseShapes: Seq[GeneralPath]
  def getBounds: Rectangle2D
  def build: Unit
}
