package core.component

import java.awt.{Color, Font}

import core.shape.{AbstractShape, Rectangle}


class TextField extends EditableTextView{

  //provide[String]("text", "", onSetText(_))
  provide[Font]("font", BasicComponent.getDefaultFont, refreshFontSize(_))
  private var submitCallback : String => Unit = _

  refreshFontSize(BasicComponent.getDefaultFont)

  override def refreshFontSize(font: Font): Unit = {
    super.refreshFontSize(font)
    width = metrics.stringWidth("m" * <--[Int]("chars"))
    val rect = metrics.getStringBounds("m" * <--[Int]("chars"), Window.getMainWindow.getGraphics)
    -->[AbstractShape]("shape", AbstractShape.createShape(new Rectangle)
      .withAttribute("width", rect.getWidth.toFloat/2)
      .withAttribute("height", rect.getHeight.toFloat)
      .withAttribute("x", <--[Float]("x"))
      .withAttribute("y", <--[Float]("y"))
      .build)
    -->("fill", true)
    -->("fillColor", Color.WHITE)
  }

  def onSubmit(f: String => Unit): Unit ={
    submitCallback = f
  }


  override def submit: Unit = {
    submitCallback(<--[String]("text"))
  }

  private var width: Float = metrics.stringWidth("m" * <--[Int]("chars"))

  override def toString: String = {
    StringBuilder.newBuilder
      .append("TextField[")
      .append("text=")
      .append(<--[String]("text"))
      .append(" id=")
      .append(<--[Int]("ID"))
      .append("]")
      .mkString
  }
}
