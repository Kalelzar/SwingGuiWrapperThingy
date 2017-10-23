package core.event

import core.component.BasicComponent

import scala.collection.mutable.ListBuffer


class EventData {

  def at(i: Int): Any = data.apply(i)
  private var action: String = _
  private var source: BasicComponent = _
  private val data: ListBuffer[Any] = ListBuffer()

  def setSource(bc: BasicComponent): Unit = source = bc
  def setAction(act: String): Unit = action = act

  def addData(data: Any*): Unit = {
    this.data++=data
  }

  def getData: ListBuffer[Any] = data
  def getSource: BasicComponent = source
  def getAction: String = action

  override def toString: String = {
    s"$action $data $source"
  }
}
