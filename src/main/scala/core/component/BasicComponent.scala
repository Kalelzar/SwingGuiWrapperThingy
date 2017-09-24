package core.component

import java.awt.{Color, Font, Graphics2D, Point}

import core.component.utils.Focus
import core.event.listener.EventListener
import core.shape.Shape

import scala.collection.mutable

/**
  * Created by kalelzar on 5/5/17.
  *
  * This trait serves as a base for everything that has
  * a visual representation on screen whether it will be opaque
  * or completely transparent.
  *
  * Contains all base methods required by Components.
  *
  */
trait BasicComponent {

  private var visualPanel: VisualPanel = null

  def setVisualPanel(visualPanel: VisualPanel): Unit = this.visualPanel = visualPanel
  def getVisualPanel: VisualPanel = visualPanel


  private val ID: Int = BasicComponent.getID(this)
  private val listeners = mutable.ListBuffer[EventListener]()

  override def toString: String = {
    //s"Has Focus: $hasFocus"
    "BasicComponent"
  }

  def moveTo(point: Point): Unit = {
    getComponentShape.moveTo(point.x, point.y)
  }



  protected var shape: Shape = _
  var parentComp: BasicComponent = _
  private var font: Font = new Font(Font.MONOSPACED, Font.PLAIN, 12)

  /* Setters */
  def setShape(shape: Shape) : Unit = this.shape = shape
  def addListener(el:EventListener): Unit ={
    el.register(this)
    listeners+=el
  }
  def setComponentFont(font: Font): Unit = this.font = font
  def setBorderThickness(bt: Float): Unit = shape.setBorderThickness(bt)
  def setFillColor(color: Color): Unit = {
    shape.setFillColor(color)
  }
  def setBorderColor(color: Color): Unit = {
    shape.setBorderColor(color)
  }
  def setFill(fill: Boolean): Unit = {
    shape.setFill(fill)
  }

  /* Getters */
  def getListeners: mutable.ListBuffer[EventListener] = listeners
  def getComponentShape: Shape = shape
  def hasFocus: Boolean = {
    Focus.hasFocus(ID)
  }
  def getShapeLocation: Point = {
    getComponentShape.getLocation
  }
  def isInside(p: Point): Boolean = {
    getComponentShape.polygon.contains(p)
  }

  def getID: Int = ID
  def getComponentFont: Font = font


  /* Placeholder */
  def draw(graphics2D: Graphics2D): Unit ={
    //println("BasicComponent draw start")
    graphics2D.setFont(font)
    shape.draw(graphics2D)
    graphics2D.setFont(Window.getMainWindow.getComponentFont)
    //println("BasicComponent draw end")
  }

}

object BasicComponent{
  private var basicComponents = mutable.ListBuffer[BasicComponent]()

  def getID(basicComponent: BasicComponent): Int = {
    basicComponents+=basicComponent
    basicComponents=basicComponents.distinct
    basicComponents.indexOf(basicComponent)
  }

  def getBasicComponentByID(id: Int): BasicComponent ={
    basicComponents.apply(id)
  }

  def getAllComponentsWhich(f: BasicComponent => Boolean): mutable.ListBuffer[BasicComponent] ={
    basicComponents.filter(f)
  }

}
