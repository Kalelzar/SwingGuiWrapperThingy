package core.layout.utils

import core.shape.helper.Point

import core.component.{BasicComponent, VisualPanel}
import core.layout.LayoutManager.LayerManager

trait Locator {

  def getComponent: BasicComponent

  def validate(visualPanel: VisualPanel, layerID: Int): Boolean =
    LayerManager.validate(this)(visualPanel, layerID)

  def getX: Float
  def getY: Float
  def getPoint: Point = new Point(getX, getY)

}
