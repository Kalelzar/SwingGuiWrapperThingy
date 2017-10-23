package core.component

import java.awt.{Color, Font}

import core.shape.Rectangle

class TextLabel extends TextView{
  //setComponentFont(font)
  provide("text", "", setText)

  private var width = getAttribute[String]("text").length*columnWidth
  private var height = (getAttribute[String]("text").count(_=='\n')+1)*columnHeight
  setShape(new Rectangle(getAttribute[Float]("x"), getAttribute[Float]("y"), width, height))



  override def setText(newText: String): Unit = {

    super.setText(newText)
    width = getAttribute[String]("text").length*columnWidth
    height = (getAttribute[String]("text").count(_=='\n')+1)*columnHeight
    setShape(new Rectangle(getAttribute[Float]("x"), getAttribute[Float]("y"), width, height))
    setBorderColor(Window.getMainWindow.getBackground)
  }


}
