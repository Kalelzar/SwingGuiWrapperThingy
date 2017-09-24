package core.animation

import core.shape.Shape

class Slide(direction: Direction, dist: Float, var frames: Int) extends Animation {
  onCreate(s"Slide $direction $dist $frames")
  val step: Float = dist/frames
  private var play: Boolean = false

  val ogFrames: Int = frames


  override def start: Unit ={
    play = true
  }

  private def animationFunc(shape: Shape, varargs: Any*): Unit = {
    val dir = varargs.head.asInstanceOf[Direction]
    val step = varargs(1).asInstanceOf[Float]
    shape.move(dir.getMovX, dir.getMovY, step)
  }

  //private val animation : (Shape, Any*)=  (shape: Shape, varargs) => animationFunc(shape, varargs)

  override def update(implicit shape: Shape): Unit = {
    if(frames == 0 || !play) return
    frames-=1
    animate(shape, Seq(direction, step))(animationFunc)
  }

  override def stop: Unit = {
    play = false
  }

  override def reset: Unit = {
    stop
    frames = ogFrames
  }
}
