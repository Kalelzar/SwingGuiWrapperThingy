package core.event.SwingEventCaller

import java.awt.event._

import core.component.BasicComponent
import core.component.utils.Focus
import core.event.{Event, EventData, EventQueue, EventType}

class SwingMouseEventsCaller extends MouseListener
                             with MouseMotionListener
                             with MouseWheelListener
{
  override def mouseExited(mouseEvent: MouseEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("ExitScreen")
    ed.addData(mouseEvent.getButton, mouseEvent.getX, mouseEvent.getY, mouseEvent.getClickCount)
    EventQueue.fireEvent(new Event(EventType.Mouse, ed))
  }

  override def mousePressed(mouseEvent: MouseEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("Pressed")
    ed.addData(mouseEvent.getButton, mouseEvent.getX, mouseEvent.getY, mouseEvent.getClickCount)
    EventQueue.fireEvent(new Event(EventType.Mouse, ed))
  }

  override def mouseReleased(mouseEvent: MouseEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("Released")
    ed.addData(mouseEvent.getButton, mouseEvent.getX, mouseEvent.getY, mouseEvent.getClickCount)
    EventQueue.fireEvent(new Event(EventType.Mouse, ed))
  }

  override def mouseEntered(mouseEvent: MouseEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("EnterScreen")
    ed.addData(mouseEvent.getButton, mouseEvent.getX, mouseEvent.getY, mouseEvent.getClickCount)
    EventQueue.fireEvent(new Event(EventType.Mouse, ed))
  }

  override def mouseClicked(mouseEvent: MouseEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("Clicked")
    ed.addData(mouseEvent.getButton, mouseEvent.getX, mouseEvent.getY, mouseEvent.getClickCount)
    EventQueue.fireEvent(new Event(EventType.Mouse, ed))
  }

  override def mouseDragged(mouseEvent: MouseEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("Dragged")
    ed.addData(mouseEvent.getButton, mouseEvent.getX, mouseEvent.getY, mouseEvent.getClickCount)
    EventQueue.fireEvent(new Event(EventType.MouseMovement, ed))
  }

  override def mouseMoved(mouseEvent: MouseEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("Moved")
    ed.addData(mouseEvent.getButton, mouseEvent.getX, mouseEvent.getY, mouseEvent.getClickCount)
    EventQueue.fireEvent(new Event(EventType.MouseMovement, ed))
  }

  override def mouseWheelMoved(mouseWheelEvent: MouseWheelEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("WheelMoved")
    ed.addData(mouseWheelEvent.getPreciseWheelRotation,
               mouseWheelEvent.getScrollAmount,
               mouseWheelEvent.getScrollType,
               mouseWheelEvent.getUnitsToScroll,
               mouseWheelEvent.getX,
               mouseWheelEvent.getY)
    EventQueue.fireEvent(new Event(EventType.MouseWheel, ed))
  }
}
