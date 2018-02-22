package core.shape

import java.awt._
import java.awt.geom.{AffineTransform, GeneralPath, Rectangle2D}

import core.util.Point

trait BasicShape extends AbstractShape {

  provide[Boolean]("fill", false)
  provide[Float]("borderThickness", 0.01f)
  provide[Color]("fillColor", Color.BLACK)
  provide[Color]("borderColor", Color.BLACK)

  override def build(x: Float, y: Float): Unit = {
    super.build(x, y)
    if(<--[Float]("width") == 0) -->[Float]("width", getBounds.getWidth.toFloat)
    if(<--[Float]("height") == 0) -->[Float]("height", getBounds.getHeight.toFloat)
  }

  override def transform(transform: AffineTransform): Unit = {
    path.transform(transform)
  }

  override def clear(): Unit = path = new GeneralPath()

  override def getBounds: Rectangle2D = path.getBounds2D

  private var path: GeneralPath = new GeneralPath()
  private var stored: GeneralPath = null

  def store: Unit ={
    if(stored != null) return
    stored = path
    path = new GeneralPath()
  }

  def restore: Unit = {
    if(stored == null) return
    path.append(stored, false)
    stored = null
  }

  override def draw(graphics2D: Graphics2D): Unit = {
    val rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    graphics2D.setRenderingHints(rh)
    graphics2D.setStroke(new BasicStroke(<--("borderThickness")))

    if(<--("fill")){
      graphics2D.setPaint(<--("fillColor"))
      graphics2D.fill(path)
    }

    graphics2D.setPaint(<--("borderColor"))
    graphics2D.draw(path)
  }

  def beginAt(x: Float, y: Float): BasicShape = {
    path.moveTo(x, y)
    this
  }

  def lineTo(x: Float, y: Float): BasicShape = {
    path.lineTo(x, y)
    this
  }

  def curveTo(x: Float, y: Float, x1: Float, y1: Float): BasicShape ={
    path.quadTo(x, y, x1, y1)
    this
  }

  def curveTo(x: Float, y: Float, x1: Float, y1: Float, x2: Float, y2: Float): BasicShape ={
    path.curveTo(x, y, x1, y1, x2, y2)
    this
  }

  def close: BasicShape = {
    path.closePath()
    this
  }


  override def getCenter: Point = new Point(path.getBounds2D.getCenterX, path.getBounds2D.getCenterY)
  override def getBaseShapes: Seq[GeneralPath] = ???
}
