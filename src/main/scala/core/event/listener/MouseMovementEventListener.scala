package core.event.listener

import core.event.{Event, EventType}

class MouseMovementEventListener extends EventListener(EventType.MouseMovement) {
  override def consume(event: Event): Int = {
    val eventAction = event.getData.getAction
    if(eventAction == "Dragged"){
      mouseDragged(event)
    }else if(eventAction == "Moved"){
      mouseMoved(event)
    }else {
      return -1
    }
    0
  }

  def mouseDragged(event: Event): Unit = { }
  def mouseMoved(event: Event): Unit = { }

}
