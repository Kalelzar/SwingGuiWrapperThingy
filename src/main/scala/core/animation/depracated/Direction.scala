package core.animation.depracated

/**
  * @deprecated
  */
object Direction {

  def apply(xmov: Float, ymov: Float): Direction = new Direction(xmov, ymov)

  val NORTH = Direction(0, -1)
  val SOUTH = Direction(0, 1)
  val WEST = Direction(-1, 0)
  val EAST = Direction(1, 0)
  val NORTHEAST = Direction(0.5f, -0.5f)
  val SOUTHEAST = Direction(0.5f, 0.5f)
  val NORTHWEST = Direction(-0.5f, -0.5f)
  val SOUTHWEST = Direction(-0.5f, 0.5f)
}

/**
  * @deprecated
  * @param xmov
  * @param ymov
  */
class Direction(xmov: Float, ymov: Float) {
  def getMovX: Float = xmov
  def getMovY: Float = ymov

  override def toString: String = s"$getMovX $getMovY"
}