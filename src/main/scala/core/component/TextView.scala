package core.component

import java.awt.geom.AffineTransform
import java.awt.{Color, Font, FontMetrics, Graphics2D}

import core.shape.AbstractShape
import sun.swing.SwingUtilities2

trait TextView extends BasicComponent{

  protected var columnWidth = 0
  protected var columnHeight = 0
  provide[String]("text", "")
  provide[Float]("offsetY", 0f)
  provide[Color]("textColor", Color.BLACK)

  def refreshFontSize(font: Font): Unit = {
    val metrics: FontMetrics = SwingUtilities2.getFontMetrics(<--[VisualPanel]("visualPanel"), <--[Font]("font"))
    columnWidth = metrics.charWidth('m')
    columnHeight = metrics.getHeight
    println(s"Refreshed to + $font")
  }

  protected def onSetText(newText: String): Unit = refreshFontSize(<--[Font]("font"))

  override def draw(graphics2D: Graphics2D): Unit = {
    super.draw(graphics2D)
    graphics2D.setColor( <--("textColor") )
    graphics2D.setFont(getAttribute("font"))

    val rot = <--[AbstractShape]("shape").<--[Float]("rotation")

    if(rot == 0)
      graphics2D.drawString(<--[String]("text"),
        getShapeLocation.x- <--[AbstractShape]("shape").getBounds.getWidth.toFloat/2,
        getShapeLocation.y+ columnHeight/4)
    else{
      val g2d = graphics2D.create().asInstanceOf[Graphics2D]
      val ct = g2d.getTransform
      ct.rotate(rot, getShapeLocation.x, getShapeLocation.y)
      g2d.setTransform(ct)

      g2d.drawString(<--[String]("text"),
        getShapeLocation.x- <--[AbstractShape]("shape").getBounds.getWidth.toFloat/2,
        getShapeLocation.y+ columnHeight/4)
      g2d.dispose()
    }


    graphics2D.setFont(Window.getMainWindow.getFont)
  }

  override def toString: String = <--[String]("text")
}
