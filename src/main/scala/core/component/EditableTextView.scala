package core.component

import java.awt.event.KeyEvent
import java.awt.{Color, Graphics2D}

import core.event.Event
import core.event.listener.KeyEventListener

//TODO: Add Text Selection with the mouse and keyboard
trait EditableTextView extends TextView {




  private class Caret{
    private var position = 0
    def getPosition: Int = position
    def next(inc: Int): Unit = {
      position+=inc
      if(position<0) position=0
      if(position>getText.length+1) position = getText.length+1
    }
    def previous(red: Int): Unit = next(-red)

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
      println(getAscii(event))
      insertTextAtCaret(event.getData.at(0).toString)
      onCharacterTyped(1)
    }

    def getAscii(event: Event): Int ={
      event.getData.at(0).toString.toSeq.head.toInt
    }

  }

  private val listener = new TextViewKeyListener
  addListener(listener)

  private val caret = new Caret

  def onCharacterTyped(change: Int): Unit = { }

  def insertTextAtCaret(txt: String): Unit ={
    val builder = new StringBuilder
    builder.append(getText)
    builder.insert(caret.getPosition, txt)
    caret.next(txt.length)
    setText(builder.toString())
  }

  def append(txt:String): Unit ={
    setText(getText+txt)
  }

  //TODO: TEXT
  def removeText(ind: Int): Unit ={
    println("Removing Text")
    removeText(ind, ind)
  }

  def removeText(startInd: Int, endInd: Int): Unit ={
    var ind = 0
    val string = new StringBuilder
    getText.toCharArray.foreach(x =>{
      if(ind < startInd ||  ind>endInd) string.append(x)
      ind+=1
    })
    setText(string.toString())
  }
  private val blinkTime = 550l
  private var caretVisible = true
  private var previousTime = System.currentTimeMillis()
  private var currentTime = System.currentTimeMillis()
  override def draw(graphics2D: Graphics2D): Unit = {
    if(!hasFocus) setBorderColor(shape.getFillColor)
    else setBorderColor(Color.BLACK)
    super.draw(graphics2D)
    if(currentTime-previousTime>=blinkTime){
      previousTime = currentTime
      caretVisible = !caretVisible
    }
    if(caretVisible && hasFocus)
      graphics2D.drawLine(caret.getPosition*columnWidth+(getShapeLocation.x-shape.getDimension.width/2),
        (getShapeLocation.y-columnHeight/2.3f).toInt,
        caret.getPosition*columnWidth+(getShapeLocation.x-shape.getDimension.width/2),
        (getShapeLocation.y+columnHeight/2.3f).toInt)
    currentTime = System.currentTimeMillis()
  }
}
