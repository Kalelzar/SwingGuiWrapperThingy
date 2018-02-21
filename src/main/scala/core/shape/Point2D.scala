package core.shape

class Point2D extends BasicShape {

  override def build(x: Float, y: Float): Unit = {
    super.build(x, y)
    beginAt(x, y).close
  }

}
