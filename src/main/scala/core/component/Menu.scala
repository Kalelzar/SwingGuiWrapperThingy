package core.component

import java.awt.Graphics2D

import scala.collection.mutable

trait Menu extends BasicComponent{

  private val elements = mutable.ListBuffer[MenuElement]()

  def addElement(element: MenuElement): Unit ={
    elements+=element
  }

  def addAllElements(elements: TextMenuElement*): Unit = {
    elements.foreach(addElement)
  }

  def removeElement(element: MenuElement): Unit ={
    elements-=element
  }
  def getElements: mutable.ListBuffer[MenuElement] = elements

  def iterateOverElements(func: MenuElement => Unit): Unit ={
    elements.foreach(func)
  }

  def iterateOverElements[T](func: MenuElement => T): mutable.ListBuffer[T]={
    elements.map(func)
  }

  override def draw(g2d: Graphics2D): Unit = {
    super.draw(g2d)
    iterateOverElements(func = (x: MenuElement) => x.draw(g2d))
  }



}