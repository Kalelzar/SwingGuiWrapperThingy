package core.event.SwingEventCaller

import java.awt.event.{KeyEvent, KeyListener}

import core.component.BasicComponent
import core.component.utils.Focus
import core.event.{Event, EventData, EventQueue, EventType}

class SwingKeyboardEventsCaller extends KeyListener{
  override def keyPressed(keyEvent: KeyEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("Pressed")
    ed.addData(keyEvent.getKeyChar, keyEvent.getKeyCode,  keyEvent.isAltDown, keyEvent.isControlDown, keyEvent.isShiftDown)
    EventQueue.fireEvent(new Event(EventType.Keyboard, ed))
  }
  override def keyTyped(keyEvent: KeyEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("Typed")
    ed.addData(keyEvent.getKeyChar, keyEvent.getKeyCode,  keyEvent.isAltDown, keyEvent.isControlDown, keyEvent.isShiftDown)
    EventQueue.fireEvent(new Event(EventType.Keyboard, ed))
  }
  override def keyReleased(keyEvent: KeyEvent): Unit = {
    if(Focus.getFocused.last < 0) return
    val ed = new EventData
    ed.setSource(BasicComponent.getBasicComponentByID(Focus.getFocused.last))
    ed.setAction("Released")
    ed.addData(keyEvent.getKeyChar, keyEvent.getKeyCode,  keyEvent.isAltDown, keyEvent.isControlDown, keyEvent.isShiftDown)
    EventQueue.fireEvent(new Event(EventType.Keyboard, ed))
  }
}
