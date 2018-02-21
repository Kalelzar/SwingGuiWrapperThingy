import java.awt.{Color, Dimension, Font}

import core.component.utils.Focus
import core.component.{BasicComponent, ShapeComponent, VisualPanel, Window}
import core.event.SwingEventCaller.MasterSwingEventController
import core.layout.AbsoluteLayout
import core.shape.{AbstractShape, Polygram, Rectangle}

object Test3 extends App {

  implicit val w: Window = new Window(1280, 1024)
  Window.setMainWindow(w)
  Focus.giveFocus(BasicComponent.getID(w))

  implicit val vp: VisualPanel = new VisualPanel(new Dimension(800, 600), "Square")
  val font = new Font(Font.MONOSPACED, Font.PLAIN, 24)
  val al = new AbsoluteLayout

  val tl = vp.build(
    new ShapeComponent(
    AbstractShape.createShape(new Rectangle)
        .withAttribute("width",100f)
        .withAttribute("height", 100f)
        .build
    )
  )
    .withAttribute("x", 640f)
    .withAttribute("y", 540f)
    .withAttribute("borderColor", Color.decode("#3a85ff"))
    .withAttribute("fillColor", Color.decode("#3a85ff"))
    .withAttribute("fill", true)
    .forLayout(al)
    .acquireReference


  w.addVisualPanel(vp)
  w.display("Square")


  MasterSwingEventController.startThread()

  while (true) {
    w.update()
    Thread.sleep(1000)
    tl.<--[AbstractShape]("shape").asInstanceOf[Rectangle].expand(5, 0)
  }
}
