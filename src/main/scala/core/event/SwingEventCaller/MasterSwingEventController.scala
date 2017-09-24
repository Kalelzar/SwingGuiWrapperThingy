package core.event.SwingEventCaller

import core.component.Window
import core.event.{EventQueue, EventType}

object MasterSwingEventController {

  private val controllerLoopThread = new Thread(
    () =>
    {
      val run = true
      while(run){
        val listenersAvailable = EventQueue.getListenerTypes
        val window = Window.getMainWindow
        listenersAvailable.foreach {
          case EventType.Mouse => if(window.getMouseListeners.length<1) window.addMouseListener(new SwingMouseEventsCaller)
          case EventType.MouseMovement => if(window.getMouseMotionListeners.length<1) window.addMouseMotionListener(new SwingMouseEventsCaller)
          case EventType.MouseWheel => if(window.getMouseWheelListeners.length<1) window.addMouseWheelListener(new SwingMouseEventsCaller)
          case EventType.Focus => //Ignore as it is not handled through AWT Focus Events
          case EventType.Keyboard => if(window.getKeyListeners.length<1) window.addKeyListener(new SwingKeyboardEventsCaller)
          case x:AnyRef => {
            System.err.println(s"$x is not a recognized EventType")
          }
        }
        Thread.sleep(1000)
      }
    }
  )

  def startThread(): Unit = controllerLoopThread.start()
  def stopThread(): Unit = controllerLoopThread.interrupt()

}
