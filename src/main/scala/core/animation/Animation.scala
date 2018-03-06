package core.animation

import core.component.{BasicComponent, Window}
import core.shape.AbstractShape

object Animation {

  def animate(comp: BasicComponent, duration: Float): AnimationBuilder
    = new AnimationBuilder(comp, duration)

}

class Animation(transform: Transform){

  val animationThread = new Thread( () => {
    var run = true
    while(run){
      Thread.sleep(1000/Window.getFramerate)
      transform()
    }
  })

  def start(): Unit ={
    animationThread.start()
  }
}
class AnimationBuilder(comp: BasicComponent, duration: Float){
  private val transform = Transform(comp.<--[AbstractShape]("shape"))

  def translate(mx: Float, my: Float): AnimationBuilder ={
    transform.translate(mx/duration, my/duration)
    this
  }

  def get: Animation = new Animation(transform)
}