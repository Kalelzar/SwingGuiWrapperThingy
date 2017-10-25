package core.component

import java.awt.{Color, Font, Point}

import core.shape.Shape

class TextMenuElement(x: Int, y: Int, var txt: String="", font: Font = BasicComponent.getDefaultFont)
  extends TextLabel with MenuElement {
  -->("borderColor", Color.WHITE)


  override def onSetText(newText: String): Unit = {
    txt = newText
  }

  override def show(): Unit = -->[String]("text", txt)
  override def hide(): Unit = -->[String]("text", "")

  override def whenBoxed(index: Int, menu: Menu): Unit ={
    println(index)
    val mx = menu.getShapeLocation.x
    val my = menu.getShapeLocation.y - menu.<--[Shape]("shape").getDimension.height/2 + <--[Int]("columnHeight")/2 + <--[Int]("columnHeight")*index
    moveTo(new Point(mx, my))
    println(getShapeLocation)
    show()
  }

}


object TextMenuElement{

  def build(height: Int, index: Int, menu: Menu, text: String="", font: Font = BasicComponent.getDefaultFont)
    : TextMenuElement ={

    val pos = menu.getShapeLocation

    new TextMenuElement(pos.x, pos.y + height*index, text, font)

  }
}