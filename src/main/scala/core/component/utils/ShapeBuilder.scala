package core.component.utils

import core.shape.AbstractShape

import scala.reflect.ClassTag

class ShapeBuilder[T <: AbstractShape ](shape: T) {

  def withAttribute[R: ClassTag](name: String, value: R): ShapeBuilder[T] = {
    shape.-->(name, value)
    this
  }

  def build: T = shape

}
