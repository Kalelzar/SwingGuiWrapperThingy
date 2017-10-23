package core.event.listener

import core.event.{Event, EventType}

abstract class MouseEventListener extends EventListener(EventType.Mouse){



  final override def consume(event: Event): Int = {
    val eventAction = event.getData.getAction
    if(eventAction == "Clicked"){
      mouseClicked(event)
    }else if(eventAction == "Pressed"){
      mousePressed(event)
    }else if(eventAction == "Released"){
      mouseReleased(event)
    }else if(eventAction == "EnterScreen"){
      mouseEnterScreen(event)
    }else if(eventAction == "ExitScreen"){
      mouseExitScreen(event)
    }else{
      return -1
    }
    0
  }

  def mouseClicked(event: Event): Unit = { }
  def mousePressed(event: Event): Unit = { }
  def mouseReleased(event: Event): Unit = { }
  def mouseExitScreen(event: Event): Unit = { }
  def mouseEnterScreen(event: Event): Unit = { }
}
