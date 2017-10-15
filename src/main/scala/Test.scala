import java.awt.{Color, Dimension, Font}

import core.TestClicked
import core.animation.{Animation, Direction, Slide}
import core.component.utils.Focus
import core.component._
import core.event.SwingEventCaller.MasterSwingEventController
import core.event.Event
import core.event.listener.FocusEventListener
import core.layout.utils.AbsoluteLocator


/**
  * Created by kalelzar on 5/10/17.
  */
object Test extends App{
  implicit val w: Window = new Window(800, 600)

  Window.setMainWindow(w)

  val vp = new VisualPanel(new Dimension(800,600),"Square")

  Focus.giveFocus(BasicComponent.getID(w))


  val font = new Font(Font.MONOSPACED, Font.PLAIN, 24)
  val textField = new TextField(300, 300, 10, font)
  val textLabel = new TextLabel(300,200,"Label", font)
  val contextMenu = new ContextMenu(100, 400, w)

  vp.addComponent(textField, 0, new AbsoluteLocator(textField))
  vp.addComponent(textLabel, 0, new AbsoluteLocator(textLabel))
  vp.addComponent(contextMenu, 1, new AbsoluteLocator(contextMenu))

  println(textField.getShapeLocation)
  println(textLabel.getShapeLocation)

  w.addVisualPanel(vp)
  w.display("Square")


  MasterSwingEventController.startThread()

  w.addListener(new TestClicked)
  while(true){
    w.update()
  }
}

class TestFocus extends FocusEventListener{
  override def focusLost(event: Event): Unit = println("Lost Focus")
  override def focusGained(event: Event): Unit = println("Gained Focus")
}