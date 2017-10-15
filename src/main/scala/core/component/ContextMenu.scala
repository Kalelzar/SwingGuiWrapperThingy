package core.component

import java.awt.event.MouseEvent
import java.awt.{Color, Point}

import core.component.utils.Focus
import core.event.Event
import core.event.listener.MouseEventListener
import core.shape.Rectangle

class ContextMenu(width: Float, height: Float, parent: BasicComponent) extends Menu {

  val blank = new Rectangle(0, 0, 0, 0)
  setShape(blank)

  class ContextMenuCreationListener extends MouseEventListener{

    override def mouseReleased(event: Event): Unit = {
      if(event.getData.at(0).asInstanceOf[Int]==3) {
        show(event.getData.at(1).asInstanceOf[Int], event.getData.at(2).asInstanceOf[Int])
      }
    }

  }

  class ContextMenuListener extends MouseEventListener{

    override def mouseClicked(event: Event): Unit = {
      if(event.getData.at(0).asInstanceOf[Int] != 1) return
      if(!isInside(new Point(event.getData.at(1).asInstanceOf[Int], event.getData.at(2).asInstanceOf[Int]))){
        Focus.giveFocus(BasicComponent.getID(parent))
        setShape(blank)
      }
    }

  }

  private val creationListener = new ContextMenuCreationListener
  parent.addListener(creationListener)

  private val listener = new ContextMenuListener
  addListener(listener)

  def show(x: Int, y: Int): Unit ={

    setShape( new Rectangle(x+width/2, y+height/2, width, height))
    setFill(true)
    setFillColor(Color.WHITE)
    Focus.giveFocus(BasicComponent.getID(this))
    println(Focus.getFocused)
  }

  override def toString = "ContextMenu"
}
