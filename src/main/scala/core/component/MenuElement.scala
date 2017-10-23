package core.component

trait MenuElement extends BasicComponent{

  def onClick(mouseButton: Int, f: (Int) => (Unit) ): Unit ={
    f(mouseButton)
  }

  def show(): Unit
  def hide(): Unit

  def whenBoxed(index: Int, menu: Menu)

}
