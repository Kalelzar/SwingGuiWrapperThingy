package core.component

import java.awt.event.MouseEvent
import java.awt.Color

import core.component.utils.Focus
import core.event.Event
import core.event.listener.MouseEventListener
import core.shape.Rectangle
import core.shape.deprecated.Shape
import core.shape.helper.Point

class ContextMenu(var width: Float, var height: Float, parent: BasicComponent) extends Menu {

  val blank = new Rectangle(0, 0, 0, 0)
  -->("shape", blank)

  class ContextMenuCreationListener extends MouseEventListener{

    override def mouseReleased(event: Event): Unit = {
      if(event.getData.at(0).asInstanceOf[Int]==3) {
        show(event.getData.at(1).asInstanceOf[Int], event.getData.at(2).asInstanceOf[Int])
      }
    }

  }

  class ContextMenuListener extends MouseEventListener{

    override def mouseClicked(event: Event): Unit = {
      val point = new Point(event.getData.at(1).asInstanceOf[Int], event.getData.at(2).asInstanceOf[Int])
      if(!isInside(point)){
        if(event.getData.at(0).asInstanceOf[Int] == 1){
          Focus.giveFocus(BasicComponent.getID(parent))
          -->("shape", blank)
          iterateOverElements( (me: MenuElement) =>me.hide())
        }else if(event.getData.at(0).asInstanceOf[Int] == 3){
          show(point.getX.toInt, point.getY.toInt)
        }
      }
    }

  }

  private val creationListener = new ContextMenuCreationListener
  parent.addListener(creationListener)

  private val listener = new ContextMenuListener
  addListener(listener)

  def box(): Unit ={
    if(getElements.isEmpty) throw new NullPointerException("There are no elements to box")
    val dim = getElements.head.<--[Shape]("shape").getDimension
    if(getElements.forall(_.<--[Shape]("shape").getDimension.equals(dim))){
      width = dim.width
      height = dim.height*getElements.length
    }
    var ind = 0
    iterateOverElements{ me: MenuElement =>
      me.whenBoxed(ind, this)
      ind+=1
    }
  }


  override def addElement(element: MenuElement): Unit = {
    super.addElement(element)
    box()
  }


  def show(x: Int, y: Int): Unit ={
    -->("shape", new Rectangle(x+width/2, y+height/2, width, height))
    -->("fill", true)
    -->("fillColor", Color.WHITE)
    box()
    println(getShapeLocation)
    if(hasFocus) return
    Focus.giveFocus(BasicComponent.getID(this))
  }

  override def toString = "ContextMenu"
}
