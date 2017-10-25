package core.component

import java.awt.{Color, Font, Graphics2D, Point}

import core.component.exception.AttributeNotProvidedException
import core.component.utils.Focus
import core.event.listener.EventListener
import core.shape.{Rectangle, Shape}


import scala.collection.mutable


/**
  * Created by kalelzar on 5/5/17.
  *
  * This trait serves as a base for everything that has
  * a visual representation on screen whether it will be opaque
  * or completely transparent.
  *
  * Contains all base methods required by Components.
  * Extended mainly by other traits that give more functionality
  * at the expense of generalization
  */
trait BasicComponent {

  private val attributes = mutable.Map[String, Any]()
  private val attributeFunc = mutable.Map[String, Any => Unit]()

  provide[Int]("layer", 0)

  /**
    * The unique identifier provided to the component upon its initialization.
    * It can be used to get a reference to the instance of the component it is assigned to
    * by utilizing the <code> BasicComponent.getBasicComponentByID(id) <code> method in the
    * BasicComponent companion object.
    */
  provide[Int]("ID", BasicComponent.getID(this))

  provide[Shape]("shape", new Rectangle(0,0,0,0))

  provide[Font]("font", BasicComponent.getDefaultFont)

  provide[Float]("x", 0.0f)
  provide[Float]("y", 0.0f)
  provide[Float]("borderThickness", 0.01f, setBorderThickness(_))

  provide[Boolean]("fill", false, setFill(_))

  provide[Color]("borderColor", Color.BLACK, setBorderColor(_))
  provide[Color]("fillColor", Color.WHITE, setFillColor(_))

  provide[mutable.ListBuffer[EventListener]]("listeners", mutable.ListBuffer[EventListener]())

  provide[VisualPanel]("visualPanel", Window.getDummyVisualPanel)

  protected def provide[R](name: String, defaultValue: R, func: R => Unit): Unit ={
    attributes(name) = defaultValue
    attributeFunc(name) = func.asInstanceOf[Any => Unit]
    func(defaultValue)
  }

  protected def provide[R](name: String, defaultValue: R): Unit ={
    attributes(name) = defaultValue

    def dummy(v: Any) : Unit = v
    attributeFunc(name) = dummy
  }

  def getAttributes: mutable.Map[String, Any] = attributes


  def setAttribute[R](name: String, value: R): Unit ={
    if(attributes.contains(name)) {
      if(!attributes(name).getClass.isInstance(value)) {
        throw new IllegalArgumentException(s"$value is a different type from ${attributes(name)}")
      }
      attributes(name) = value
      attributeFunc(name)(value)
    }
    else throw AttributeNotProvidedException(s"Attribute $name is not provided by any component")
  }
  def -->[R](name: String, value: R): Unit = setAttribute(name, value)

  def getAttribute[R](name: String): R ={
    if(attributes.contains(name)) attributes(name).asInstanceOf[R]
    else throw AttributeNotProvidedException(s"Attribute $name is not provided by any component")
  }
  def <--[R](name: String): R = getAttribute(name)




  /* Setters */

  /**
    * Moves the shape's center to the location pointed to by the Point
    *
    * @param point - the location
    */
  def moveTo(point: Point): Unit = {
    <--[Shape]("shape").moveTo(point.x, point.y)
  }

  /**
    * Adds the provided event listener to the currently maintained list.
    *
    * @param el - the event listener
    */
  def addListener(el:EventListener): Unit ={
    el.register(this)
    <--[mutable.ListBuffer[EventListener]]("listeners")+=el
  }



  /**
    * Sets the thickness of the border surrounding the shape to the specified value. The default is 0.01f.
    *
    * @param bt the border thickness
    */
  private def setBorderThickness(bt: Float): Unit = <--[Shape]("shape").setBorderThickness(bt)

  /**
    * Sets the fill color of the shape if fill is enabled to the specified value. The default is Black.
    *
    * @param color the fill color
    */
  private def setFillColor(color: Color): Unit = {
    <--[Shape]("shape").setFillColor(color)
  }

  /**
    * Sets the border color of the shape to the specified value. The default is Black.
    *
    * @param color the border color
    */
  private def setBorderColor(color: Color): Unit = {
    <--[Shape]("shape").setBorderColor(color)
  }

  /**
    * Enables or disables the fill color of the shape. The default is off.
    *
    * @param fill should the shape be filled with background color ( true ) or left transparent ( false )
    */
  private def setFill(fill: Boolean): Unit = {
    <--[Shape]("shape").setFill(fill)
  }


  /* Getters */

  /**
    * Tells if this component currently has focus i.e. it is currently in use or it has to consume events
    *
    * @return the focus
    */
  def hasFocus: Boolean = {
    Focus.hasFocus( <--("ID") )
  }

  /**
    * Returns the coordinates of the center of the currently assigned shape as reported by the shape itself
    *
    * @return the location of the shape
    */
  def getShapeLocation: Point = {
    <--[Shape]("shape").getLocation
  }

  /**
    * Checks if a coordinate point is contained within the boundaries of the current shape.
    *
    * @param p the point to check
    * @return is it contained
    */
  def isInside(p: Point): Boolean = {
    <--[Shape]("shape").polygon.contains(p)
  }

  override def toString: String = {
    //s"Has Focus: $hasFocus"
    "BasicComponent"
  }


  /* Placeholder */


  /**
    * The main draw method of the component. All it does by default is to call the
    * current shape's draw method and relegate all drawing to it.
    * This behavior can be overridden.
    *
    * @param graphics2D the graphics
    */
  def draw(graphics2D: Graphics2D): Unit ={
    //println("BasicComponent draw start")
    graphics2D.setFont(getAttribute("font"))
    <--[Shape]("shape").draw(graphics2D)
    graphics2D.setFont(BasicComponent.getDefaultFont)
    //println("BasicComponent draw end")
  }

}

object BasicComponent{
  /**
    * Returns the default Font used by BasicComponents
    *
    * @return the default font
    */
  val getDefaultFont: Font = new Font(Font.MONOSPACED, Font.PLAIN, 12)

  /**
    * A list of all BasicComponents ever instantiated
    */
  private var basicComponents = mutable.ListBuffer[BasicComponent]()

  /**
    * First adds the provided component if it isn't already there
    * then returns its index within that list to serve as the ID
    *
    * @param basicComponent the BasicComponent whose ID to return
    * @return the ID
    */
  def getID(basicComponent: BasicComponent): Int = {
    basicComponents+=basicComponent
    basicComponents=basicComponents.distinct
    basicComponents.indexOf(basicComponent)
  }

  /**
    * Returns a reference to the instance with ID equal to the one provided
    *
    * @param id -the id of the component to return
    * @return the component
    */
  def getBasicComponentByID(id: Int): BasicComponent ={
    basicComponents.apply(id)
  }

  /**
    * Returns a list of components that contains all elements
    * from the list of all BasicComponents for which the
    * provided function is true
    *
    * @param f filter function that takes a BasicComponent and returns a Boolean
    * @return the list of components for which the function is true
    */
  def getAllComponentsWhich(f: BasicComponent => Boolean): mutable.ListBuffer[BasicComponent] ={
    basicComponents.filter(f)
  }

}
