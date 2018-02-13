package core.shape.deprecated

import java.awt.geom.GeneralPath
import java.awt.{BasicStroke, Color, Dimension, Graphics2D, Point, RenderingHints}

import core.animation.Animation
import core.shape.Vertex
/**
  * Created by kalelzar on 5/5/17.
  *
  * Basis of all visual representation which is not text or an image
  */
trait Shape{


  /**
  *
  * Returns the center point of the polygon shape
  *
  *@return The center point
  *
  */
  def getLocation: Point = {
    new Point(polygon.getBounds.getCenterX.toInt, polygon.getBounds.getCenterY.toInt)
  }

  implicit def shape: Shape = this

  protected var borderThickness = 0.01f
  protected var borderColor: Color = Color.BLACK
  protected var fillColor: Color = Color.BLACK
  protected var fill = false

  protected var animationList: List[Animation] = List[Animation]()

  protected var vertexList: List[Vertex] = List[Vertex]()
  private var bakVertexList: List[Vertex] = List[Vertex]()

  protected var packed = false
  var polygon = new GeneralPath()

  def draw(graphics2D: Graphics2D): Unit ={
    //println("Shape draw start")
    updateAll

    val rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    graphics2D.setRenderingHints(rh)
    graphics2D.setStroke(new BasicStroke(borderThickness))
    if(packed) {
      if(fill){
        graphics2D.setPaint(fillColor)
        graphics2D.fill(polygon)
      }
      graphics2D.setPaint(borderColor)
      graphics2D.draw(polygon)

    }
    //println("Shape draw end")
  }

  def addVertex(vertex: Vertex): Unit ={
    if(packed) return

    vertexList = vertexList ++ List(vertex)
  }

  def addAnimation(animation: Animation): Unit ={

    animationList = animationList ++ List(animation)
  }

  def pack(): Unit = {
    packed = true
    bakVertexList = vertexList
    polygon.moveTo(vertexList.head._1, vertexList.head._2)
    vertexList = vertexList.drop(1)
    vertexList.foreach( vertex => polygon.lineTo(vertex._1, vertex._2))
    polygon.closePath()
  }

  def rebuild(): Unit = {
    packed = false
    vertexList = List[Vertex]()
    polygon = new GeneralPath()
  }

  def moveTo(xx: Float, yy: Float): Unit ={
    if(!packed && vertexList.nonEmpty) return
    val first = vertexList.head
    vertexList = bakVertexList.map( vx =>(vx._1+xx-first._1+centerOnX,vx._2+yy-first._2+centerOnY))
    println(vertexList)
    polygon = new GeneralPath()
    packed = false
    pack()
  }

  def centerOnX: Float
  def centerOnY: Float

  def move(xx: Float, yy: Float, step: Float): Unit = {
    if(!packed) return
    vertexList = bakVertexList.map( x => (x._1 + step*xx, x._2 + step*yy) )
    polygon = new GeneralPath()
    packed = false
    pack()
  }

  //def scale(xscale:)

  def getDimension: Dimension
  def getBorderSize: Float = borderThickness
  def getFillColor: Color = fillColor

  def setFillColor(color: Color): Unit = {
    fillColor = color
  }

  def setBorderColor(color: Color): Unit = {
    borderColor = color
  }

  def setFill(fill: Boolean): Unit = {
    this.fill = fill
  }

  def setBorderThickness(thickness: Float): Unit = {
    borderThickness = thickness
  }

  def updateAll: Unit =  {
    animationList.foreach{_.update}
  }

  def startAll: Unit = {
    animationList.foreach{_.start}
  }

  def stopAll: Unit = {
    animationList.foreach{_.stop}
  }

  def resetAll: Unit = {
    animationList.foreach{_.reset}
  }

}
