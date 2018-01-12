import java.awt.{Dimension, Font}

import core.component.utils.Focus
import core.component.{BasicComponent, ShapeComponent, VisualPanel, Window}
import core.event.SwingEventCaller.MasterSwingEventController
import core.layout.AbsoluteLayout
import core.shape.Polygram

object Test3 extends App {

  implicit val w: Window = new Window(800, 600)
  Window.setMainWindow(w)
  Focus.giveFocus(BasicComponent.getID(w))

  implicit val vp: VisualPanel = new VisualPanel(new Dimension(800, 600), "Square")
  val font = new Font(Font.MONOSPACED, Font.PLAIN, 24)
  val al = new AbsoluteLayout
  val tl = vp.build(new ShapeComponent(new Polygram(0, 0, 5, 100)))
    .withAttribute("x", 400f)
    .withAttribute("y", 200f)
    .withAttribute("layer", 3)
    .forLayout(al)
    .acquireReference

  w.addVisualPanel(vp)
  w.display("Square")

  MasterSwingEventController.startThread()

  while (true) {
    w.update()
  }
}
