package core.event

class Event(eType: String, eData: EventData){
  private var consumed: Boolean = false

  def isConsumed: Boolean = consumed
  def consume(): Unit = consumed = true

  def getType: String = eType
  def getData: EventData = eData

  override def toString: String = {
    s"$eType: $eData"
  }
}




