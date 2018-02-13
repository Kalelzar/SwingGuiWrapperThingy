package core.layout

import java.awt.Graphics2D

import core.shape.helper.Point
import core.component.BasicComponent

class Layer(index: Int)(implicit layout: Layout) {

  def getAt(point: Point): Option[BasicComponent] = {
    getLayoutPane.getAt(point)
  }


  private val layoutPane: LayoutPane = new LayoutPane(index, layout.getAdministeredVisualPanel)


  def getLayoutPane: LayoutPane = layoutPane
  def getIndex: Int = index

  def update(graphics2D: Graphics2D): Unit ={
    //println("Layer update start")
    layoutPane.update(graphics2D)
    //println("Layer update end")
  }

}
