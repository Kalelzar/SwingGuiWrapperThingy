import java.awt.{Dimension, Font}


import core.component.{BasicComponent, TextLabel, VisualPanel, Window}
import core.component.utils.Focus
import core.event.SwingEventCaller.MasterSwingEventController
import core.layout.AbsoluteLayout

object Test2 extends App{

  implicit val w: Window = new Window(800, 600)
  Window.setMainWindow(w)
  Focus.giveFocus(BasicComponent.getID(w))

  implicit val vp: VisualPanel = new VisualPanel(new Dimension(800,600),"Square")

  val tl = vp.build(new TextLabel())
        .withAttribute("x", 200f)
        .withAttribute("y", 200f)
        .withAttribute("layer", 3)
        .forLayout(new AbsoluteLayout)
        .withAttribute("font", new Font(Font.MONOSPACED, Font.PLAIN, 48))
        .withAttribute("text", "Hello")
        .acquireReference

  println(tl.getAttributes)

  w.addVisualPanel(vp)
  w.display("Square")

  MasterSwingEventController.startThread()

  while(true){
    w.update()
  }

}
