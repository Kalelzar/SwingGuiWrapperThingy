package core.shape

import java.awt.Dimension

import core.shape.deprecated.Shape

class Rectangle(var centerX: Float, var centerY: Float, var width: Float, var height: Float, rotation: Float = 0 ) extends Shape{
  private var offsetX = 0f
  private var offsetY = 0f
  override def getDimension = new Dimension(width.toInt, height.toInt)

  build()

  def offsetShapeY(by: Float): Unit ={
    offsetY += by
  }


  override def centerOnX: Float = width/2
  override def centerOnY: Float = -height/2

  def build(): Unit ={
    addVertex(centerX-width/2, centerY-height/2)
    addVertex(centerX+width/2, centerY-height/2)
    addVertex(centerX+width/2, centerY+height/2)
    addVertex(centerX-width/2, centerY+height/2)
    pack()
    println(centerX, width, height)
  }

  def expand(byW: Float, byH: Float): Unit ={
    width += byW
    height += byH
    rebuild()
    build()
  }

  def expandInPlace(byW: Float, byH: Float): Unit ={
    if(height+byH<0 || width+byW<0)
      return

    offsetX += byW/2
    offsetY += byH/2
    expand(byW, byH)
  }


}
