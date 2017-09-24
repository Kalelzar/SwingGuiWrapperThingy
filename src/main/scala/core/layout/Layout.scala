package core.layout

import core.component.{BasicComponent, VisualPanel}
import core.layout.LayoutManager.LayerManager
import core.layout.utils.Locator

trait Layout {

  private var administeredVisualPanel: VisualPanel = _

  def setAdministeredVisualPanel(administeredVisualPanel:VisualPanel): Unit = this.administeredVisualPanel = administeredVisualPanel

  def getAdministeredVisualPanel: VisualPanel = administeredVisualPanel

  def getLayers: Seq[Layer] = {
    LayerManager.getLayers(administeredVisualPanel)
  }

  def getLayerByID(layerID: Int): Layer = {
    val opt = LayerManager.getLayerByID(administeredVisualPanel, layerID)
    var layer = new Layer(layerID)(this)
    if(opt.isEmpty){
      LayerManager.addLayer(administeredVisualPanel, layer)
    }else{
      layer = opt.get
    }

    layer
  }

  def addComponent(component: BasicComponent, layer: Int, location: Locator ): Unit = {
    component.setVisualPanel(administeredVisualPanel)
    getLayerByID(layer).getLayoutPane.addLayerElement(component, location)
  }



}
