package core.shape
import java.awt.Dimension

class Circle(startx: Float, starty: Float, radius: Int) extends RegularPolygon(startx,starty,500,radius){


  override def centerOnX: Float = ???
  override def centerOnY: Float = ???

  override def getDimension: Dimension = new Dimension(radius, radius)
}
