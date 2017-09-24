package core.component

import java.awt.{Color, Font, FontMetrics}
import javax.swing.JFrame

import core.shape.{Rectangle, Square}
import sun.swing.SwingUtilities2


class TextField(x: Float, y: Float, chars: Int, font: Font) extends EditableTextView{

  setComponentFont(font)

  override def refreshFontSize(): Unit = {
    super.refreshFontSize()
    width = chars*columnWidth
  }

  private var width = chars*columnWidth

  setShape( new Rectangle(x, y, width, columnHeight))
  setFill(true)
  setFillColor(Color.WHITE)
  //offsetTextY(-columnHeight/4)

  override def onCharacterTyped(change: Int): Unit = {
    println(chars, columnWidth, width)
  }


}
