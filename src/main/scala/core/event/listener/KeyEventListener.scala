package core.event.listener

import core.event.{Event, EventType}

class KeyEventListener extends EventListener(EventType.Keyboard){
  final override def consume(event: Event): Int = {
    val eventAction = event.getData.getAction
    if(eventAction == "Pressed") keyPressed(event)
    else if(eventAction == "Typed") keyTyped(event)
    else if(eventAction == "Released") keyReleased(event)
    else return -1
    0
  }

  def keyPressed(event: Event): Unit = { }
  def keyTyped(event: Event): Unit = { }
  def keyReleased(event: Event): Unit = { }

}
