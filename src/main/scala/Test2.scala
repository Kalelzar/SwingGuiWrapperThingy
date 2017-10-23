import java.awt.{Dimension, Font}

import core.component._
import core.component.utils.Focus
import core.event.SwingEventCaller.MasterSwingEventController
import core.layout.AbsoluteLayout

object Test2 extends App{

  implicit val w: Window = new Window(800, 600)
  Window.setMainWindow(w)
  Focus.giveFocus(BasicComponent.getID(w))

  implicit val vp: VisualPanel = new VisualPanel(new Dimension(800,600),"Square")
  val font = new Font(Font.MONOSPACED, Font.PLAIN, 24)
  val al = new AbsoluteLayout
  val tl = vp.build(new TextLabel)
    .withAttribute("x", 200f)
    .withAttribute("y", 200f)
    .withAttribute("layer", 3)
    .forLayout(al)
    .withAttribute("font", font)
    .withAttribute("text", "Hello")
    .acquireReference

  val tf = vp.build(new TextField)
    .withAttribute("x",200f)
    .withAttribute("y",300f)
    .withAttribute("layer", 0)
    .forLayout(al)
    .withAttribute("chars", 10)
    .withAttribute("font", font)
    .acquireReference

  println(tl.getAttributes)
  println(tf.getAttributes)

  w.addVisualPanel(vp)
  w.display("Square")

  MasterSwingEventController.startThread()

  while(true){
    w.update()
  }

}
