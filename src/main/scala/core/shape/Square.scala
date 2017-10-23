package core.shape
import java.awt.Dimension

/**
  * Created by kalelzar on 5/5/17.
  */
class Square(x: Float, y: Float, side: Int, rotation: Double = 0) extends RegularPolygon(x, y, 4, side, rotation){



  override def getDimension: Dimension = new Dimension(side, side)
}
