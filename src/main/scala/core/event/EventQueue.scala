package core.event

import com.sun.java.swing.plaf.motif.MotifBorders.FocusBorder
import core.component.utils.Focus
import core.event.listener.EventListener

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object EventQueue {

  private val eventQueue = mutable.Queue[Event]()
  private val listeners = mutable.Map[String, mutable.ListBuffer[EventListener]]()



  def fireEvent(event: Event): Unit = {

    if(listeners.isDefinedAt(event.getType)) eventQueue.enqueue(event)

  }

  def registerListener(listener: EventListener): Unit ={

    if(listeners.isDefinedAt(listener.getType)){
      listeners(listener.getType)+=listener
    }else{
      listeners(listener.getType) = ListBuffer[EventListener](listener)
    }

  }

  def consumeNextEvent(): Unit = {

    if (eventQueue.length <1) return
    val event = eventQueue.dequeue()
    if( event == null) return
    val list = listeners(event.getType).filter(_.hasFocus)
    if(list.length<1) return
    list.foreach(_.consume(event))
  }


  def getListenerTypes: Seq[String] = {
    listeners.keys.toSeq
  }

}
