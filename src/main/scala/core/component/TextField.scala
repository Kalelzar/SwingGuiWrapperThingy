package core.component

import java.awt.{Color, Font}

import core.shape.{AbstractShape, Rectangle}


class TextField extends EditableTextView{

  provide[String]("text", "", onSetText(_))
  provide[Font]("font", BasicComponent.getDefaultFont, refreshFontSize(_))


  refreshFontSize(BasicComponent.getDefaultFont)

  override def refreshFontSize(font: Font): Unit = {
    super.refreshFontSize(font)
    width = <--[Int]("chars") * columnWidth
    -->("shape", AbstractShape.createShape(new Rectangle)
      .withAttribute("width", width)
      .withAttribute("height", columnHeight)
      .withAttribute("x", <--[Float]("x"))
      .withAttribute("y", <--[Float]("y"))
      .build)
    -->("fill", true)
    -->("fillColor", Color.WHITE)
  }




  private var width = <--[Int]("chars") * columnWidth




}
