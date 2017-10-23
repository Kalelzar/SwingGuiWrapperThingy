package core.component

import java.awt.{Color, Font}

import core.shape.Rectangle


class TextField extends EditableTextView{

  provide("chars", 10)
  provide("text", "", onSetText)
  provide("font", BasicComponent.getDefaultFont, refreshFontSize)

  refreshFontSize(BasicComponent.getDefaultFont)

  override def refreshFontSize(font: Font): Unit = {
    super.refreshFontSize(font)
    width = ?|[Int]("chars") * ?|[Int]("columnWidth")
    +|("shape", new Rectangle( ?|("x"), ?|("y"), width, ?|[Int]("columnHeight")))
    +|("fill", true)
    +|("fillColor", Color.WHITE)
  }

  private var width = ?|[Int]("chars") * ?|[Int]("columnWidth")


  override def onCharacterTyped(change: Int): Unit = {
    println(?|("chars"), ?|[Int]("columnWidth"), width)
  }


}
