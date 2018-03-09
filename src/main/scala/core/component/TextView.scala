package core.component

import java.awt.geom.AffineTransform
import java.awt.{Color, Font, FontMetrics, Graphics2D}

import core.shape.AbstractShape
import sun.swing.SwingUtilities2

trait TextView extends BasicComponent{


  protected var metrics: FontMetrics = _
  provide[String]("text", "")
  provide[Float]("offsetY", 0f)
  provide[Color]("textColor", Color.BLACK)

  def refreshFontSize(font: Font): Unit = {
    val vp = <--[VisualPanel]("visualPanel")
    val font = <--[Font]("font")
    val text = <--[String]("text")
    metrics = SwingUtilities2.getFontMetrics(vp, font)
  }

  protected def onSetText(newText: String): Unit = refreshFontSize(<--[Font]("font"))

  override def draw(graphics2D: Graphics2D): Unit = {
    super.draw(graphics2D)
    graphics2D.setColor( <--("textColor") )
    graphics2D.setFont(getAttribute("font"))

    val text = <--[String]("text")
    val rot = <--[AbstractShape]("shape").<--[Float]("rotation")

    if(metrics != null){
      val rect =  metrics.getStringBounds(text, graphics2D)
      if(rot == 0) {
        println(s"$text," +
          s"${(getShapeLocation.x - <--[AbstractShape]("shape").getBounds.getWidth.toFloat / 4).toInt}," +
          s" ${(getShapeLocation.y + rect.getHeight / 4).toInt}")
        graphics2D.drawString(text,
          (getShapeLocation.x - <--[AbstractShape]("shape").getBounds.getWidth.toFloat / 4).toInt,
          (getShapeLocation.y + rect.getHeight / 4).toInt)
      } else {
        val g2d = graphics2D.create().asInstanceOf[Graphics2D]
        val ct = g2d.getTransform
        ct.rotate(rot, getShapeLocation.x, getShapeLocation.y)
        g2d.setTransform(ct)

        g2d.drawString(text,
          (getShapeLocation.x- <--[AbstractShape]("shape").getBounds.getWidth.toFloat/2).toInt,
          (getShapeLocation.y+ rect.getHeight/4).toInt)
        g2d.dispose()
      }
    }else{
      throw new NullPointerException(s"Font metrics aren't initialized in $this")
    }




    graphics2D.setFont(Window.getMainWindow.getFont)
  }

  override def toString: String = <--[String]("text")
}
