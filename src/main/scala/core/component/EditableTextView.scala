package core.component

import java.awt.event.KeyEvent
import java.awt.{Color, Font, Graphics2D}

import core.event.Event
import core.event.listener.{CompleteMouseEventListener, KeyEventListener}
import core.shape.{AbstractShape, Point2D}
import core.util.Point

trait EditableTextView extends TextView {

  provide[Int]("chars", 10)
  provide[Boolean]("limitChars", false)
  provide[Color]("markColor", Color.BLUE)

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
      if(curTime-clickTime <= 300l){
        startInd = 0
        charsToSelect = <--[String]("text").length
      }
      clickTime = curTime
    }

    override def mousePressed(event: Event): Unit = {
      if(event.getData.at(0).asInstanceOf[Int] != 1 ) return
      val cp = new Point(event.getData.at(1).asInstanceOf[Int], event.getData.at(2).asInstanceOf[Int])
      val colW: Int = metrics.charWidth('m')
      if(!isInside(cp) || cp.getX == mp.getX) return
      cp.translate(colW/5, 0)

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
      charsToSelect = -Math.ceil(dir / metrics.charWidth('m')).toInt
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
          val moves = charsToSelect
          removeText(caret.getPosition-1)
          if(back) caret.previous(1)
          if(backMore) {
            println(moves)
            caret.next(moves)
          }
          onCharacterTyped(-1)
          if(caret.getPosition > <--[String]("text").length) caret.goto( <--[String]("text").length)
        }
        if(getAscii(event)==10){
         submit
        }
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

  def submit

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
    val shape = <--[AbstractShape]("shape")
    val fillColor = <--[Color]("fillColor")
    val borderColor =  <--[Color]("borderColor")
    val text = <--[String]("text")
    val chars = <--[Int]("chars")
    val y = <--[Float]("y")
    val x = <--[Float]("x")
    val textColor = <--[Color]("textColor")
    val markColor = <--[Color]("markColor")
    val font = <--[Font]("font")


    if(!hasFocus && borderColor != fillColor)
      -->("borderColor",fillColor)
    else if(hasFocus && borderColor != Color.BLACK)
      -->("borderColor", Color.BLACK)
    graphics2D.setFont(font)

    //println(s"$getShapeLocation :: ${<--[AbstractShape]("shape").getBounds}")
    shape.draw(graphics2D)

    val columnHeight = metrics.getHeight
    val columnWidth = metrics.charWidth('m')

    if(startInd >= 0){
      graphics2D.setColor(markColor)
      if(charsToSelect != 0)
      {
        if(charsToSelect>0) charsToSelect = Math.min(charsToSelect, text.length-startInd)
        if(charsToSelect<0) charsToSelect = Math.max(charsToSelect, -startInd)
        graphics2D.fillRect(
          (startInd - chars/ 2) * columnWidth + x.toInt  ,
          y.toInt - columnHeight/2,
          columnWidth*charsToSelect,
          columnHeight
        )

      }

      graphics2D.setColor(Color.BLACK)
    }

    graphics2D.setColor(textColor)

    val rot = <--[AbstractShape]("shape").<--[Float]("rotation")


    if(rot == 0)
      graphics2D.drawString(text,
        getShapeLocation.x - shape.getBounds.getWidth.toFloat/2,
        getShapeLocation.y + columnHeight/4)
    else{
      val g2d = graphics2D.create().asInstanceOf[Graphics2D]
      val ct = g2d.getTransform
      ct.rotate(rot, getShapeLocation.x, getShapeLocation.y)
      g2d.setTransform(ct)

      g2d.drawString(text,
        getShapeLocation.x - shape.getBounds.getWidth.toFloat/2,
        getShapeLocation.y + columnHeight/4)
      g2d.dispose()
    }

    if(currentTime-previousTime>=blinkTime){
      previousTime = currentTime
      caretVisible = !caretVisible
    }

    if(caretVisible && hasFocus)
      graphics2D.drawLine(caret.getPosition * columnWidth+(getShapeLocation.x - shape.getBounds.getWidth.toFloat/2).toInt,
        (getShapeLocation.y- columnHeight /2.3f).toInt,
        caret.getPosition* columnWidth+(getShapeLocation.x- shape.getBounds.getWidth.toFloat/2).toInt,
        (getShapeLocation.y+ columnHeight/2.3f).toInt)

    currentTime = System.currentTimeMillis()
    graphics2D.setFont(BasicComponent.getDefaultFont)
  }
}
