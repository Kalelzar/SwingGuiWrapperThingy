package core.component

import java.awt.{Color, Font, FontMetrics, Graphics2D}

import core.shape.Shape
import sun.swing.SwingUtilities2

trait TextView extends BasicComponent{

  provide[Int]("columnWidth", 0)
  provide[Int]("columnHeight", 0)
  provide[String]("text", "")
  provide[Float]("offsetY", 0f)
  provide[Color]("textColor", Color.BLACK)

  def refreshFontSize(font: Font): Unit = {
    val metrics: FontMetrics = SwingUtilities2.getFontMetrics(?|[VisualPanel]("visualPanel"), ?|[Font]("font"))
    +|("columnWidth", metrics.charWidth('m'))
    +|("columnHeight", metrics.getHeight)
    println(s"Refreshed to + $font")
  }

  protected def onSetText(newText: String): Unit = refreshFontSize(?|[Font]("font"))

  override def draw(graphics2D: Graphics2D): Unit = {
    super.draw(graphics2D)
    graphics2D.setColor( ?|("textColor") )
    graphics2D.setFont(getAttribute("font"))
    graphics2D.drawString(?|[String]("text"),
      getShapeLocation.x- ?|[Shape]("shape").getDimension.width/2,
      getShapeLocation.y+ ?|[Int]("columnHeight")/4)
    graphics2D.setFont(Window.getMainWindow.getFont)
  }

  override def toString: String = ?|[String]("text")
}
