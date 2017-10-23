package core.layout

import core.component.{BasicComponent, VisualPanel}
import core.layout.utils.{AbsoluteLocator, Locator}

class AbsoluteLayout(implicit visualPanel: VisualPanel) extends Layout{
  setAdministeredVisualPanel(visualPanel)

  override def acquireLocator(comp: BasicComponent): Locator = new AbsoluteLocator(comp)
}

