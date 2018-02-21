package core.util

class Point(var x: Float, var y: Float) {


  override def toString: String = s"Point[x=$x, y=$y]"

  def this(){
    this(0, 0)
  }

  def this(x: Double, y: Double){
    this(x.toFloat, y.toFloat)
  }

  def translate(dx: Float, dy: Float): Unit = {
    x+=dx
    y+=dy
  }

  def setLocation(x: Float, y: Float): Unit = {
    this.x = x
    this.y = y
  }

  def getLocation: Point = new Point(x, y)

  def setX(x: Float): Unit = {
    setLocation(x, y)
  }

  def setY(y: Float): Unit = {
    setLocation(x, y)
  }

  def getX: Float = x
  def getY: Float = y

}

