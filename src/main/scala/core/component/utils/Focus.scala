package core.component.utils


import core.component.BasicComponent
import core.event.{Event, EventData, EventQueue}
import core.event.EventType

import scala.collection.mutable

object Focus {



  private val focusMap = mutable.Map[Int, Boolean]()
  private var focused: Int = -1
  private val silentFocus = mutable.ListBuffer[Int]()

  def getFocused: Seq[Int] = silentFocus.toList.++:(Seq(focused))

  def hasFocus(id: Int): Boolean ={
    if(!focusMap.isDefinedAt(id)) {
      focusMap(id)= if(id == focused || silentFocus.contains(id)) true else false
    }
    focusMap(id)
  }

  def clearFocus(): Unit ={
    takeFocus(focused, -1)
    focused = -1
  }

  protected def takeFocus(focused: Int, newid: Int): Unit = {
    if(focused == newid) return
    focusMap(focused)=false
    val ed = new EventData
    ed.setAction("FocusLost")
    ed.addData(newid)
    ed.setSource(BasicComponent.getBasicComponentByID(focused))
    EventQueue.fireEvent(new Event(EventType.Focus, ed ))
  }

  def giveFocus(basicComponent: BasicComponent, silent: Boolean): Unit ={
    giveFocus(BasicComponent.getID(basicComponent), silent)
  }

  def giveFocus(newid: Int, silent: Boolean = false): Unit ={
    if(focused == newid) return
    focusMap(newid)=true
    if(silent) silentFocus+=newid else {
      if(focused>=0) takeFocus(focused, newid)
      val ed = new EventData
      ed.setAction("FocusGained")
      ed.addData(focused)
      ed.setSource(BasicComponent.getBasicComponentByID(newid))
      EventQueue.fireEvent(new Event(EventType.Focus, ed ))
      focused = newid
    }

  }


}
