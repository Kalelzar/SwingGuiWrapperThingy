

import core.animation.Animation
import core.component.BasicComponent
import core.shape.{Polygram, Shape}

class PentagramComponent(var x: Int, var y: Int, var sideSize: Int,var rotation:Double=0.0) extends BasicComponent{
  +|("shape", new Polygram(x, y, 5, sideSize, rotation))

  def addAnimation(animation: Animation): Unit = ?|[Shape]("shape").addAnimation(animation)
}
