package core.component

import java.awt.event.KeyEvent
import java.awt.{Color, Graphics2D, Point}

import core.event.Event
import core.event.listener.{KeyEventListener, MouseEventListener, MouseMovementEventListener}
import core.shape.Shape

//TODO: Add Text Selection with the mouse and keyboard
trait EditableTextView extends TextView {

  provide[Int]("chars", 10)
  provide[Boolean]("limitChars", false)

  private var startInd = -1
  private var charsToSelect = 0

  private class Caret{
    private var position = 0
    def getPosition: Int = position
    def next(inc: Int): Unit = {
      position+=inc
      if(position<0) position=0
      if(position> ?|[String]("text").length+1) position = ?|[String]("text").length+1
    }
    def previous(red: Int): Unit = next(-red)

  }

  class TextMouseSelectListener extends MouseMovementEventListener{
    private var mp = new Point(-1, -1)
    private var startInd = -1
    override def mouseDragged(event: Event): Unit = {
      val cp = new Point(event.getData.at(1).asInstanceOf[Int], event.getData.at(2).asInstanceOf[Int])
      if(!isInside(cp) || cp.equals(mp)) return

      if(event.getData.at(0).asInstanceOf[Int] != 1) return

      if(mp.getX < 0 && mp.getY < 0){
        mp = cp
        startInd = Math.floor((?|[Float]("x") - ?|[Int]("columnWidth") * ?|[String]("text").length) / ?|[Int]("columnWidth")).toInt
      }else{
        val dir = mp.getX - cp.getX
        val charsToSelect = dir / ?|[Int]("columnWidth")
      }


    }

  }

  class TextViewKeyListener extends KeyEventListener{
    override def keyPressed(event: Event): Unit = {
      if(event.getData.at(1) == KeyEvent.VK_LEFT){
        caret.previous(1)
        return
      }else if(event.getData.at(1) == KeyEvent.VK_RIGHT){
        caret.next(1)
        return
      }
      else if( getAscii(event) <= 31 || getAscii(event) >= 127){

        if(getAscii(event) == 127){
          onCharacterTyped(-1)
          removeText(caret.getPosition)
        }
        else if(getAscii(event) == 8){
          removeText(caret.getPosition-1)
          onCharacterTyped(-1)
          caret.previous(1)
        }
        if(getAscii(event)!=10)
          return
      }
      if(?|[Boolean]("limitChars") && ?|[Int]("chars") == ?|[String]("text").length) return
      println(getAscii(event))
      insertTextAtCaret(event.getData.at(0).toString)
      onCharacterTyped(1)
    }

    def getAscii(event: Event): Int ={
      event.getData.at(0).toString.toSeq.head.toInt
    }

  }

  private val keyListener = new TextViewKeyListener
  addListener(keyListener)
  private val mouseListener = new MouseMovementEventListener
  addListener(mouseListener)

  private val caret = new Caret

  def onCharacterTyped(change: Int): Unit = { }

  def insertTextAtCaret(txt: String): Unit ={
    val builder = new StringBuilder
    builder.append(?|[String]("text"))
    builder.insert(caret.getPosition, txt)
    caret.next(txt.length)
    +|("text", builder.toString())
  }

  def append(txt:String): Unit ={
    +|("text", ?|[String]("text")+txt)
  }

  //TODO: TEXT
  def removeText(ind: Int): Unit ={
    println("Removing Text")
    removeText(ind, ind)
  }

  def removeText(startInd: Int, endInd: Int): Unit ={
    var ind = 0
    val string = new StringBuilder
    ?|[String]("text").toCharArray.foreach(x =>{
      if(ind < startInd ||  ind>endInd) string.append(x)
      ind+=1
    })
    +|("text", string.toString())
  }
  private val blinkTime = 550l
  private var caretVisible = true
  private var previousTime = System.currentTimeMillis()
  private var currentTime = System.currentTimeMillis()
  override def draw(graphics2D: Graphics2D): Unit = {
    if(!hasFocus && ?|("borderColor") != ?|[Shape]("shape").getFillColor) +|("borderColor", ?|[Shape]("shape").getFillColor)
    else if(hasFocus && ?|("borderColor") != Color.BLACK)  +|("borderColor", Color.BLACK)
    super.draw(graphics2D)
    if(currentTime-previousTime>=blinkTime){
      previousTime = currentTime
      caretVisible = !caretVisible
    }

    if(startInd > 0){
      graphics2D.drawRect(100, 100, 50, 50)
    }

    if(caretVisible && hasFocus)
      graphics2D.drawLine(caret.getPosition* ?|[Int]("columnWidth")+(getShapeLocation.x- ?|[Shape]("shape").getDimension.width/2),
        (getShapeLocation.y- ?|[Int]("columnHeight") /2.3f).toInt,
        caret.getPosition* ?|[Int]("columnWidth")+(getShapeLocation.x- ?|[Shape]("shape").getDimension.width/2),
        (getShapeLocation.y+ ?|[Int]("columnHeight")/2.3f).toInt)
    currentTime = System.currentTimeMillis()
  }
}
