package core

import core.component.BasicComponent
import core.component.utils.Focus
import core.event.listener.MouseEventListener
import core.event.{Event, EventType}

class TestClicked extends MouseEventListener{
  override def mouseClicked(event: Event): Unit = {

  }

  override def mousePressed(event: Event): Unit = {

  }

  override def mouseReleased(event: Event): Unit = {

  }

  override def mouseExitScreen(event: Event): Unit ={

  }

  override def mouseEnterScreen(event: Event): Unit = {

  }

}

object TestClicked{

  val tc = new TestClicked

  def get: TestClicked = tc

}