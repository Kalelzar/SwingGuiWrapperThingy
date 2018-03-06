package core.component

import java.awt.{Color, Font}

import core.shape.{AbstractShape, Rectangle}

class TextLabel extends TextView{
  //setComponentFont(font)
  provide("text", "", onSetText)

  private var width: Float = <--[String]("text").length * columnWidth
  private var height: Float = (<--[String]("text").count(_=='\n')+1)* columnHeight

  onSetText("")



  override def onSetText(newText: String): Unit = {
    refreshFontSize( <--("font"))
    width = <--[String]("text").length * columnWidth
    height = (<--[String]("text").count(_=='\n')+1) * columnHeight

    -->[AbstractShape]("shape", AbstractShape.createShape(new Rectangle)
      .withAttribute("width", width)
      .withAttribute("height", height)
      .withAttribute("x", <--[Float]("x"))
      .withAttribute("y", <--[Float]("y"))
      .build)

    -->("borderColor", Window.getMainWindow.getBackground)
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
