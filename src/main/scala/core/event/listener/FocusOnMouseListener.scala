package core.event.listener
import java.awt.Point

import core.component.{BasicComponent, Window}
import core.component.utils.Focus
import core.event.Event
import core.layout.LayoutManager.LayerManager

import scala.collection.mutable.ListBuffer

class FocusOnMouseListener extends MouseEventListener {
  override def mousePressed(event: Event): Unit = {
    val lb: ListBuffer[String] = event.getData.getData.map(_.toString)
      if(lb.head.toInt == 1){
        val layers = LayerManager.getLayers(Window.getMainWindow.getCurrentVisualPanel)

        val last = layers.map(_.getAt(new Point(lb(1).toInt, lb(2).toInt)))
                         .filter(_.isDefined).lastOption.flatten
        if(last.nonEmpty) {
          if(!last.get.hasFocus) {
            Focus.giveFocus(last.get, silent = false)
          }
        }else{
          Focus.clearFocus()
        }
      }
  }

}

