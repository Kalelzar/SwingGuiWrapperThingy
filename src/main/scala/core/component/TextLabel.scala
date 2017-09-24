package core.component

import java.awt.{Color, Font}

import core.shape.Rectangle

class TextLabel(x: Float, y: Float, text: String="", font: Font) extends TextView{
  setComponentFont(font)
  private var width = text.length*columnWidth
  private var height = (text.count(_=='\n')+1)*columnHeight
  setShape( new Rectangle(x, y, width, height))
  setText(text)


  override def setText(newText: String): Unit = {
    super.setText(newText)
    width = getText.length*columnWidth
    height = (text.count(_=='\n')+1)*columnHeight
    shape.asInstanceOf[Rectangle].expandInPlace(width-shape.getDimension.width,
      height-shape.getDimension.height)
    setBorderColor(Window.getMainWindow.getBackground)
  }


}
