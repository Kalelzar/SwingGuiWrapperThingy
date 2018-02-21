package core.layout.utils

import core.component.{BasicComponent, VisualPanel}
import core.layout.LayoutManager.LayerManager
import core.util.Point

trait Locator {

  def getComponent: BasicComponent

  def validate(visualPanel: VisualPanel, layerID: Int): Boolean =
    LayerManager.validate(this)(visualPanel, layerID)

  def getX: Float
  def getY: Float
  def getPoint: Point = new Point(getX, getY)

}
