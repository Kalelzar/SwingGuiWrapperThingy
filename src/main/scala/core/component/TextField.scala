package core.component

import java.awt.{Color, Font}

import core.shape.Rectangle


class TextField extends EditableTextView{

  //setComponentFont(font)
  provide("text", "", setText)
  provide("chars", 10)
  provide("font", BasicComponent.getDefaultFont, refreshFontSize)


  override def refreshFontSize(font: Font): Unit = {
    super.refreshFontSize(font)
    width = getAttribute[Int]("chars")*columnWidth
    println(width, columnHeight)
  }

  private var width = getAttribute[Int]("chars")*columnWidth

  setShape( new Rectangle(getAttribute("x"), getAttribute("y"), width, columnHeight))
  setFill(true)
  setFillColor(Color.WHITE)
  //offsetTextY(-columnHeight/4)

  override def onCharacterTyped(change: Int): Unit = {
    println(getAttribute("chars"), columnWidth, width)
  }


}
