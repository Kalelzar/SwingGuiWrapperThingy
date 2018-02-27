import java.awt.{Color, Dimension, Font}

import core.animation.Transform
import core.component.utils.Focus
import core.component._
import core.event.SwingEventCaller.MasterSwingEventController
import core.layout.AbsoluteLayout
import core.shape._

object Test3 extends App {

  implicit val w: Window = new Window(1280, 1024)
  Window.setMainWindow(w)
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

  while (true) {
    w.update()
    Thread.sleep(1000)
    if(shape.<--[Float]("x") >= 960 || shape.<--[Float]("x") <= 320){
      dir = -dir
    }
    Transform.apply(shape).rotateInPlace(Math.PI.toFloat/950).translate(dir, 0)()
  }
}
