package core.event.listener

import core.event.{Event, EventType}

abstract class FocusEventListener extends EventListener(EventType.Focus){

  final override def consume(event: Event): Int = {
    val eventAction = event.getData.getAction
    if(eventAction == "FocusLost") focusLost(event)
    else if(eventAction == "FocusGained") focusGained(event)
    else return -1
    0
  }

  def focusLost(event: Event): Unit = { }
  def focusGained(event: Event): Unit = { }


}

