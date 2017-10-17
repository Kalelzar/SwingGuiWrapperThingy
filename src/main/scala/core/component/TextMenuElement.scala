package core.component

import java.awt.{Font, Point}

class TextMenuElement(x: Int, y: Int, text: String="", font: Font = BasicComponent.getDefaultFont)
  extends TextLabel(x, y, text, font) with MenuElement {

  override def whenBoxed(index: Int, menu: Menu): Unit ={
    println(index)
    val mx = menu.getShapeLocation.x
    val my = menu.getShapeLocation.y - menu.getComponentShape.getDimension.height/2 + columnHeight/2 + columnHeight*index
    moveTo(new Point(mx, my))
    println(getShapeLocation)
  }

}


object TextMenuElement{

  def build(height: Int, index: Int, menu: Menu, text: String="", font: Font = BasicComponent.getDefaultFont)
    : TextMenuElement ={

    val pos = menu.getShapeLocation

    new TextMenuElement(pos.x, pos.y + height*index, text, font)

  }
}