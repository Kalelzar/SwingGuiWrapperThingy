package core.util

import core.component.exception.AttributeNotProvidedException

import scala.collection.mutable
import scala.reflect.ClassTag

trait AttributeRegister {
  private val attributes = mutable.Map[String, Any]()
  private val attributeFunc = mutable.Map[String, Any => Unit]()

  def provide[R](name: String, defaultValue: R, func: R => Unit): Unit ={
    attributes(name) = defaultValue
    attributeFunc(name) = func.asInstanceOf[Any => Unit]
    func(defaultValue)
  }

  def provide[R](name: String, defaultValue: R): Unit ={
    attributes(name) = defaultValue

    def dummy(v: Any) : Unit = v
    attributeFunc(name) = dummy
  }

  def getAttributes: mutable.Map[String, Any] = attributes


  def setAttribute[R: ClassTag](name: String, value: R): Unit = {
    if(attributes.contains(name)) {
      val classList = new java.util.ArrayList[Class[_]]
      classList.add(attributes(name).getClass)
      classList.add(value.getClass)
      val result = List(attributes(name)).flatMap {
        case x: R => Some(x)
        case _ => None
      }.nonEmpty
      if (!result) {
        throw new IllegalArgumentException(s"$value is a different type from ${attributes(name)}")
      }
      attributes(name) = value
      attributeFunc(name)(value)
    }
    else throw AttributeNotProvidedException(s"Attribute $name is not provided by any component")
  }

  def -->[R: ClassTag](name: String, value: R): Unit = setAttribute[R](name, value)

  def getAttribute[R](name: String): R ={
    if(attributes.contains(name)) attributes(name).asInstanceOf[R]
    else throw AttributeNotProvidedException(s"Attribute $name is not provided by any component")
  }
  def <--[R](name: String): R = getAttribute(name)
}
