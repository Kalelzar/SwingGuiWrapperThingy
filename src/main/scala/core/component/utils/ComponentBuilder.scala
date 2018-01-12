package core.component.utils

import core.component.{BasicComponent, VisualPanel}
import core.layout.Layout

import scala.reflect.ClassTag

class ComponentBuilder[T <: BasicComponent](component: T, vp : VisualPanel) {



  def forLayout[R <: Layout](layout: R): ComponentBuilder[T] = {
    vp.addComponent(component, component.<--[Int]("layer"), layout.acquireLocator(component))
    this
  }

  def withAttribute[R: ClassTag](name: String, value: R): ComponentBuilder[T] = {
    component.-->(name, value)
    this
  }

  def acquireReference: T = component

  def acquireID: Int = BasicComponent.getID(component)


}
