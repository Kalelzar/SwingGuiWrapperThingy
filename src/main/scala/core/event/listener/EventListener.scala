package core.event.listener

import core.component.BasicComponent
import core.event.{Event, EventQueue}

abstract class EventListener(eventType: String*) {



  EventQueue.registerListener(this)

  override def toString = s"Listening for $eventType on $parent"

  private var parent: BasicComponent = _

  def register(basicComponent: BasicComponent): Unit = parent = basicComponent

  def consume(event: Event): Int

  def hasFocus: Boolean = {
    parent.hasFocus
  }
  def getType: Seq[String] = eventType

}
