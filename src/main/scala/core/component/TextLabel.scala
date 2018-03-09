package core.component

import java.awt.{Color, Font}

import core.shape.{AbstractShape, Rectangle}

class TextLabel extends TextView{
  //setComponentFont(font)
  provide("text", "", onSetText)

  private var width: Float = metrics.stringWidth(<--[String]("text"))
  private var height: Float = (<--[String]("text").count(_=='\n')+1)* metrics.getHeight

  onSetText("")



  override def onSetText(newText: String): Unit = {
    refreshFontSize( <--("font"))
    width = metrics.stringWidth(<--[String]("text"))
    height = (<--[String]("text").count(_=='\n')+1) * metrics.getHeight

    -->[AbstractShape]("shape", AbstractShape.createShape(new Rectangle)
      .withAttribute("width", width)
      .withAttribute("height", height)
      .withAttribute("x", <--[Float]("x"))
      .withAttribute("y", <--[Float]("y"))
      .build)

    -->("borderColor", Window.getMainWindow.getBackground)
    #::
  }

  override def toString: String = {
    val s = StringBuilder.newBuilder
    s .append("TextLabel[")
      .append("text=")
      .append(<--[String]("text"))
      .append(" id=")
      .append(<--[Int]("ID"))
      .append("]")
    s.mkString
  }

}
