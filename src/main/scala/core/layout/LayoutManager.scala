package core.layout

import core.component.VisualPanel
import core.layout.utils.Locator

import scala.collection.mutable

/**
  * What do we want LayoutManager to do?
  * 1) Deal with the Layering and all helpers required such as
  *    methods for retrieving the object at a specified x and y in a specific layer
  *    -Programmers should be discouraged from having a number of BasicComponents overlapping
  *     at the same layer
  *    -The Layering Manager is a child object to the top-level LayoutManager tasked
  *     with dealing with the specifics of managing layering as the name would suggest.
  *     It should automatically manage the layers of overlapping objects as well as
  *     contain all layering helpers. It should make sure to display all Components as
  *     their layering will suggest. The higher the number(ID) of the layer the higher
  *     it is displayed.
  */
object LayoutManager{

  object LayerManager{


    def bump(locator: Locator, visualPanel: VisualPanel, layerID: Int): Unit = {
      visualPanel.getVisualPanelLayout.addComponent(locator.getComponent, layerID+1, locator)
    }

    def validate(locator: Locator)(visualPanel: VisualPanel, layerID: Int): Boolean = {
      val layerOpt: Option[Layer] = getLayerByID(visualPanel, layerID)
      if(layerOpt.isDefined){
        val layer = layerOpt.get
        val component = layer.getAt(locator.getPoint)
        if(component.isDefined){
          bump(locator, visualPanel, layerID)
          return false
        }else{
          return true
        }
      }
      false
    }

    val layersInVP = new mutable.HashMap[VisualPanel, Seq[Layer]]

    def isLayerDefinedAt(visualPanel: VisualPanel, layerID: Int): Boolean ={
      getLayerByID(visualPanel, layerID).nonEmpty
    }

    def isLayerDefinedAt(visualPanel: VisualPanel, layer: Layer): Boolean ={
      isLayerDefinedAt(visualPanel, layer.getIndex)
    }


    def getLayerByID(visualPanel: VisualPanel, layerID: Int): Option[Layer] = {
      getLayers(visualPanel).find(_.getIndex == layerID)
    }

    def getLayers(visualPanel: VisualPanel): Seq[Layer]={
        if(!layersInVP.isDefinedAt(visualPanel)) {
          layersInVP(visualPanel)=Seq(new Layer(0)(visualPanel.getVisualPanelLayout))
        }
        layersInVP(visualPanel)
    }

    def addLayer(visualPanel: VisualPanel, layer: Layer): Unit ={
      //If the layer is already defined at the same index of elevation - bail
      //This should not happen
      //Also print out a warning to stderr
      if( isLayerDefinedAt(visualPanel, layer) ) {
        System.err.println(s"Layer at index ${layer.getIndex} already defined for $visualPanel")
        return
      }

      layersInVP(visualPanel) = getLayers(visualPanel) ++ Seq(layer)
    }

  }

}

