import java.awt.{Color, Dimension, Font}

import core.animation.{Animation, Transform}
import core.component.utils.Focus
import core.component._
import core.event.SwingEventCaller.MasterSwingEventController
import core.layout.AbsoluteLayout
import core.shape._

object Test3 extends App {

  implicit val w: Window = new Window(1280, 1024)
  Window.setMainWindow(w)
  w.showFramerate(true)
  Focus.giveFocus(BasicComponent.getID(w))

  implicit val vp: VisualPanel = new VisualPanel(new Dimension(800, 600), "Square")
  val font = new Font(Font.MONOSPACED, Font.PLAIN, 24)
  val al = new AbsoluteLayout

  val circle = vp.build(new TextField)
    .withAttribute("x", 640f)
    .withAttribute("y", 540f)
    .withAttribute("text", "This is a test")
    .withAttribute("font", font)
    .forLayout(al)
    .acquireReference



  w.addVisualPanel(vp)
  w.display("Square")


  MasterSwingEventController.startThread()

  val shape = circle.<--[AbstractShape]("shape")

  var dir = 5f

  val anim = Animation.animate(circle, 120).translate(-540, 0).get


  var frames = 0
  anim.start()
  while (frames < 120) {
    w.update()
    Thread.sleep(1000/Window.getFramerate)
    frames+=1
  }
}
