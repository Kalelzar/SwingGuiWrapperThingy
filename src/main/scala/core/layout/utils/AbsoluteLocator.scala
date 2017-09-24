package core.layout.utils

import core.component.BasicComponent

class AbsoluteLocator(basicComponent: BasicComponent) extends Locator{
  override def getComponent: BasicComponent = basicComponent

  override def getX: Int = basicComponent.getShapeLocation.x
  override def getY: Int = basicComponent.getShapeLocation.y

}
