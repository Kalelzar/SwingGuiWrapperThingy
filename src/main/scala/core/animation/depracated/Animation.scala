package core.animation.depracated

import core.shape.deprecated.Shape

import scala.collection.mutable

/**
  * @deprecated
  */
object Animation {
  val animations = mutable.Map[String, Animation]()

  def add(name: String)(implicit animation: Animation): Unit = animations(name)=animation

  def apply(name: String): Animation = animations(name)

}

/**
  * @deprecated
  */
trait Animation {
  implicit def animation : Animation = this
  def onCreate(name: String): Unit = Animation.add(name)

  def animate(shape: Shape, varargs : Seq[Any])(f: (Shape, Seq[Any]) => (Unit)): Unit = {
    f(shape, varargs)
  }

  def start: Unit
  def update(implicit shape: Shape): Unit
  def stop: Unit
  def reset: Unit
}
