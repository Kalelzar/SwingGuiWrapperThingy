package core.layout.utils

import java.awt.Point

import core.component.{BasicComponent, VisualPanel}
import core.layout.LayoutManager.LayerManager

trait Locator {

  def getComponent: BasicComponent

  def validate(visualPanel: VisualPanel, layerID: Int): Boolean =
    LayerManager.validate(this)(visualPanel, layerID)

  def getX: Int
  def getY: Int
  def getPoint: Point = new Point(getX, getY)

}
