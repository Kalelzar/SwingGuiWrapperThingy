package core.shape


class Circle extends RegularPolygon {
  provide[Int]("sides", 500)
  provide[Float]("radius", 100f, updateRadius(_))
  def updateRadius(rad: Float): Unit ={
    -->[Float]("sideLength", rad)
  }

}
