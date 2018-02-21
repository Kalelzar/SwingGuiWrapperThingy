package core.layout

import java.awt.Graphics2D

import core.component.{BasicComponent, VisualPanel}
import core.layout.utils.Locator
import core.util.Point

import scala.collection.mutable

/**
  * The LayoutPane is used in Layers. Separate Layers are drawn on separate LayoutPanes.
  * Furthermore the Layouts are enforced on a Layer basis, yet they are aware of lower Layers
  * with the purpose of eliminating overlap.
  */
class LayoutPane(layerID: Int, visualPanel: VisualPanel) {

  def getAt(point: Point): Option[BasicComponent] = {
    getLayerElements.find(_.isInside(point))
  }

  setLayout()

  private var layout = visualPanel.getVisualPanelLayout
  private var layerElements = new mutable.ListBuffer[BasicComponent]()

  def setLayerElements(layerElements: mutable.ListBuffer[BasicComponent]): Unit =
    this.layerElements = layerElements

  def addLayerElement(layerElement: BasicComponent, locator: Locator): Unit ={
    val valid = locator.validate(visualPanel, layerID)
    val point = locator.getPoint
    if(valid){
      layerElement.moveTo(point)
      layerElements += layerElement
    }
  }

  def getLayerElements:mutable.ListBuffer[BasicComponent]  = layerElements

  protected def setLayout(): Unit = this.layout = visualPanel.getVisualPanelLayout

  def getLayout:Layout  = layout

  def update(graphics2D: Graphics2D): Unit ={
    //println("LayoutPane update start")
    draw(graphics2D)
    //println("LayoutPane update end")
  }

  def draw(g2d: Graphics2D): Unit = {

    //println("LayoutPane draw start")
    layerElements.foreach(_.draw(g2d))
    //println("LayoutPane draw end")

  }
}
