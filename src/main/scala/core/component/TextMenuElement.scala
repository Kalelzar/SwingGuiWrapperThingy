package core.component

import java.awt.{Color, Font, Point}

class TextMenuElement(x: Int, y: Int, var txt: String="", font: Font = BasicComponent.getDefaultFont)
  extends TextLabel(x, y, "", font) with MenuElement {
  setBorderColor(Color.WHITE)


  override def setText(newText: String): Unit = {
    txt = newText
  }

  override def show(): Unit = super.setText(txt)
  override def hide(): Unit = super.setText("")

  override def whenBoxed(index: Int, menu: Menu): Unit ={
    println(index)
    val mx = menu.getShapeLocation.x
    val my = menu.getShapeLocation.y - menu.getComponentShape.getDimension.height/2 + columnHeight/2 + columnHeight*index
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