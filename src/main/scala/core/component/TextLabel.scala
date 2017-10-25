package core.component

import java.awt.{Color, Font}

import core.shape.Rectangle

class TextLabel extends TextView{
  //setComponentFont(font)
  provide("text", "", onSetText)

  private var width = <--[String]("text").length * <--[Int]("columnWidth")
  private var height = (<--[String]("text").count(_=='\n')+1)* <--[Int]("columnHeight")
  -->("shape", new Rectangle(<--[Float]("x"), <--[Float]("y"), width, height))



  override def onSetText(newText: String): Unit = {
    refreshFontSize( <--("font"))
    width = <--[String]("text").length * <--[Int]("columnWidth")
    height = (<--[String]("text").count(_=='\n')+1) * <--[Int]("columnHeight")
    -->("shape", new Rectangle( <--[Float]("x"), <--[Float]("y"), width, height))
    -->("borderColor", Window.getMainWindow.getBackground)
  }


}
