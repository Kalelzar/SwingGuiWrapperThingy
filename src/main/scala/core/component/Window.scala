package core.component

import java.awt.image.BufferStrategy
import java.awt.{Dimension, Graphics2D}
import javax.swing.{JFrame, SwingUtilities}

import core.event.listener.{EventListener, FocusOnMouseListener}
import core.event.{EventQueue, EventType}
import core.shape.Shape

import scala.collection.mutable
import scala.collection.mutable.Map

/**
  * Created by kalelzar on 5/6/17.
  */
class Window(dimension: Dimension) extends JFrame with BasicComponent {

  implicit def window: Window = this


  override def toString: String = {
    //s"Has Focus: $hasFocus"
    "Window"
  }

  override def hasFocus: Boolean = super.hasFocus

  private val panels : mutable.Map[String, VisualPanel] = mutable.Map()
  private var currentPanel: String = ""

  def addVisualPanel(vp: VisualPanel): Unit = {
    panels(vp.getName) = vp
    if(! <--[mutable.ListBuffer[EventListener]]("listeners").exists(_.isInstanceOf[FocusOnMouseListener])){
      addListener(new FocusOnMouseListener)
    }
  }

  def display(name: String): Unit = {

    if(panels.contains(currentPanel)) remove(panels(currentPanel))
    if(panels(name) != null) add(panels(name))

    createBufferStrategy(2)
    strategy = getBufferStrategy



    currentPanel = name
  }


  def getCurrentVisualPanel: VisualPanel = panels(currentPanel)

  def update(): Unit = {
    //println("Window update start")
    EventQueue.consumeNextEvent()
    val cp = getCurrentVisualPanel
    val g =  strategy.getDrawGraphics

    g.clearRect(0, 0, getWidth, getHeight)
    cp.update(g)

    g.dispose()
    strategy.show()
    //println("Window update end")
  }

  private var strategy: BufferStrategy = _
  SwingUtilities.invokeLater(() => {
    setPreferredSize(dimension)
    setMinimumSize(dimension)
    setResizable(false)
    setLocationRelativeTo(null)
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

    setVisible(true)
  })

  def this(shape: Shape){
    this(shape.getDimension)
    this.-->("shape", shape)
  }

  def this(w: Int, h: Int){
    this(new Dimension(w, h))
  }

  override def draw(graphics2D: Graphics2D): Unit = {
    //panels(currentPanel).repaint()
  }
}

object Window{


  private var mainWindow: Window = _
  private val dummyVisualPanel = new VisualPanel(new Dimension(0,0), "_Dummy")
  def getDummyVisualPanel: VisualPanel = dummyVisualPanel
  implicit def getMainWindow: Window = mainWindow
  def setMainWindow(mainWindow:Window): Unit = this.mainWindow = mainWindow

}