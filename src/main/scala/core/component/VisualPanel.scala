package core.component

import java.awt.{Dimension, Graphics, Graphics2D}
import javax.swing.JPanel

import core.layout.utils.{AbsoluteLocator, Locator}
import core.layout.{AbsoluteLayout, Layer, Layout}
import core.shape.Shape

import scala.collection.mutable.ListBuffer

/**
  * Created by kalelzar on 6/27/17.
  */
class VisualPanel(dimension: Dimension, name: String)(implicit window: Window) extends JPanel{

  private var vpLayout: Layout = new AbsoluteLayout(this)

  def getVisualPanelLayout: Layout  = vpLayout
  def setVisualPanelLayout(layout:Layout): Unit = this.vpLayout = layout

  def addComponent(component: BasicComponent, layer: Int, locator: Locator): Unit ={
    vpLayout.addComponent(component, layer, locator)
  }

  def addComponent(component: BasicComponent, layer: Layer, locator: Locator): Unit ={
    addComponent(component, layer.getIndex, locator)
  }



  override def toString = s"VisualPanel{$name, $dimension}"

  override def getName: String = name

  override def update(g: Graphics): Unit = {
    //println("VisualPanel update start")
    //super.paintComponent(g)
    val g2d = g.asInstanceOf[Graphics2D]



    vpLayout.getLayers.foreach(_.update(g2d))
    //println("VisualPanel update end")
  }

}
