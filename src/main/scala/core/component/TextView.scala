package core.component

import java.awt.{Color, Font, FontMetrics, Graphics2D}

import sun.swing.SwingUtilities2

trait TextView extends BasicComponent{
  protected var columnWidth = 0
  protected var columnHeight = 0

  def refreshFontSize(font: Font): Unit = {
    val metrics: FontMetrics = SwingUtilities2.getFontMetrics(getVisualPanel,getAttribute[Font]("font"))
    columnWidth=metrics.charWidth('m')
    columnHeight=metrics.getHeight
    println(s"Refreshed to + $font")
  }

  private var text : String = ""
  private var offsetY = 0f
  private var textColor = Color.BLACK

  def setText(newText: String): Unit = text = newText
  def getText: String = text
  def setTextColor(newTextColor: Color): Unit = textColor = newTextColor
  def getTextColor: Color = textColor

  def offsetTextY(by: Float): Unit = offsetY += by

  override def draw(graphics2D: Graphics2D): Unit = {
    super.draw(graphics2D)
    graphics2D.setColor(textColor)
    graphics2D.setFont(getAttribute("font"))
    graphics2D.drawString(text,
      getShapeLocation.x-shape.getDimension.width/2,
      getShapeLocation.y+columnHeight/4)
    graphics2D.setFont(Window.getMainWindow.getFont)
  }

  override def toString = getText
}
