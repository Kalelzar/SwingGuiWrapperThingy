package core.component

import java.awt.event.KeyEvent
import java.awt.{Color, Graphics2D, Point}

import core.event.Event
import core.event.listener.{CompleteMouseEventListener, KeyEventListener, MouseEventListener, MouseMovementEventListener}
import core.shape.Shape


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
      if(position> <--[String]("text").length+1) position = <--[String]("text").length+1
    }
    def previous(red: Int): Unit = next(-red)

    def goto(pos: Int): Unit = {
      position=pos
      if(position<0) position=0
      if(position> <--[String]("text").length+1) position = <--[String]("text").length+1
    }
  }

  class TextMouseSelectListener extends CompleteMouseEventListener{
    private var mp = new Point(-1, -1)
    private var pressed = false

    private var clickTime = 0l

    override def mouseClicked(event: Event): Unit = {
      val curTime = System.currentTimeMillis()
      println(curTime)
      if(curTime-clickTime <= 300l){
        startInd = 0
        charsToSelect = <--[String]("text").length
      }
      clickTime = curTime
    }

    override def mousePressed(event: Event): Unit = {
      if(event.getData.at(0).asInstanceOf[Int] != 1 ) return
      val cp = new Point(event.getData.at(1).asInstanceOf[Int], event.getData.at(2).asInstanceOf[Int])
      if(!isInside(cp) || cp.getX == mp.getX) return

      val colW = <--[Int]("columnWidth")

      startInd = Math.min( Math.floor(
        (
          cp.getX -
          ( <--[Float]("x") -
            ( colW * <--[Int]("chars") ) / 2
          )
        ) / colW
      ).toInt, <--[String]("text").length)
      charsToSelect = 0
      caret.goto(startInd)

      mp = cp
      pressed = true
    }


    override def mouseReleased(event: Event): Unit = {
      if(event.getData.at(0).asInstanceOf[Int] != 1) return

      mp = new Point(-1, -1)
      pressed = false
    }

    override def mouseDragged(event: Event): Unit = {
      val cp = new Point(event.getData.at(1).asInstanceOf[Int], event.getData.at(2).asInstanceOf[Int])
      if(!isInside(cp) || cp.getX == mp.getX) return
      if(!pressed) return
      val dir = mp.getX - cp.getX
      charsToSelect = -Math.ceil(dir / <--[Int]("columnWidth")).toInt
      if(dir < 0) charsToSelect+=1

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
          if(caret.getPosition > <--[String]("text").length) caret.goto( <--[String]("text").length)
        }
        else if(getAscii(event) == 8){
          val back = charsToSelect==0
          val backMore = charsToSelect<0
          removeText(caret.getPosition-1)
          if(back) caret.previous(1)
          if(backMore) caret.next(charsToSelect)
          onCharacterTyped(-1)
          if(caret.getPosition > <--[String]("text").length) caret.goto( <--[String]("text").length)
        }
        if(getAscii(event)!=10)
          return
      }
      if(<--[Boolean]("limitChars") && <--[Int]("chars") == <--[String]("text").length) return
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
  private val mouseListener = new TextMouseSelectListener
  addListener(mouseListener)

  private val caret = new Caret

  def onCharacterTyped(change: Int): Unit = { }

  def insertTextAtCaret(txt: String): Unit ={
    val builder = new StringBuilder
    builder.append(<--[String]("text"))
    builder.insert(caret.getPosition, txt)
    caret.next(txt.length)
    -->("text", builder.toString())
  }

  def append(txt:String): Unit ={
    -->("text", <--[String]("text")+txt)
  }

  //TODO: TEXT
  def removeText(ind: Int): Unit ={
    println("Removing Text")
    removeText(ind, ind)
  }

  def removeText(startInd: Int, endInd: Int): Unit ={
    if(charsToSelect!=0){
      val sioffset = if(charsToSelect<0) -1 else 0
      val ctsoffset = if(charsToSelect<0) 0 else -1
      val start = Math.min(this.startInd + sioffset, this.startInd+charsToSelect + ctsoffset)
      val end = Math.max(this.startInd + sioffset, this.startInd+charsToSelect + ctsoffset)
      charsToSelect = 0
      removeText(start, end)
      return
    }
    var ind = 0
    val string = new StringBuilder
    <--[String]("text").toCharArray.foreach(x =>{
      if(ind < startInd ||  ind>endInd) string.append(x)
      ind+=1
    })
    -->("text", string.toString())

  }
  private val blinkTime = 550l
  private var caretVisible = true
  private var previousTime = System.currentTimeMillis()
  private var currentTime = System.currentTimeMillis()
  override def draw(graphics2D: Graphics2D): Unit = {
    if(!hasFocus && <--[Color]("borderColor") != <--[Shape]("shape").getFillColor) -->("borderColor", <--[Shape]("shape").getFillColor)
    else if(hasFocus && <--[Color]("borderColor") != Color.BLACK)  -->("borderColor", Color.BLACK)

    graphics2D.setFont(getAttribute("font"))
    <--[Shape]("shape").draw(graphics2D)


    if(startInd >= 0){
      graphics2D.setColor(Color.decode("#6b79d6"))
      if(charsToSelect != 0)
      {
        if(charsToSelect>0) charsToSelect = Math.min(charsToSelect, <--[String]("text").length-startInd)
        if(charsToSelect<0) charsToSelect = Math.max(charsToSelect, -startInd)
        println(charsToSelect)

        val startRectX = (startInd - <--[Int]("chars")/ 2) * <--[Int]("columnWidth") + <--[Float]("x").toInt

        val rectX = if(charsToSelect>0) startRectX else startRectX + <--[Int]("columnWidth")*charsToSelect

        graphics2D.fillRect(
          rectX ,
          <--[Float]("y").toInt - <--[Int]("columnHeight")/2,
          <--[Int]("columnWidth")*Math.abs(charsToSelect),
          <--[Int]("columnHeight")
        )
      }

      graphics2D.setColor(Color.BLACK)
    }

    graphics2D.setColor( <--("textColor") )
    graphics2D.setFont(getAttribute("font"))
    graphics2D.drawString(<--[String]("text"),
      getShapeLocation.x- <--[Shape]("shape").getDimension.width/2,
      getShapeLocation.y+ <--[Int]("columnHeight")/4)
    graphics2D.setFont(Window.getMainWindow.getFont)

    if(currentTime-previousTime>=blinkTime){
      previousTime = currentTime
      caretVisible = !caretVisible
    }

    if(caretVisible && hasFocus)
      graphics2D.drawLine(caret.getPosition* <--[Int]("columnWidth")+(getShapeLocation.x- <--[Shape]("shape").getDimension.width/2),
        (getShapeLocation.y- <--[Int]("columnHeight") /2.3f).toInt,
        caret.getPosition* <--[Int]("columnWidth")+(getShapeLocation.x- <--[Shape]("shape").getDimension.width/2),
        (getShapeLocation.y+ <--[Int]("columnHeight")/2.3f).toInt)
    currentTime = System.currentTimeMillis()
    graphics2D.setFont(BasicComponent.getDefaultFont)
  }
}
